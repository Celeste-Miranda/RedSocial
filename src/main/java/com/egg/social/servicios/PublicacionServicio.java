package com.egg.social.servicios;

import com.egg.social.repositorios.PublicacionRepositorio;
import com.egg.social.entidades.Publicacion;
import com.egg.social.repositorios.ComentarioRepositorio;
import com.egg.social.repositorios.PerfilRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PublicacionServicio {

    //Importar repositorios a utilizar
    @Autowired
    private PublicacionRepositorio publicacionRepositorio;
    @Autowired
    private PerfilRepositorio perfilRepositorio;
    @Autowired
    private ComentarioRepositorio comentarioRepositorio;

    @Autowired
    private FotoServicio fotoServicio;

    //Metodo para mostrar todas las publiciones
    @Transactional(readOnly = true)
    public List<Publicacion> buscarTodas() {
        return publicacionRepositorio.findAll();
    }

    //Metodo para crear una publicacion
    @Transactional
    public void crearNueva(Long idPerfil, String descripcion, MultipartFile foto, Date fecha) throws Exception {
        Publicacion publicacion = new Publicacion();
        if (perfilRepositorio.getById(idPerfil) == null) {
            throw new Exception("No se ha encontrado a ningun perfil con ese identificador.");
        }

        publicacion.setPerfil(perfilRepositorio.getById(idPerfil));  //Obtener perfil
        publicacion.setComentarios(null);
        publicacion.setEggs(null);

        publicacion.setDescripcion(descripcion);

        if (!foto.isEmpty()) {

            publicacion.setFoto(fotoServicio.guardarFoto(foto));

        } else {
            publicacion.setFoto(null);
        }

        publicacion.setFechaDePublicacion(fecha);
        publicacion.setFechaDeBaja(null);
        publicacionRepositorio.save(publicacion);
    }

    //Metodo para modificar una publicacion
    @Transactional
    public Publicacion buscarPorId(Long dni) throws Exception {
        if (perfilRepositorio.getById(dni) == null) {
            throw new Exception("No se ha encontrado a ningun perfil con ese identificador.");
        }
        return publicacionRepositorio.getById(dni);
    }

    //Metodo para crear una publicacion
    @Transactional
    public void Modificar(Long idPublicacion, String descripcion, MultipartFile foto) throws Exception {

        Optional<Publicacion> respuesta = publicacionRepositorio.findById(idPublicacion);

        if (respuesta.isPresent()) {
            
            Publicacion publicacion = respuesta.get();
            

            publicacion.setDescripcion(descripcion);

            if (!foto.isEmpty()) {

                publicacion.setFoto(fotoServicio.guardarFoto(foto));

            } else {
                publicacion.setFoto(null);
            }

            publicacionRepositorio.save(publicacion);
        } else {
            throw new Exception("No se ha encontrado a ningun publicacion para editar.");
        }


    }
}
