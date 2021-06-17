package com.egg.social.controladores;

import com.egg.social.entidades.Perfil;
import com.egg.social.servicios.PerfilServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/perfil")
public class PerfilControlador {

    @Autowired
    private PerfilServicio perfilServicio;

    @GetMapping("/crear")
    public String crear(Model model) {
        model.addAttribute("objeto", new Perfil());
        model.addAttribute("accion", "guardar");

        return "perfil-formulario";
    }

    @GetMapping("/editar/{id}")
    public String modificar(@PathVariable Long id, Model model) {
        model.addAttribute("objeto", perfilServicio.buscarPorId(id));
        model.addAttribute("accion", "editar");
        return "perfil-formulario";
    }
}
