package com.egg.social.servicios;

import com.egg.social.entidades.Foto;
import com.egg.social.entidades.Perfil;
import com.egg.social.enumeraciones.Residencia;
import com.egg.social.repositorios.PerfilRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PerfilServicio {

    @Autowired
    private PerfilRepositorio perfilRepositorio;

    @Transactional
    public Perfil crear(String nombre, String apellido) throws Exception {

        Perfil perfil = new Perfil();
        perfil.setApellido(apellido);
        perfil.setNombre(nombre);

        perfilRepositorio.save(perfil);
        return perfil;

    }

    @Transactional
    public void eliminar(Long id) {

        perfilRepositorio.deleteById(id);

    }
    
    /*
    @Transactional
    public void modificar(Long id, String nombre, String apellido, Residencia residencia, Foto foto) {
        perfilRepositorio.modificar(id, nombre, apellido, residencia, foto);
    }
    */

    @Transactional(readOnly = true)
    public List<Perfil> mostrarTodos() {
        return perfilRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public Perfil buscarPorId(Long id) {
        return perfilRepositorio.findById(id).orElse(null);
    }
    
    /*
    @Transactional(readOnly = true)
    public List<Perfil> buscarPorNombreYApellido(String nombre, String apellido) {
        return perfilRepositorio.buscarPorNombreYApellido(apellido, nombre);
    }
    */
}
