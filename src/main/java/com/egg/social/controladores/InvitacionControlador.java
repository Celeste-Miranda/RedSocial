package com.egg.social.controladores;

import com.egg.social.entidades.Invitacion;
import com.egg.social.entidades.Perfil;
import com.egg.social.excepciones.ExcepcionSpring;
import com.egg.social.servicios.InvitacionServicio;
import com.egg.social.servicios.PerfilServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/invitaciones")
public class InvitacionControlador {

    @Autowired
    PerfilServicio perfilService;
    @Autowired
    InvitacionServicio invitacionService;

    @GetMapping("/lista")
    public ModelAndView mostrarInvitacionPerdinete(HttpSession session) {
        ModelAndView mav = new ModelAndView("lista-invitaciones");

        Perfil perfil = perfilService.buscarPorIdUsuario((Long) session.getAttribute("idUsuadio"));

        List<Invitacion> invitacionesPendientes = invitacionService.invitacionesRecibidasPendientes(perfil.getId());

        mav.addObject("Lista", invitacionesPendientes);
        mav.addObject("perfil", perfil);

        return mav;
    }

    @PostMapping("/aceptar/{idInvitacion}")
    public RedirectView aceptarInvitacion(@PathVariable Long idInvitacion) throws ExcepcionSpring {
        invitacionService.aceptarInvitacion(idInvitacion);

        return new RedirectView("/invitaciones/lista");
    }

    @PostMapping("/rechazar/{idInvitacion}")
    public RedirectView rechazarInvitacion(@PathVariable Long idInvitacion) throws ExcepcionSpring {
        invitacionService.rechazarInvitacion(idInvitacion);

        return new RedirectView("/invitaciones/lista");
    }
}
