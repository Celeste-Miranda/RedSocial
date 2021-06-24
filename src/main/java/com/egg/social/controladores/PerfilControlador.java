package com.egg.social.controladores;

import com.egg.social.entidades.Perfil;
import com.egg.social.enumeraciones.Residencia;
import com.egg.social.servicios.PerfilServicio;
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
    
    

    @GetMapping("/editar/{idPerfil}")
    public ModelAndView modificar(@PathVariable Long idPerfil, HttpSession session) {
       
        Perfil perfil = perfilServicio.buscarPorIdUsuario((Long)session.getAttribute("idUsuario"));
        Long idUsuario = perfil.getUsuario().getId();
        
        if (!(session.getAttribute("idUsuario").equals(idUsuario))) {
            return new ModelAndView (new RedirectView ("/"));
          
        }
      
        ModelAndView mav = new ModelAndView ("perfil-formulario");
         mav.addObject("title", "Cargando Perfil");
        mav.addObject("perfil",perfil );
        mav.addObject("accion", "modificar");
        
        return mav;
    }

    @PostMapping("/modificar")
    public RedirectView guardar(@RequestParam Long id, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam(required = false) Residencia residencia, @RequestParam(required = false) MultipartFile foto, HttpSession session) throws Exception {
        
        perfilServicio.modificar(id, nombre, apellido);
        
        if (!foto.isEmpty()) {
           
            perfilServicio.modificarFoto(foto, id);
        }
        

        return new RedirectView("/");

    }

}
