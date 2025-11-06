package com.github.grupo_s.nyx_app.establecimiento;

import com.github.grupo_s.nyx_app.ubicacion.Direccion;
import com.github.grupo_s.nyx_app.users.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "establecimiento")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Establecimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstablecimiento;

    @NotNull
    private String nombre;

    @NotNull
    private String descripcion;

    @NotNull
    private String imagen;

    @NotNull
    private Integer capacidad;

    @NotNull
    private LocalDateTime horaApertura;

    @NotNull
    private LocalDateTime horaCierre;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoEstablecimiento")
    private TipoEstablecimientoEnum tipoEstablecimiento;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idDireccion")
    private Direccion direccion;

    @ManyToOne
    @JoinColumn(name = "idDueno", nullable = false)
    private User dueno;

}

