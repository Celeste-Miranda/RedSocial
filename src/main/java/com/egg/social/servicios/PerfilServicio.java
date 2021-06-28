package com.egg.social.servicios;

import com.egg.social.entidades.Invitacion;
import com.egg.social.entidades.Perfil;
import com.egg.social.entidades.Usuario;
import com.egg.social.excepciones.ExcepcionSpring;
import com.egg.social.repositorios.PerfilRepositorio;
import com.egg.social.utilidades.Utilidad;
import java.util.ArrayList;
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
    private FotoServicio fotoService;

    @Transactional
    public Perfil crear(Usuario usuario) {
        Perfil perfil = new Perfil();
        perfil.setUsuario(usuario);
        return perfilRepositorio.save(perfil);
    }

    /*
    @Transactional
    public void eliminar(Long id) throws Exception {
        Perfil perfil = buscarPorId(id);

        if (perfil != null) {
            perfilRepositorio.deleteById(id);

        } else {
            throw new Exception("No se encontro Perfil");
        }

    }
     */
    
    @Transactional
    public void modificar(Long idPerfil, String nombre, String apellido, String residencia, MultipartFile foto) throws ExcepcionSpring {
        try {
            Utilidad.validarPerfil(idPerfil, nombre, apellido, residencia);

            perfilRepositorio.modificar(idPerfil, nombre, apellido, fotoService.guardarFoto(foto));
        } catch (ExcepcionSpring e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcepcionSpring("Error al modificar perfil");
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
    
      @Transactional
    public List<Perfil> amigos(Long idPerfil) {

        List<Perfil> amigos = new ArrayList();

        for (Invitacion i : buscarPorId(idPerfil).getInvitacionesEnviadas()) {
            if (i.getAceptada() == true) {
                amigos.add(i.getDestinatario());
            }

        }
        for (Invitacion i : buscarPorId(idPerfil).getInvitacionesRecibidas()) {
            if (i.getAceptada() == true) {
                amigos.add(i.getRemitente());
            }

        }

        return amigos;
    }
}
