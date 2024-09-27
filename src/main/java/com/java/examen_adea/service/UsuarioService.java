    package com.java.examen_adea.service;
    import com.java.examen_adea.entity.Usuario;
    import com.java.examen_adea.repository.UsuarioRepository;
    import jakarta.transaction.Transactional;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.dao.DataAccessException;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.User;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.stereotype.Service;
    
    import java.nio.charset.StandardCharsets;
    import java.security.MessageDigest;
    import java.security.NoSuchAlgorithmException;
    import java.time.LocalDate;
    import java.util.*;

    @Service
    public class UsuarioService implements UserDetailsService {
        @Autowired
        UsuarioRepository usuarioRepository;
    
        public Usuario create(Usuario usuario) {
            usuario.setPassword(encriptarConSHA256(usuario.getPassword()));
            LocalDate currentDate = LocalDate.now();
            usuario.setFechaAlta(currentDate);
            usuario.setFechaModificacion(currentDate);
            if (usuario.getFechaVigencia().isBefore(currentDate)) {
                usuario.setStatus('A');
            } else {
                usuario.setStatus('B');
            }
            usuario.setStatus('A');
            usuario.setIntentos(0.0f);
            return usuarioRepository.save(usuario);
        }


        public List<Usuario> findStatus(Character status) {
            return usuarioRepository.findByStatus(status);
        }

        public List<Usuario> findNombre(String numbre) {
            return usuarioRepository.findByNombre(numbre);
        }

        public List<Usuario> findFechaAlta(Date fecha) {
            return usuarioRepository.findByFechaAlta(fecha);
        }

        public List<Usuario> findFechaBaja(Date fecha) {
            return usuarioRepository.findByFechaBaja(fecha);
        }

        public List<Usuario> findAllUser() {
            return usuarioRepository.findAll();
        }



        public Usuario update(Usuario usuario) {
            usuario.setPassword(encriptarConSHA256(usuario.getPassword()));
            LocalDate currentDate = LocalDate.now();
            usuario.setFechaModificacion(currentDate);
            if (usuario.getFechaVigencia() == null) {
                // Manejo del caso: puedes lanzar una excepción, devolver un error, o asignar un valor por defecto
                throw new IllegalArgumentException("La fecha de vigencia no puede ser nula");
            }

            if (usuario.getStatus() != 'R' && usuario.getFechaVigencia().isAfter(currentDate)) {
                usuario.setStatus('A');
            } else {
                usuario.setStatus('B');
            }
            return usuarioRepository.save(usuario);
        }

        public boolean delete(Usuario usuario) {
            try {
                usuarioRepository.delete(usuario);
                return true;
            } catch (DataAccessException e) {
                System.err.println("Error al eliminar el usuario: " + e.getMessage());
                return false;
            }
        }



        public String encriptarConSHA256(String password) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                return Base64.getEncoder().encodeToString(encodedhash);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Error al encriptar la contraseña", e);
            }
        }
        public Usuario findByLogin(String login) {
            return usuarioRepository.findById(login).orElse(null); // Busca el usuario por su login

        }

        public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
            Usuario usuario = findByLogin(nombre);
            if(usuario == null) {
                throw new UsernameNotFoundException("Usuario inválido");
            }
            // Verificar la fecha de vigencia
            LocalDate currentDate = LocalDate.now();
            if (usuario.getFechaVigencia() != null && usuario.getFechaVigencia().isBefore(currentDate)) {
                throw new UsernameNotFoundException("La fecha de vigencia ha expirado");
            }
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
            return new User(usuario.getLogin(),usuario.getPassword(), Collections.singletonList(authority));

        }

    }
