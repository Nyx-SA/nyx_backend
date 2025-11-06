package com.github.grupo_s.nyx_app.establecimiento;

import com.github.grupo_s.nyx_app.users.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EstablecimientoService {

    private final EstablecimientoRepository establecimientoRepository;

    public List<Establecimiento> getAll(){
        return establecimientoRepository.findAll()
                .stream()
                .collect(Collectors.toList());
    }

    public Establecimiento getById(Long id) {
        Optional<Establecimiento> establecimiento = establecimientoRepository.findById(id);
        return establecimiento.orElseThrow(() ->
                new EntityNotFoundException("Establecimiento con id " + id + " no encontrado"));
    }

    //guardar el establecimiento asociado a un dueno
    public Establecimiento saveEstablecimiento(Establecimiento establecimiento, User dueno) {
        Optional<Establecimiento> existingEstablecimiento =
                establecimientoRepository.findByNombre(establecimiento.getNombre());

        if (existingEstablecimiento.isPresent()) {
            return null; // Establecimiento ya existe
        }

        establecimiento.setDueno(dueno); //ACA asignamos el dueno al establecimiento

        return establecimientoRepository.save(establecimiento);
    }

    public Establecimiento updateEstablecimiento(long id, Establecimiento updatedEstablecimiento) {

        //buscamos el establecimiento a actualizar
        Establecimiento establecimiento = establecimientoRepository.findById(id)
                //Chequeamos que exista
                .orElseThrow(() -> new EntityNotFoundException("Establecimiento con id " + id + " no encontrado"));

        updatedEstablecimiento.setIdEstablecimiento(establecimiento.getIdEstablecimiento());

        //Guardamos el establecimiento actualizado
        return establecimientoRepository.save(updatedEstablecimiento);
    }

    //borrar el establecimiento por id
    public void deleteEstablecimiento(long id) {

        Establecimiento establecimiento = establecimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Establecimiento con id " + id + " no encontrado"));

        establecimientoRepository.delete(establecimiento);


    }










}
