package com.egg.social.utilidades;

import com.egg.social.excepciones.ExcepcionSpring;

public class Utilidad {

    public static void validarUsuario(String correo, String password, String password2) throws ExcepcionSpring {
        if (correo == null || correo.isEmpty()) {
            throw new ExcepcionSpring("El correo electrónico no puede ser nulo");
        }
        
        /*
        if (correo.matches("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            throw new ExcepcionSpring("El formato del correo electrónico ingresado es inválido");
        }
        */

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

    public static void validarPerfil(String nombre, String apellido) throws ExcepcionSpring {
        if (nombre == null || nombre.isEmpty()) {
            throw new ExcepcionSpring("campo obligatorio");
        }

        if (apellido == null || apellido.isEmpty()) {
            throw new ExcepcionSpring("campo obligatiorio");
        }

//        if (!nombre.matches("^[a-zA-Z]$")) {
//            throw new ExcepcionSpring("formato incorrecto");
//        }
//
//        if (!apellido.matches("^[a-zA-Z]$")) {
//            throw new ExcepcionSpring("formato incorrecto");
//        }
    }
}
