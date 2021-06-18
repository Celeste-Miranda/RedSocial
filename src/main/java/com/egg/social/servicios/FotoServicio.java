package com.egg.social.servicios;

import com.egg.social.entidades.Foto;
import com.egg.social.excepciones.ExcepcionSpring;
import com.egg.social.repositorios.FotoRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoServicio {

    @Autowired
    private FotoRepositorio fotoRepositorio;

    @Transactional
    public Foto guardarFoto(MultipartFile archivo) throws ExcepcionSpring {
        if (!archivo.isEmpty()) {
            try {
                Foto foto = new Foto();

                foto.setNombre(archivo.getName());
                foto.setMime(archivo.getContentType());
                foto.setContenido(archivo.getBytes());

                return fotoRepositorio.save(foto);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ExcepcionSpring("Error al guardar portada");
            }
        }

        return null;
    }

    @Transactional
    public Foto actualizarFoto(Long idFoto, MultipartFile archivo) throws ExcepcionSpring {
        try {
            Foto foto = new Foto();

            if (idFoto != null) {
                Optional<Foto> respuesta = fotoRepositorio.findById(idFoto);

                if (respuesta.isPresent()) {
                    foto = respuesta.get();
                }
            }

            foto.setNombre(archivo.getName());
            foto.setMime(archivo.getContentType());
            foto.setContenido(archivo.getBytes());

            return fotoRepositorio.save(foto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcepcionSpring("Error al actualizar foto");
        }
    }
}
