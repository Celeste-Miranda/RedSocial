package com.egg.social.servicios;

import com.egg.social.entidades.Usuario;
import com.egg.social.excepciones.ExcepcionSpring;
import com.egg.social.repositorios.RolRepositorio;
import com.egg.social.repositorios.UsuarioRepositorio;
import com.egg.social.utilidades.Utilidad;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private RolRepositorio rolRepositorio;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public void crearUsuario(String correo, String password, String password2) throws ExcepcionSpring {
        try {
            Utilidad.validarUsuario(correo, password, password2);

            if (usuarioRepositorio.buscarUsuarioPorCorreo(correo) == null) {
                Usuario usuario = new Usuario();

                usuario.setCorreo(correo);
                usuario.setPassword(encoder.encode(password));

                if (usuarioRepositorio.findAll().isEmpty()) {
                    usuario.setRol(rolRepositorio.buscarRolAdministrador());
                } else {
                    usuario.setRol(rolRepositorio.buscarRolUsuario());
                }

                usuarioRepositorio.save(usuario);
            } else {
                throw new ExcepcionSpring("Ya existe un usuario con el correo ingresado");
            }
        } catch (ExcepcionSpring e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcepcionSpring("Error al registrar usuario");
        }
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarUsuarios() throws ExcepcionSpring {
        try {
            List<Usuario> usuarios = usuarioRepositorio.buscarUsuarios();

            if (!usuarios.isEmpty()) {
                return usuarios;
            } else {
                throw new ExcepcionSpring("No existen usuarios registrados en la plataforma");
            }
        } catch (ExcepcionSpring e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcepcionSpring("Error al buscar usuarios");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarUsuarioPorCorreo(correo);

        if (usuario == null) {
            throw new UsernameNotFoundException("No existe un usuario registrado con el correo electrónico ingresado");
        }

        GrantedAuthority rol = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombre());

        return new User(usuario.getCorreo(), usuario.getPassword(), Collections.singletonList(rol));
    }
}
