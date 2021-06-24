package com.egg.social.controladores;

import com.egg.social.excepciones.ExcepcionSpring;
import com.egg.social.servicios.PerfilServicio;
import com.egg.social.servicios.UsuarioServicio;
import java.security.Principal;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private PerfilServicio perfilServicio;

    public void authWithHttpServletRequest(HttpServletRequest request, String correo, String password) {
        try {
            request.login(correo, password);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/signin")
    public ModelAndView ingreso(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("login");

        if (error != null) {
            modelAndView.addObject("error", "Correo electrónico o contraseña inválida");
        }

        if (logout != null) {
            modelAndView.addObject("logout", "Ha salido correctamente de la plataforma");
        }

        if (principal != null) {
            modelAndView.setViewName("redirect:/");
        }

        return modelAndView;
    }

    @GetMapping("/signup-get")
    public ModelAndView registro(HttpServletRequest request, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("signup");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            modelAndView.addObject("exito", flashMap.get("exito"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("correo", flashMap.get("correo"));
            modelAndView.addObject("password", flashMap.get("password"));
            modelAndView.addObject("password2", flashMap.get("password2"));
        }

        if (principal != null) {
            modelAndView.setViewName("redirect:/");
        }

        return modelAndView;
    }

    @PostMapping("/signup-post")
    public RedirectView registro(@RequestParam String correo, @RequestParam String password, @RequestParam String password2, RedirectAttributes redirectAttributes) {
        try {
            perfilServicio.crear(usuarioServicio.crearUsuario(correo, password, password2));

            redirectAttributes.addFlashAttribute("exito", "El registro ha sido realizado satisfactoriamente");
        } catch (ExcepcionSpring e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("correo", correo);
            redirectAttributes.addFlashAttribute("password", password);
            redirectAttributes.addFlashAttribute("password2", password2);

            return new RedirectView("/signup-get");
        }

        return new RedirectView("/login");
    }
}
