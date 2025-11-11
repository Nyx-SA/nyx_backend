package com.github.grupo_s.nyx_app.evento;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nyx")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    // Lista todos los eventos
    @GetMapping("/eventos")
    public ResponseEntity<?> getEventos() {
        try {
            return ResponseEntity.ok(eventoService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    //Obtener un evento por id
    @GetMapping("/evento/{id}")
    public ResponseEntity<?> getEvento(@PathVariable Long id) {
        try {
            Evento evento = eventoService.getById(id);
            if (evento == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Evento not found");
            }
            return ResponseEntity.ok(evento);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    // Crear un nuevo evento, solo el duenio puede
    @PostMapping("/evento")
    @PreAuthorize("hasAuthority('DUENO')")
    public ResponseEntity<?> createEvento(@Valid @RequestBody Evento evento) {
        try {
            Evento savedEvento = eventoService.saveEvento(evento);

            if (savedEvento == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("This evento already exists for this establecimiento");
            }

            return ResponseEntity.ok(savedEvento);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    //Actualizar un evento, solo el duenio puede
    @PutMapping("/evento/{id}")
    @PreAuthorize("hasAuthority('DUENO')")
    public ResponseEntity<?> updateEvento(@PathVariable Long id, @Valid @RequestBody Evento evento) {
        try {
            Evento savedEvento = eventoService.updateEvento(id, evento);

            if (savedEvento == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("This evento does not exist");
            }
            return ResponseEntity.ok(savedEvento);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    // DELETE /nyx/evento/1 - Elimina un evento (solo DUENO)
    @DeleteMapping("/evento/{id}")
    @PreAuthorize("hasAuthority('DUENO')")
    public ResponseEntity<?> deleteEvento(@PathVariable Long id) {
        try {
            if (eventoService.getById(id) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("This evento does not exist");
            }
            eventoService.deleteEvento(id);
            return ResponseEntity.ok().body("Evento deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
}