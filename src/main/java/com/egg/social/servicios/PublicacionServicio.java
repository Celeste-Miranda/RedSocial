package com.egg.social.servicios;

import com.egg.social.repositorios.PublicacionRepositorio;
import com.egg.social.entidades.Publicacion;
import com.egg.social.repositorios.ComentarioRepositorio;
import com.egg.social.repositorios.FotoRepositorio;
import com.egg.social.entidades.Foto;
import com.egg.social.repositorios.PerfilRepositorio;
import java.util.Date;
import java.util.List;
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
    private FotoRepositorio fotoRepositorio;
    
    //Metodo para mostrar todas las publiciones
    @Transactional(readOnly = true)
    public List<Publicacion> buscarTodas() {
        return publicacionRepositorio.findAll();
    }
    
    //Metodo para crear una publicacion
    @Transactional
    public void crearNuevaConFoto(Long dni, MultipartFile foto, Date fecha)  throws Exception {
        Publicacion publicacion = new Publicacion();
        if (perfilRepositorio.getById(dni) == null) {
            throw new Exception("No se ha encontrado a ningun perfil con ese identificador.");
        }
        if (foto == null || foto.isEmpty()) {
            throw new Exception("No se puede crear una publicacion vacia.");
        }
        publicacion.setPerfil(perfilRepositorio.getById(dni));  //Obtener perfil
        publicacion.setComentarios(null);
        publicacion.setEggs(null);
        publicacion.setDescripcion(null);
        publicacion.setFoto(FotoServicio.guardar(foto));
        publicacion.setFechaDePublicacion(fecha);
        publicacion.setFechaDeBaja(null);
        publicacionRepositorio.save(publicacion);
    }
    
    //Metodo para modificar una publicacion
    @Transactional
    public Publicacion buscarPorId(Long dni)  throws Exception {
        if (perfilRepositorio.getById(dni) == null) {
            throw new Exception("No se ha encontrado a ningun perfil con ese identificador.");
        }
        return publicacionRepositorio.getById(dni);
    }
}
