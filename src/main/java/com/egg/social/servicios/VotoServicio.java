/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.social.servicios;

import com.egg.social.entidades.Perfil;
import com.egg.social.entidades.Publicacion;
import com.egg.social.entidades.Voto;
import com.egg.social.excepciones.ExcepcionSpring;
import com.egg.social.repositorios.VotoRepositorio;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Usuario
 */
@Service
public class VotoServicio {

    @Autowired
    VotoRepositorio votoRepositorio;
    @Autowired
    PerfilServicio perfilServicio;

    @Transactional
    public void votar(Long idUsuario, Publicacion publicacion) throws ExcepcionSpring {

        Perfil perfil = perfilServicio.buscarPorIdUsuario(idUsuario);
        Voto voto = votoRepositorio.findVoto(perfil.getId(), publicacion.getId());
        
        if (voto == null) {
            voto = new Voto();
            voto.setPerfil(perfil);
            voto.setPublicacion(publicacion);
            
            
        } else if (voto.getFechaDeBaja()== null){
            
            voto.setFechaDeBaja(new Date());
        } else {
            
            voto.setFechaDeBaja(null);
            
        }
        


            votoRepositorio.save(voto);

        }
    
    // agregar metodo para contar votos
   

    }
    
  

