package com.egg.social.controladores;

import com.egg.social.entidades.Foto;
import com.egg.social.entidades.Perfil;
import com.egg.social.excepciones.ExcepcionSpring;
import com.egg.social.servicios.PublicacionServicio;
import com.egg.social.entidades.Publicacion;
import com.egg.social.servicios.PerfilServicio;
import java.util.List;
import java.util.Date;
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
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/publicaciones")
public class PublicacionControlador {
    //Importar servicios a utilizar
    @Autowired
    private PublicacionServicio publicacionServicio;
    @Autowired
    private PerfilServicio perfilServicio;

    //Metodo para mostrar todas las publicaciones
    @GetMapping("")
    public ModelAndView buscarTodos() {
        ModelAndView mav = new ModelAndView("publicaciones");
        List<Publicacion> publicaciones = publicacionServicio.buscarTodas();
        mav.addObject("publicaciones", publicaciones);
        return mav;
    }

    //Metodos para crear una publicacion
    //Metodo que vamos a mostrar
    @GetMapping("/nueva")
    public ModelAndView mostrarFormulario() {
        return new ModelAndView("formulario-publicacion");
    }
    //Metodo que lo guarda y redirige
    @PostMapping("/guardar-publicacion")
    public RedirectView guardar(@RequestParam Long dni, @RequestParam(required = false) String descripcion, @RequestParam(required = false) MultipartFile foto, RedirectAttributes redirectAttributes) {
        if (descripcion != null && foto != null) {
            try {
                publicacionServicio.crearNuevaCompleta(dni, descripcion, foto, new Date());
                redirectAttributes.addFlashAttribute("publicacionExitosa", "La publicación se ha realizado satisfactoriamente");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
                return new RedirectView("/publicaciones");
            }
        }
        if ((descripcion == null || descripcion.isEmpty()) && (foto != null)) {
            try {
                publicacionServicio.crearNuevaConFoto(dni, foto, new Date());
                redirectAttributes.addFlashAttribute("publicacionExitosa", "La publicación se ha realizado satisfactoriamente");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
                return new RedirectView("/publicaciones");
            }
        } else {
            if ((foto == null || foto.isEmpty()) && (descripcion != null)) {
                try {
                    publicacionServicio.crearNuevaConDescripcion(dni, descripcion, new Date());
                    redirectAttributes.addFlashAttribute("publicacionExitosa", "La publicación se ha realizado satisfactoriamente");
                } catch (Exception e) {
                    redirectAttributes.addFlashAttribute("error", e.getMessage());
                    return new RedirectView("/publicaciones");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "No se puede crear una publicacion vacia");
                return new RedirectView("/publicaciones");
            }
        }
        return new RedirectView("/publicaciones");
    }
    
    //Metodo para editar una publicacion
    @GetMapping("/editar/{id}")
    public ModelAndView modificar(@PathVariable Long id, HttpSession session) {
        ModelAndView mav = new ModelAndView("usuario-formulario");
        try {
            Publicacion publicacion = publicacionServicio.buscarPorId(id);
            Long idUsuario = publicacion.getPerfil().getUsuario().getId();
            if (!(session.getAttribute("idUsuario").equals(idUsuario))) {
                return new ModelAndView("/");
            }
            mav.addObject("publicacion", publicacion);
            return mav;
        } catch (Exception e) {
            mav = new ModelAndView("/");
            mav.addObject("error", "Error en buscar publicacion por id. --- Mensaje: " + e.getMessage());
        }
        return new ModelAndView("/");
    }
}
