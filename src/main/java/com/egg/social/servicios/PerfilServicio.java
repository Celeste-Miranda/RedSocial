package com.egg.social.servicios;

import com.egg.social.entidades.Foto;
import com.egg.social.entidades.Perfil;
import com.egg.social.entidades.Usuario;
import com.egg.social.enumeraciones.Residencia;
import com.egg.social.excepciones.ExcepcionSpring;
import com.egg.social.repositorios.PerfilRepositorio;
import com.egg.social.repositorios.UsuarioRepositorio;
import com.egg.social.utilidades.Utilidad;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PerfilServicio {

    @Autowired
    private PerfilRepositorio perfilRepositorio;

    @Autowired
    private FotoServicio fotoService;

    @Transactional
    public Perfil crear(Usuario usuario) {
        Perfil perfil = new Perfil();
        perfil.setUsuario(usuario);
        return perfilRepositorio.save(perfil);
    }

    @Transactional
    public void eliminar(Long id) throws Exception {
        Perfil perfil = buscarPorId(id);

        if (perfil != null) {
            perfilRepositorio.deleteById(id);

        } else {
            throw new Exception("No se encontro Perfil");
        }

    }

    @Transactional
    public void modificar(Long idPerfil, String nombre, String apellido) throws Exception {

        try {
            Utilidad.validarPerfil(nombre, apellido);
            perfilRepositorio.modificar(idPerfil, nombre, apellido);
        } catch (Exception e) {
            throw new Exception("Error al completar Perfil "+ e.getMessage());
        }

    }

    @Transactional
    public void modificarFoto(MultipartFile archivo, Long id) throws ExcepcionSpring {

        Perfil perfil = buscarPorId(id);

        if (perfil != null) {
            Long idFoto = null;

            if (perfil.getFoto().getId() != null) {
                idFoto = perfil.getFoto().getId();

                Foto foto = fotoService.actualizarFoto(idFoto, archivo);

                perfil.setFoto(foto);

                perfilRepositorio.save(perfil);
            }
        } else {

            throw new ExcepcionSpring("No se encontro el perfil");
        }

    }

    @Transactional(readOnly = true)
    public Perfil buscarPorIdUsuario(Long idUsuario) {
        return perfilRepositorio.buscarPorIdUsuario(idUsuario);
    }

    @Transactional(readOnly = true)
    public List<Perfil> mostrarTodos() {
        return perfilRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public Perfil buscarPorId(Long id) {
        return perfilRepositorio.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Perfil> buscarPorNombreYApellido(String nombre, String apellido) {
        return perfilRepositorio.buscarPorNombreYApellido(apellido, nombre);
    }

}
