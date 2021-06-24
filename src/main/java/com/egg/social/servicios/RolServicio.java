package com.egg.social.servicios;

import com.egg.social.entidades.Rol;
import com.egg.social.excepciones.ExcepcionSpring;
import com.egg.social.repositorios.RolRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolServicio {

    @Autowired
    private RolRepositorio rolRepositorio;

    @Transactional
    public Rol crearRol(String nombre) {
        Rol rol = new Rol();

        rol.setNombre(nombre);

        return rolRepositorio.save(rol);
    }

    public List<Rol> buscarRoles() throws ExcepcionSpring {
        try {
            List<Rol> roles = rolRepositorio.findAll();

            if (!roles.isEmpty()) {
                return roles;
            } else {
                throw new ExcepcionSpring("No existen roles");
            }
        } catch (ExcepcionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExcepcionSpring("Error al buscar roles");
        }
    }
}
