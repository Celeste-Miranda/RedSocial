package com.egg.social.controladores;

import com.egg.social.excepciones.ExcepcionSpring;
import com.egg.social.servicios.RolServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/roles")
public class RolControlador {

    @Autowired
    private RolServicio rolServicio;
    
    @GetMapping
    public ModelAndView mostrarRoles() {
        ModelAndView mav = new ModelAndView("lista-roles");

        try {
            mav.addObject("roles", rolServicio.buscarRoles());
        } catch (ExcepcionSpring e) {
            mav.addObject("error", e.getMessage());
        }

        return mav;
    }

    @GetMapping("/crear-rol")
    /*@PreAuthorize("hasRole('ADMIN')")*/
    public ModelAndView crearRol() {
        return new ModelAndView("rol-formulario");
    }

    @PostMapping("guardar-rol")
    /*@PreAuthorize("hasRole('ADMIN')")*/
    public RedirectView guardarRol(@RequestParam String nombre) {
        rolServicio.crearRol(nombre);

        return new RedirectView("/");
    }
}
