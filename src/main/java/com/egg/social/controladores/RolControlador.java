package com.egg.social.controladores;

import com.egg.social.entidades.Rol;
import com.egg.social.excepciones.ExcepcionSpring;
import com.egg.social.servicios.RolServicio;
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
/*@PreAuthorize("hasRole('ADMIN')")*/
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
    
    public ModelAndView crearRol() {
        ModelAndView mav = new ModelAndView("rol-formulario");
        mav.addObject("rol", new Rol() );
        mav.addObject("action", "guardar-rol");
        
        return mav;
    }

    @PostMapping("/guardar-rol")
    /*@PreAuthorize("hasRole('ADMIN')")*/
    public RedirectView guardarRol(@RequestParam String nombre) {
        rolServicio.crearRol(nombre);

        return new RedirectView("/");
    }
}
