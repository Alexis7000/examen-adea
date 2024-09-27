package com.java.examen_adea.repository;

import com.java.examen_adea.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface  UsuarioRepository  extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByLogin(String login);

    List<Usuario> findByNombre(String nombre);

    //Busquedas por estatus
    List<Usuario> findByStatus(char status);


    //Busqueda por fecha de alta inicial
    List<Usuario> findByFechaAlta(Date fechaAlta);

    //Busqueda por fecha de alta final
    List<Usuario> findByFechaBaja(Date fechaBaja);

}
