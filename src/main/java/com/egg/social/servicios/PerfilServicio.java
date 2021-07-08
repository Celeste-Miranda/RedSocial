package com.egg.social.servicios;

import com.egg.social.entidades.Invitacion;
import com.egg.social.entidades.Perfil;
import com.egg.social.entidades.Usuario;
import com.egg.social.excepciones.ExcepcionSpring;
import com.egg.social.repositorios.PerfilRepositorio;
import com.egg.social.validaciones.Validacion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PerfilServicio {

    @Autowired
    private PerfilRepositorio perfilRepositorio;

    @Autowired
    private FotoServicio fotoServicio;

    @Transactional
    public Perfil crearPerfil(Usuario usuario) throws ExcepcionSpring {
        try {
            if (usuario != null) {
                Perfil perfil = new Perfil();

                perfil.setUsuario(usuario);

                return perfilRepositorio.save(perfil);
            } else {
                throw new ExcepcionSpring("El usuario no puede ser nulo");
            }
        } catch (ExcepcionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExcepcionSpring("Error al crear perfil");
        }
    }

    @Transactional
    public void modificar(Long idUsuario, String nombre, String apellido, String residencia, List<String> tecnologías, MultipartFile foto) throws ExcepcionSpring {
        try {
            Validacion.validarPerfil(idUsuario, nombre, apellido, residencia);

            Perfil perfil = perfilRepositorio.buscarPerfilPorIdDeUsuario(idUsuario);

            if (perfil != null) {
                perfil.setNombre(nombre);
                perfil.setApellido(apellido);
                perfil.setResidencia(residencia);
                perfil.getTecnologias().addAll(tecnologías);

                if (!foto.isEmpty()) {
                    perfil.setFoto(fotoServicio.guardarFoto(foto));
                }

                perfilRepositorio.save(perfil);
            } else {
                throw new ExcepcionSpring("El usuario no puede ser nulo");
            }
        } catch (ExcepcionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExcepcionSpring("Error al modificar perfil");
        }
    }

    public List<String> obtenerTecnologias() {
        return Arrays.asList("Java", "Python", "PHP", "JavaScript", "TypeScript", "Go", "C#", "C++", "Swift");
    }

    @Transactional(readOnly = true)
    public List<Perfil> mostrarTodos() throws ExcepcionSpring {
        try {
            List perfiles = perfilRepositorio.buscarPerfiles();

            if (!perfiles.isEmpty()) {
                return perfiles;
            } else {
                throw new ExcepcionSpring("No existen usuarios registrados en la plataforma");
            }
        } catch (ExcepcionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExcepcionSpring("Error al buscar usuarios");
        }
    }

    /* Método de Celeste */
    @Transactional(readOnly = true)
    public List<Perfil> buscarPorNombreYApellido(String nombre, String apellido) {
        return perfilRepositorio.buscarPorNombreYApellido(apellido, nombre);
    }

    @Transactional(readOnly = true)
    public List<Perfil> obtenerAmigos(Long idUsuario) throws ExcepcionSpring {
        try {
            List<Perfil> listaDeAmigos = new ArrayList();
            Perfil perfil = perfilRepositorio.buscarPerfilPorIdDeUsuario(idUsuario);

            if (perfil != null) {
                for (Invitacion i : perfil.getInvitacionesEnviadas()) {
                    if (i.getAceptada() == true) {
                        listaDeAmigos.add(i.getDestinatario());
                    }
                }

                for (Invitacion i : perfil.getInvitacionesRecibidas()) {
                    if (i.getAceptada() == true) {
                        listaDeAmigos.add(i.getRemitente());
                    }
                }

                return listaDeAmigos;
            } else {
                throw new ExcepcionSpring("No exite un usuario con el ID indicado");
            }
        } catch (ExcepcionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExcepcionSpring("Error al buscar amigos de usuario");
        }
    }
}
