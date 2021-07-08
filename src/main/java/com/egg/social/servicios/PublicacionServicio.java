package com.egg.social.servicios;

import com.egg.social.entidades.Perfil;
import com.egg.social.repositorios.PublicacionRepositorio;
import com.egg.social.entidades.Publicacion;
import com.egg.social.excepciones.ExcepcionSpring;
import com.egg.social.repositorios.InvitacionRepositorio;
import com.egg.social.repositorios.PerfilRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PublicacionServicio {

    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    @Autowired
    private PerfilRepositorio perfilRepositorio;

    @Autowired
    private InvitacionRepositorio invitacionRepositorio;

    @Autowired
    private FotoServicio fotoServicio;

    @Transactional
    public void crearPublicacion(Long idUsuario, String descripcion, MultipartFile foto) throws ExcepcionSpring {
        try {
            Perfil perfil = perfilRepositorio.buscarPerfilPorIdDeUsuario(idUsuario);

            if (perfil != null) {
                Publicacion publicacion = new Publicacion();

                publicacion.setPerfil(perfil);
                publicacion.setDescripcion(descripcion);
                publicacion.setFechaDePublicacion(new Date());

                if (!foto.isEmpty()) {
                    publicacion.setFoto(fotoServicio.guardarFoto(foto));
                }

                publicacionRepositorio.save(publicacion);
            } else {
                throw new ExcepcionSpring("No existe un usuario con el ID indicado");
            }
        } catch (ExcepcionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExcepcionSpring("Error al crear publicación");
        }
    }

    @Transactional
    public void modificarPublicacion(Long idPublicacion, String descripcion, MultipartFile foto) throws ExcepcionSpring {
        try {
            Publicacion publicacion = publicacionRepositorio.buscarPublicacionPorId(idPublicacion);

            if (publicacion != null) {
                publicacion.setDescripcion(descripcion);

                if (!foto.isEmpty()) {
                    publicacion.setFoto(fotoServicio.guardarFoto(foto));
                }

                publicacionRepositorio.save(publicacion);
            } else {
                throw new ExcepcionSpring("No se ha encontrado a ningun publicacion para editar");
            }
        } catch (ExcepcionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExcepcionSpring("Error al modificar publicacion");
        }
    }

    @Transactional(readOnly = true)
    public List<Publicacion> buscarPublicaciones(Long idUsuario) throws ExcepcionSpring {
        try {
            Perfil perfil = perfilRepositorio.buscarPerfilPorIdDeUsuario(idUsuario);

            if (perfil != null) {
                List<Long> listaDeAmigos = invitacionRepositorio.listaDestinatario(perfil.getId());
                listaDeAmigos.addAll(invitacionRepositorio.listaRemitente(perfil.getId()));

                List<Publicacion> publicaciones = publicacionRepositorio.findByPerfil_IdIn(listaDeAmigos);

                List<Publicacion> publicacionesRetorno = new ArrayList<>();

                for (Publicacion publicacion : publicaciones) {
                    publicacion.setComentarios(publicacion.getComentarios().stream()
                            .filter(c -> c.getFechaDeBaja() == null).collect(Collectors.toList()));
                    publicacionesRetorno.add(publicacion);
                }

                return publicacionesRetorno;
            } else {
                throw new ExcepcionSpring("No existen un usuario con el ID indicado");
            }
        } catch (ExcepcionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExcepcionSpring("Error al buscar publicaciones");
        }
    }

    @Transactional
    public void eliminarPublicacion(Long idPublicacion) throws ExcepcionSpring {
        try {
            Publicacion publicacion = publicacionRepositorio.buscarPublicacionPorId(idPublicacion);

            if (publicacion != null) {
                publicacion.setFechaDeBaja(new Date());

                publicacionRepositorio.save(publicacion);
            } else {
                throw new ExcepcionSpring("No existe una publicacion con el ID indicado");
            }
        } catch (ExcepcionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExcepcionSpring("Error al eliminar publicación");
        }
    }
}
