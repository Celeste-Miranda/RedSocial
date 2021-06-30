package com.egg.social.controladores;

import com.egg.social.entidades.Perfil;
import com.egg.social.servicios.PerfilServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PrincipalControlador {
    
    
    
    @Autowired
    private PerfilServicio perfilServicio;
    

    @GetMapping("/")
    public ModelAndView principal() {
        
         ModelAndView mav = new ModelAndView("inicio");
        List<Perfil> perfiles = perfilServicio.mostrarTodos();
        mav.addObject("perfiles", perfiles);
        
        return  mav;
    }

  
}
