/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.social.servicios;

import com.egg.social.entidades.Comentario;
import com.egg.social.entidades.Publicacion;
import com.egg.social.repositorios.ComentarioRepositorio;
import com.egg.social.repositorios.PublicacionRepositorio;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rodri
 */
@Service
public class ComentarioServicio {

    //Importar repositorios a utilizar
    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;

    @Autowired
    private PerfilServicio perfilServicio;

    @Transactional
    public void crearComentario(Long idUsuario, Publicacion publicacion, String descripcion) {

        //despues hacemos validacion 
        Comentario comentario = new Comentario();

        comentario.setPublicacion(publicacion);
        comentario.setPerfil(perfilServicio.buscarPorIdUsuario(idUsuario));
        comentario.setDescripcion(descripcion);

        comentarioRepositorio.save(comentario);

        publicacion.getComentarios().add(comentario);

        publicacionRepositorio.save(publicacion);
    }

    @Transactional
    public void eliminar(Comentario comentario) {
        comentario.setFechaDeBaja(new Date());

        comentarioRepositorio.save(comentario);

    }

}
