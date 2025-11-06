package com.github.grupo_s.nyx_app.ubicacion;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "direccion")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDireccion;

    @NotNull
    private String calle;

    @NotNull
    private String altura;

    @NotNull
    private String piso;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idLocalidad", nullable = false)
    private Localidad localidad;

}
