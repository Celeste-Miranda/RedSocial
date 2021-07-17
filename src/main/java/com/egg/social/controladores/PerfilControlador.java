package com.egg.social.controladores;

import com.egg.social.entidades.Comentario;
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
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
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

        mav.addObject("perfil", perfil);
        mav.addObject("perfiles", perfilServicio.listaDeCuatro(perfiles, perfil.getId()));
        mav.addObject("perfilFeed", perfil);
        mav.addObject("publicacion", new Publicacion());
        mav.addObject("publicaciones", publicaciones);
        mav.addObject("cantidadInvitaciones", invitacionesPendientes.size());
        mav.addObject("amigos", perfilServicio.obtenerAmigos((Long) sesion.getAttribute("idUsuario")));
        mav.addObject("comentario", new Comentario());
        mav.addObject("amistad", true);


        return mav;

    }

    @GetMapping("/amigos")
    public ModelAndView listaAmigos(HttpSession sesion) throws ExcepcionSpring {

        ModelAndView mav = new ModelAndView("lista-amigos");
        Perfil perfil = perfilServicio.buscarPerfilPorIdUsuario((Long) sesion.getAttribute("idUsuario"));

        List<Invitacion> invitacionesPendientes = invitacionServicio.invitacionesRecibidasPendientes(perfil);

        mav.addObject("perfil", perfil);
        mav.addObject("perfilFeed", perfil);
        mav.addObject("perfiles", perfilServicio.obtenerAmigos((Long) sesion.getAttribute("idUsuario")));
        mav.addObject("cantidadInvitaciones", invitacionesPendientes.size());
        return mav;

    }

    @GetMapping("/buscados")
    public ModelAndView listaBuscados(HttpSession sesion, @RequestParam(required = false) String nombreYApellido) throws ExcepcionSpring {

        ModelAndView mav = new ModelAndView("lista-buscados");

        try {
            Perfil perfil = perfilServicio.buscarPerfilPorIdUsuario((Long) sesion.getAttribute("idUsuario"));
            List<Invitacion> invitacionesPendientes = invitacionServicio.invitacionesRecibidasPendientes(perfil);

            List<Perfil> perfilesBuscados = perfilServicio.buscarPorNombreYApellido(nombreYApellido);

            mav.addObject("perfil", perfil);
            mav.addObject("perfiles", perfilesBuscados);
            mav.addObject("perfilFeed", perfil);
            mav.addObject("exito", "busqueda exitosa");
            mav.addObject("cantidadInvitaciones", invitacionesPendientes.size());
        } catch (ExcepcionSpring e) {
            Perfil perfil = perfilServicio.buscarPerfilPorIdUsuario((Long) sesion.getAttribute("idUsuario"));
            List<Invitacion> invitacionesPendientes = invitacionServicio.invitacionesRecibidasPendientes(perfil);
            mav.addObject("error", e.getMessage());
            mav.addObject("perfil", perfil);
            mav.addObject("perfilFeed", perfil);
            mav.addObject("perfiles", new ArrayList<>());
            mav.addObject("cantidadInvitaciones", invitacionesPendientes.size());

        }
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView modificar(@PathVariable Long id, HttpSession session, HttpServletRequest request) throws ExcepcionSpring {
        ModelAndView mav = new ModelAndView("perfil-formulario");

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("error", flashMap.get("error"));
        }

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
        Invitacion invitacion = invitacionServicio.invitacionesEntreDosPerfiles(perfil, perfil2);

        mav.addObject("perfil", perfil);
        mav.addObject("perfilFeed", perfil2);
        mav.addObject("publicaciones", publicacionServicio.buscarPublicacionesPorPerfil(perfil2));
        mav.addObject("perfiles", perfilServicio.listaDeCuatro(perfiles, perfil.getId()));
        mav.addObject("invitar", invitacionServicio.comprobarInvitacion(perfil, perfil2));
        mav.addObject("invitacion", invitacion);
        mav.addObject("amistad", invitacionServicio.sonAmigos(perfil, perfil2));
        mav.addObject("amigos", perfilServicio.obtenerAmigos((Long) sesion.getAttribute("idUsuario")));
        mav.addObject("comentario", new Comentario());
        return mav;

    }

    @PostMapping("/modificar")
    public RedirectView guardar(@RequestParam Long id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String residencia, @RequestParam(required = false) List<String> tecnologias, @RequestParam(required = false) MultipartFile foto, RedirectAttributes redirectAttributes) throws ExcepcionSpring {

        try {
            perfilServicio.modificar(id, nombre, apellido, residencia, tecnologias, foto);
        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return new RedirectView("/perfil/editar/" + id);

        }

        return new RedirectView("/");
    }
}
