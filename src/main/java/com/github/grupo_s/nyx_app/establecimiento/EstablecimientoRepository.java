package com.github.grupo_s.nyx_app.establecimiento;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstablecimientoRepository extends JpaRepository<Establecimiento, Long> {

    // Buscar establecimiento por nombre
    Optional<Establecimiento> findByNombre(String nombre);
    // Buscar establecimientos por tipo
    List<Establecimiento> findByTipoEstablecimiento(TipoEstablecimientoEnum tipoEstablecimiento);
}
