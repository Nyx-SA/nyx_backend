package com.github.grupo_s.nyx_app.evento;

import com.github.grupo_s.nyx_app.establecimiento.Establecimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    //Metodo para verificar si existe un evento con el mismo nombre en un establecimiento dado

    boolean existsByNombreAndEstablecimiento(String nombre, Establecimiento establecimiento);

}
