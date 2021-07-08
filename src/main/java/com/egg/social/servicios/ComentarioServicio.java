package com.egg.social.servicios;

import com.egg.social.entidades.Comentario;
import com.egg.social.entidades.Perfil;
import com.egg.social.entidades.Publicacion;
import com.egg.social.excepciones.ExcepcionSpring;
import com.egg.social.repositorios.ComentarioRepositorio;
import com.egg.social.repositorios.PerfilRepositorio;
import com.egg.social.repositorios.PublicacionRepositorio;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComentarioServicio {

    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;

    @Autowired
    private PerfilRepositorio perfilRepositorio;

    @Transactional
    public void crearComentario(Long idUsuario, Publicacion publicacion, String descripcion) throws ExcepcionSpring {
        try {
            Perfil perfil = perfilRepositorio.buscarPerfilPorIdDeUsuario(idUsuario);

            if (perfil != null && publicacion != null) {
                if (descripcion == null && descripcion.isEmpty()) {
                    throw new ExcepcionSpring("La descripción no puede ser nula");
                }

                Comentario comentario = new Comentario();

                comentario.setPublicacion(publicacion);
                comentario.setPerfil(perfil);
                comentario.setDescripcion(descripcion);

                comentarioRepositorio.save(comentario);

                publicacion.getComentarios().add(comentario);

                publicacionRepositorio.save(publicacion);
            } else {
                throw new ExcepcionSpring("Es necesario que exista tanto un usuario como una publicación");
            }
        } catch (ExcepcionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExcepcionSpring("Error al crear comentario");
        }
    }

    @Transactional
    public void eliminarComentario(Comentario comentario) throws ExcepcionSpring {
        try {
            if (comentario != null) {
                comentario.setFechaDeBaja(new Date());

                comentarioRepositorio.save(comentario);
            } else {
                throw new ExcepcionSpring("El comentario indicado no existe");
            }
        } catch (ExcepcionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExcepcionSpring("Error al eliminar comentario");
        }
    }
}
