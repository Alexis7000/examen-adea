package com.java.examen_adea.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuario")
public class Usuario{

    @Id
    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "cliente", nullable = false)
    private Float cliente;

    @Column(name = "email")
    private String email;

    @Column(name = "fecha_alta", updatable = false)
    private LocalDate fechaAlta;

    @Column(name = "fecha_baja")
    private LocalDate fechaBaja;

    @Column(name = "status", nullable = false)
    private Character status;

    @Column(name = "intentos", nullable = false)
    private Float intentos;

    @Column(name = "fecha_revocado")
    private LocalDate fechaRevocado;

    @Column(name = "fecha_vigencia")
    private LocalDate fechaVigencia;

    @Column(name = "no_acceso")
    private Integer noAcceso;

    @Column(name = "apellido_paterno")
    private String apellidoPaterno;

    @Column(name = "apellido_materno")
    private String apellidoMaterno;

    @Column(name = "area")
    private Integer area;

    @Column(name = "fecha_modificacion", nullable = false)
    private LocalDate fechaModificacion;


}

