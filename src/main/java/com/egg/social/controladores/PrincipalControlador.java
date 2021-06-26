package com.egg.social.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PrincipalControlador {

    @GetMapping("/")
    public ModelAndView principal() {
        return new ModelAndView("inicio");
    }

    /*
    @GetMapping("/")
    public ModelAndView principal() {
        return new ModelAndView("usuario-formulario");
    }

    @GetMapping("/")
    public ModelAndView principal() {
        return new ModelAndView("rol-formulario");
    }

    @GetMapping("/")
    public ModelAndView principal() {
        return new ModelAndView("lista-roles");
    }

    @GetMapping("/")
    public ModelAndView principal() {
        return new ModelAndView("perfil-formulario");
    }
     */
}
