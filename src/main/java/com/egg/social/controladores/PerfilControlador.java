package com.egg.social.controladores;

import com.egg.social.entidades.Invitacion;
import com.egg.social.entidades.Perfil;
import com.egg.social.entidades.Publicacion;
import com.egg.social.excepciones.ExcepcionSpring;
import com.egg.social.repositorios.PerfilRepositorio;
import com.egg.social.servicios.InvitacionServicio;
import com.egg.social.servicios.PerfilServicio;
import com.egg.social.servicios.PublicacionServicio;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/perfil")
public class PerfilControlador {

    @Autowired
    private PerfilServicio perfilServicio;

    @Autowired
    private PublicacionServicio publicacionServicio;

    @Autowired
    private InvitacionServicio invitacionServicio;

    @GetMapping
    public ModelAndView mostrarPerfil(HttpSession sesion) throws ExcepcionSpring {

        ModelAndView mav = new ModelAndView("perfil");
        Perfil perfil = perfilServicio.buscarPerfilPorIdUsuario((Long) sesion.getAttribute("idUsuario"));
        List<Perfil> perfiles = perfilServicio.mostrarTodos();

        List<Publicacion> publicaciones = publicacionServicio.buscarPublicacionesPorPerfil(perfil);

        List<Invitacion> invitacionesPendientes = invitacionServicio.invitacionesRecibidasPendientes(perfil);

        mav.addObject("publicacion", new Publicacion());
        mav.addObject("publicaciones", publicaciones);
        mav.addObject("perfil", perfil);
        mav.addObject("perfilFeed", perfil);
        mav.addObject("perfiles", perfilServicio.listaDeCuatro(perfiles, perfil.getId()));
        mav.addObject("cantidadInvitaciones", invitacionesPendientes.size());

        return mav;

    }

    @GetMapping("/amigos")
    public ModelAndView listaAmigos(HttpSession sesion) throws ExcepcionSpring {

        ModelAndView mav = new ModelAndView("lista-amigos");
        Perfil perfil = perfilServicio.buscarPerfilPorIdUsuario((Long) sesion.getAttribute("idUsuario"));
        List<Perfil> perfiles = perfilServicio.mostrarTodos();
        
        
        List<Invitacion> invitacionesPendientes = invitacionServicio.invitacionesRecibidasPendientes(perfil);

        mav.addObject("perfil", perfil);
        mav.addObject("perfilFeed", perfil);
        mav.addObject("perfiles", perfilServicio.obtenerAmigos((Long) sesion.getAttribute("idUsuario")));
        mav.addObject("cantidadInvitaciones", invitacionesPendientes.size());
        return mav;

    }

//    @GetMapping  
//    public ModelAndView buscarTodos() throws ExcepcionSpring {
//        ModelAndView mav = new ModelAndView("perfil");
//        List<Perfil> perfiles = perfilServicio.mostrarTodos();
//        mav.addObject("perfiles", perfiles);
//        return mav;
//    }
    @GetMapping("/editar/{id}")
    public ModelAndView modificar(@PathVariable Long id, HttpSession session) throws ExcepcionSpring {
        ModelAndView mav = new ModelAndView("perfil-formulario");

        Perfil perfil = perfilServicio.buscarPerfilPorIdUsuario((Long) session.getAttribute("idUsuario"));
        Long idUsuario = perfil.getUsuario().getId();

        if (!(session.getAttribute("idUsuario").equals(idUsuario))) {
            return new ModelAndView(new RedirectView("/"));
            /*Redirigir a una vista que detalle el error 403*/
        }

        mav.addObject("title", "Cargando Perfil");
        mav.addObject("perfil", perfil);
        mav.addObject("listaTecnologia", perfilServicio.obtenerTecnologias());
        mav.addObject("accion", "modificar");

        return mav;
    }

    @GetMapping("/mostrar/{id}")

    public ModelAndView mostrarPerfil(@PathVariable Long id, HttpSession sesion) throws ExcepcionSpring {

        ModelAndView mav = new ModelAndView("perfil");
        Perfil perfil = perfilServicio.buscarPerfilPorIdUsuario((Long) sesion.getAttribute("idUsuario"));
        Perfil perfil2 = perfilServicio.obtenerPerfil(id);
        List<Perfil> perfiles = perfilServicio.mostrarTodos();
        mav.addObject("perfil", perfil);
        mav.addObject("perfilFeed", perfil2);
        mav.addObject("publicaciones", publicacionServicio.buscarPublicacionesPorPerfil(perfil2));
        mav.addObject("perfiles", perfilServicio.listaDeCuatro(perfiles, perfil.getId()));

        return mav;

    }

    @PostMapping("/modificar")
    public RedirectView guardar(@RequestParam Long id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam(required = false) String residencia, @RequestParam List<String> tecnologias, @RequestParam(required = false) MultipartFile foto) throws ExcepcionSpring {
        perfilServicio.modificar(id, nombre, apellido, residencia, tecnologias, foto);

        return new RedirectView("/");
    }
}
