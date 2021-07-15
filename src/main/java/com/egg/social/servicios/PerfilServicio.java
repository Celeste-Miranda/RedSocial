package com.egg.social.servicios;

import com.egg.social.entidades.Invitacion;
import com.egg.social.entidades.Perfil;
import com.egg.social.entidades.Usuario;
import com.egg.social.excepciones.ExcepcionSpring;
import com.egg.social.repositorios.InvitacionRepositorio;
import com.egg.social.repositorios.PerfilRepositorio;
import com.egg.social.validaciones.Validacion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    @Autowired
    private InvitacionRepositorio invitacionRepositorio;

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

                perfil.setTecnologias(tecnologías);

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
    public List<Perfil> buscarPorNombreYApellido(String nombreYApellido) throws ExcepcionSpring {
        try {
            String nombre = "";
            String apellido = "";

            for (int i = 0; i < nombreYApellido.length(); i++) {
                nombre += nombreYApellido.substring(i, i + 1);
                if (nombreYApellido.substring(i, i + 1).equalsIgnoreCase(" ")) {
                    break;
                }
            }

            for (int i = nombre.length(); i < nombreYApellido.length(); i++) {
                apellido += nombreYApellido.substring(i, i + 1);
            }

            if (nombre.equals("")) {
                throw new ExcepcionSpring("No ingreso ningun dato en la búsqueda");
            }

            List<Perfil> perfiles = new ArrayList<>();

            if (apellido.equals("")) {
                perfiles = perfilRepositorio.buscarPerfilesPorNombreOApellido(nombre);
            } else {

                perfiles = perfilRepositorio.buscarPerfilesPorNombreYApellido(nombre, apellido);

            }

            if (!perfiles.isEmpty()) {
                return perfiles;
            } else {
                throw new ExcepcionSpring("No existen coincidencias en la búsqueda");
            }
        } catch (ExcepcionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExcepcionSpring("Error al buscar perfiles por nombre y apellido");
        }
    }

    @Transactional(readOnly = true)
    public Perfil buscarPerfilPorIdUsuario(Long idUsuario) throws ExcepcionSpring {
        try {
            Perfil perfil = perfilRepositorio.buscarPerfilPorIdDeUsuario(idUsuario);

            if (perfil != null) {
                return perfil;
            } else {
                throw new ExcepcionSpring("No existe un usuario con el ID indicado");
            }
        } catch (ExcepcionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExcepcionSpring("Error al buscar perfil");
        }
    }

    @Transactional(readOnly = true)
    public List<Perfil> listaDeCuatro(List<Perfil> listaPerfil, Long idPerfil) throws ExcepcionSpring {
        try {
            Collections.shuffle(listaPerfil);
            Perfil perfil = buscarPerfilPorIdUsuario(idPerfil);

            List<Perfil> listaCuatro = new ArrayList();

            if (listaPerfil.size() > 4) {
                listaCuatro = listaPerfil.subList(0, 4);
            } else {
                listaCuatro = listaPerfil;
            }

            listaCuatro.remove(perfil);

            return listaCuatro;
        } catch (ExcepcionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExcepcionSpring("Error al listar perfiles");
        }
    }

    @Transactional(readOnly = true)
    public Perfil obtenerPerfil(Long idPerfil) throws ExcepcionSpring {
        try {
            Perfil perfil = perfilRepositorio.findById(idPerfil).orElse(null);

            if (perfil != null) {
                return perfil;
            } else {
                throw new ExcepcionSpring("No existe un perfil con el ID indicado");
            }
        } catch (ExcepcionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExcepcionSpring("Error al buscar perfil");
        }
    }

    @Transactional(readOnly = true)
    public List<Perfil> obtenerAmigos(Long idUsuario) throws ExcepcionSpring {
        try {

            Perfil perfil = perfilRepositorio.buscarPerfilPorIdDeUsuario(idUsuario);

            List<Perfil> listaDeAmigos = invitacionRepositorio.listaDestinatarioPerfil(perfil.getId());
            listaDeAmigos.addAll(invitacionRepositorio.listaRemitentePerfil(perfil.getId()));

            return listaDeAmigos;

        } catch (Exception e) {
            throw new ExcepcionSpring("Error al buscar amigos de usuario");
        }
    }
}
