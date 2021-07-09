package com.egg.social.controladores;

import com.egg.social.entidades.Perfil;
import com.egg.social.excepciones.ExcepcionSpring;
import com.egg.social.servicios.PerfilServicio;
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
    
    

    @GetMapping("/") //Editar bien el metodo de pasar Usuario y try
    public ModelAndView principal(HttpSession sesion) throws ExcepcionSpring {
        
        
        ModelAndView mav = new ModelAndView("inicio");
        List<Perfil> perfiles = perfilServicio.mostrarTodos();
        mav.addObject("perfiles", perfiles);
        mav.addObject("perfil", perfilServicio.buscarPerfilPorIdUsuario((Long) sesion.getAttribute("idUsuario")));
        mav.addObject("usuario", perfilServicio.buscarPerfilPorIdUsuario((Long) sesion.getAttribute("idUsuario")).getUsuario());
        
        return  mav;
    }

  
}
