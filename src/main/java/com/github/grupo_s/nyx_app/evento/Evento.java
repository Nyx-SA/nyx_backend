package com.github.grupo_s.nyx_app.evento;

import com.github.grupo_s.nyx_app.establecimiento.Establecimiento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "evento")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvento;

    @NotNull
    private String nombre;

    @NotNull
    private String descripcion;

    @NotNull
    private String imagen;

    @NotNull
    private LocalDateTime fechaHoraInicio;

    @NotNull
    private LocalDateTime fechaHoraFin;

    @ManyToOne
    @JoinColumn(name = "idEstablecimiento", nullable = false)
    private Establecimiento establecimiento;


}

