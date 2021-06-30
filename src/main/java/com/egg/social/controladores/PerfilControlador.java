package com.egg.social.controladores;

import com.egg.social.entidades.Perfil;
import com.egg.social.excepciones.ExcepcionSpring;
import com.egg.social.servicios.PerfilServicio;
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

    

    @GetMapping  
    public ModelAndView buscarTodos() {
        ModelAndView mav = new ModelAndView("perfil");
        List<Perfil> perfiles = perfilServicio.mostrarTodos();
        mav.addObject("perfiles", perfiles);
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView modificar(@PathVariable Long id, HttpSession session) {
        ModelAndView mav = new ModelAndView("perfil-formulario");

        Perfil perfil = perfilServicio.buscarPorIdUsuario((Long) session.getAttribute("idUsuario"));
        Long idUsuario = perfil.getUsuario().getId();

        if (!(session.getAttribute("idUsuario").equals(idUsuario))) {
            return new ModelAndView(new RedirectView("/"));
            /*Redirigir a una vista que detalle el error 403*/
        }

        mav.addObject("title", "Cargando Perfil");
        mav.addObject("perfil", perfil);
        mav.addObject("accion", "modificar");

        return mav;
    }

    @PostMapping("/modificar")
    public RedirectView guardar(@RequestParam Long id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam(required = false) String residencia, @RequestParam(required = false) MultipartFile foto, HttpSession session) throws ExcepcionSpring {
        perfilServicio.modificar(id, nombre, apellido, residencia, foto);

        return new RedirectView("/");
    }
}
