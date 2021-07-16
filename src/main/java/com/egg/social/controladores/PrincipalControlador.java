package com.egg.social.controladores;

import com.egg.social.entidades.Invitacion;
import com.egg.social.entidades.Perfil;
import com.egg.social.entidades.Publicacion;
import com.egg.social.excepciones.ExcepcionSpring;
import com.egg.social.servicios.InvitacionServicio;
import com.egg.social.servicios.PerfilServicio;
import com.egg.social.servicios.PublicacionServicio;
import com.egg.social.servicios.UsuarioServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PrincipalControlador {

    @Autowired
    private PerfilServicio perfilServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private PublicacionServicio publicacionServicio;
    
    @Autowired
    private InvitacionServicio invitacionServicio;

    @GetMapping("/") //Editar bien el metodo de pasar Usuario y try
    public ModelAndView principal(HttpSession sesion) throws ExcepcionSpring {

        ModelAndView mav = new ModelAndView("inicio");

        Perfil perfil = perfilServicio.buscarPerfilPorIdUsuario((Long) sesion.getAttribute("idUsuario"));
        List<Perfil> perfiles = perfilServicio.mostrarTodos();
        List<Invitacion> invitacionesPendientes = invitacionServicio.invitacionesRecibidasPendientes(perfil);

        mav.addObject("perfiles", perfilServicio.listaDeCuatro(perfiles, perfil.getId()));
        mav.addObject("perfil", perfil);
        mav.addObject("publicacion", new Publicacion());
        mav.addObject("publicaciones", publicacionServicio.buscarPublicaciones((Long) sesion.getAttribute("idUsuario")));
        mav.addObject("perfilFeed", perfil);
        mav.addObject("usuario", perfilServicio.buscarPerfilPorIdUsuario((Long) sesion.getAttribute("idUsuario")).getUsuario());
        mav.addObject("amigos", perfilServicio.obtenerAmigos((Long) sesion.getAttribute("idUsuario")));
        mav.addObject("cantidadInvitaciones", invitacionesPendientes.size());

        return mav;
    }

}
