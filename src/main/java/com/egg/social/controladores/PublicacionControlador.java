package com.egg.social.controladores;

import com.egg.social.entidades.Foto;
import com.egg.social.servicios.PublicacionServicio;
import com.egg.social.entidades.Publicacion;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class PublicacionControlador {
    //Importar servicios a utilizar
    @Autowired
    private PublicacionServicio publicacionServicio;
    
    //Metodo para mostrar todas las publicaciones
    @GetMapping("")
    public ModelAndView buscarTodos() {
        ModelAndView mav = new ModelAndView("publicaciones");
        List<Publicacion> publicaciones = publicacionServicio.buscarTodas();
        mav.addObject("publicaciones", publicaciones);
        return mav;
    }
    
    //Metodos para crear un usuario
    //Metodo que vamos a mostrar
    @GetMapping("publicar")
    public ModelAndView mostrarFormulario() {
        return new ModelAndView("usuario-formulario");
    }
    //Metodo que lo guarda y redirige
    @PostMapping("/guardar-publicacion")
    public RedirectView guardar(@RequestParam Long dni, @RequestParam String descripcion, @RequestParam Foto foto) {
        Calendar cal = Calendar.getInstance();
        publicacionServicio.crearNueva(dni, descripcion, foto, (Date) (cal));   //Pasar a date
        return new RedirectView("/usuarios/ver-todos");
    }
	}
}
