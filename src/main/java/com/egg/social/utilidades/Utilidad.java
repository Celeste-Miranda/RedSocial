package com.egg.social.utilidades;

import com.egg.social.excepciones.ExcepcionSpring;

public class Utilidad {

    public static void validarUsuario(String correo, String password, String password2) throws ExcepcionSpring {
        if (correo == null || correo.isEmpty()) {
            throw new ExcepcionSpring("El correo electrónico no puede ser nulo");
        }

        if (password == null || correo.isEmpty()) {
            throw new ExcepcionSpring("La contraseña no puede ser nula");
        }

        if (password2 == null || correo.isEmpty()) {
            throw new ExcepcionSpring("La repetición de la contraseña no puede ser nulo");
        }

        if (!password.equals(password2)) {
            throw new ExcepcionSpring("Ambas contraseñas deben ser iguales");
        }
    }

    public static void validarPerfil(Long idPerfil, String nombre, String apellido, String residencia) throws ExcepcionSpring {
        if (idPerfil == null) {
            throw new ExcepcionSpring("El ID no puede ser nulo");
        }

        if (nombre == null || nombre.isEmpty()) {
            throw new ExcepcionSpring("El nombre no puede ser nulo");
        }

        if (nombre.matches("^-?[0-9]+$")) {
            throw new ExcepcionSpring("Sólo se admiten caracteres en el nombre");
        }

        if (apellido == null || apellido.isEmpty()) {
            throw new ExcepcionSpring("El apellido no puede ser nulo");
        }

        if (apellido.matches("^-?[0-9]+$")) {
            throw new ExcepcionSpring("Sólo se admiten caracteres en el apellido");
        }

        if (residencia == null || residencia.isEmpty()) {
            throw new ExcepcionSpring("La residencia no puede ser nula");
        }

        if (residencia.matches("^-?[0-9]+$")) {
            throw new ExcepcionSpring("Sólo se admiten caracteres en la residencia");
        }
    }
}
