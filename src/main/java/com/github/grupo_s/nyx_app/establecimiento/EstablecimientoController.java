package com.github.grupo_s.nyx_app.establecimiento;

import com.github.grupo_s.nyx_app.users.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nyx")
public class EstablecimientoController {

    @Autowired
    private EstablecimientoService establecimientoService;

    // Obtener todos los establecimientos
    @GetMapping("/establecimientos")
    public ResponseEntity<?> getEstablecimientos() {
        try {
            return ResponseEntity.ok(establecimientoService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    // Obtener un establecimiento por ID
    @GetMapping("/establecimiento/{id}")
    public ResponseEntity<?> getEstablecimiento(@PathVariable long id) {
        try {
            return ResponseEntity.ok(establecimientoService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    // Crear un nuevo establecimiento, asociado al usuario autenticado, sacamos el usuario directamente del token
    @PostMapping("/establecimiento")
    @PreAuthorize("hasAuthority('DUENO')") //Solo un usuario con el rol de dueno va a poder crear los establecimientos
    public ResponseEntity<?> createEstablecimiento(@Valid @RequestBody Establecimiento establecimiento, @AuthenticationPrincipal User user) {
        try {
            Establecimiento savedEstablecimiento = establecimientoService.saveEstablecimiento(establecimiento, user); //Le pasamos el usuario autenticado al service

            if (savedEstablecimiento == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("This establecimiento already exists");
            }

            return ResponseEntity.ok(savedEstablecimiento);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @PutMapping("/establecimiento/{id}")
    public ResponseEntity<?> updateEstablecimiento(@PathVariable long id, @Valid @RequestBody Establecimiento establecimiento) {
        try {
            Establecimiento savedEstablecimiento = establecimientoService.updateEstablecimiento(id, establecimiento);

            if (savedEstablecimiento == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("This establecimiento does not exist");
            }
            return ResponseEntity.ok(savedEstablecimiento);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/establecimiento/{id}")
    @PreAuthorize("hasAuthority('DUENO')") //Solo un usuario con el rol de dueno va a poder eliminar los establecimientos
    public ResponseEntity<?> deleteEstablecimiento(@PathVariable long id) {
        try {
            if (establecimientoService.getById(id) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("This establecimiento does not exist");
            }
            establecimientoService.deleteEstablecimiento(id);
            return ResponseEntity.ok().body("Establecimiento deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
}
