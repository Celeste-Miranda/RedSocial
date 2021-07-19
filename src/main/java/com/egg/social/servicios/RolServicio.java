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
    public Rol crearRol(String nombre) throws ExcepcionSpring {

        try {
            if (rolRepositorio.findByNombre(nombre) != null) {
                throw new ExcepcionSpring("Ya existe " + nombre + " en la lista de Roles no se puede guardar");
            }

            Rol rol = new Rol();

            rol.setNombre(nombre);

            return rolRepositorio.save(rol);
        } catch (ExcepcionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExcepcionSpring("Error al crear rol");
        }

    }

    @Transactional(readOnly = true)
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

    @Transactional
    public void eliminar(Long id) {
        rolRepositorio.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Rol buscarRol(String nombre) throws ExcepcionSpring {
        try {
            /*Probar sentencia simplificada*/
            Rol rol = rolRepositorio.findByNombre(nombre);

            if (rol != null) {
                return rol;
            } else {
                throw new ExcepcionSpring("No existe ese rol");
            }
        } catch (ExcepcionSpring e) {
            throw e;
        } catch (Exception e) {
            throw new ExcepcionSpring("Error al buscar roles");
        }
    }
}
