package com.egg.social.controladores;

import com.egg.social.entidades.Invitacion;
import com.egg.social.entidades.Perfil;
import com.egg.social.entidades.Publicacion;
import com.egg.social.entidades.Rol;
import com.egg.social.excepciones.ExcepcionSpring;
import com.egg.social.servicios.InvitacionServicio;
import com.egg.social.servicios.PerfilServicio;
import com.egg.social.servicios.PublicacionServicio;
import com.egg.social.servicios.RolServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/roles")
@PreAuthorize("hasRole('ADMIN')")
public class RolControlador {

    @Autowired
    private RolServicio rolServicio;

    @Autowired
    private PerfilServicio perfilServicio;

    @Autowired
    private PublicacionServicio publicacionServicio;

    @Autowired
    private InvitacionServicio invitacionServicio;

    @GetMapping
    public ModelAndView mostrarRoles(HttpSession sesion) {
        ModelAndView mav = new ModelAndView("lista-roles");

        try {

            Perfil perfil = perfilServicio.buscarPerfilPorIdUsuario((Long) sesion.getAttribute("idUsuario"));
            List<Perfil> perfiles = perfilServicio.mostrarTodos();

            List<Publicacion> publicaciones = publicacionServicio.buscarPublicacionesPorPerfil(perfil);

            List<Invitacion> invitacionesPendientes = invitacionServicio.invitacionesRecibidasPendientes(perfil);

            mav.addObject("publicacion", new Publicacion());
            mav.addObject("publicaciones", publicaciones);

            mav.addObject("perfil", perfil);
            mav.addObject("perfilFeed", perfil);
            mav.addObject("perfiles", perfilServicio.listaDeCuatro(perfiles, perfil.getId()));
            mav.addObject("amigos", perfilServicio.obtenerAmigos((Long) sesion.getAttribute("idUsuario")));
            mav.addObject("cantidadInvitaciones", invitacionesPendientes.size());

            mav.addObject("roles", rolServicio.buscarRoles());
        } catch (ExcepcionSpring e) {
            mav.addObject("error", e.getMessage());
            mav.setViewName("redirect:/error");

        }

        return mav;
    }

    @GetMapping("/crear")

    public ModelAndView crearRol() {
        ModelAndView mav = new ModelAndView("rol-formulario");
        mav.addObject("rol", new Rol());
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
