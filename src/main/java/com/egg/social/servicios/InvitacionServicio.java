package com.egg.social.servicios;

import com.egg.social.entidades.Invitacion;
import com.egg.social.entidades.Perfil;
import com.egg.social.excepciones.ExcepcionSpring;
import com.egg.social.repositorios.InvitacionRepositorio;
import com.egg.social.repositorios.PerfilRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvitacionServicio {
    
    @Autowired
    InvitacionRepositorio invitacionRepositorio;
    @Autowired
    PerfilRepositorio perfilrepositorio;
    @Autowired
    PerfilServicio perfilServicio;
    
    
    @Transactional
   public Invitacion crear (Perfil remitente, Perfil destinatario ){
       
       Invitacion invitacion = new Invitacion();
       
       invitacion.setRemitente(remitente);
       invitacion.setDestinatario(destinatario);
       invitacion.setAceptada(false);
       
       remitente.getInvitacionesEnviadas().add(invitacion);
       destinatario.getInvitacionesRecibidas().add(invitacion);
       
       
       perfilrepositorio.save(remitente);
       perfilrepositorio.save(destinatario);
       
       invitacionRepositorio.save(invitacion);
       return invitacion;
   }
    
//   @Transactional
//   public void eliminar (Long idInvitacion) throws ExcepcionSpring{
//       Invitacion invitacion = invitacionRepositorio.findById(idInvitacion).orElse(null);
//       
//       if (invitacion != null) {
//           invitacionRepositorio.deleteById(idInvitacion);
//       }else{
//           throw new ExcepcionSpring ("No se encontro Invitación");
//       }
//       
//   }
   
   @Transactional
   public void aceptarInvitacion (Long idInvitacion) throws ExcepcionSpring{
       
       Invitacion invitacion = invitacionRepositorio.findById(idInvitacion).orElse(null);
       
       if(invitacion!=null){
           invitacion.setAceptada(true);
           
       }else {
           throw new ExcepcionSpring ("invitacion no se encontro");
       }
       
       invitacionRepositorio.save(invitacion);
       
   }
   
   @Transactional
   public void rechazarInvitacion(Long idInvitacion) throws ExcepcionSpring{
       
       Invitacion invitacion = invitacionRepositorio.findById(idInvitacion).orElse(null);
       
        if(invitacion!=null){
            invitacion.setFechaDeBaja(new Date());
           
       }else {
           throw new ExcepcionSpring ("invitacion no se encontro");
       }
       
        invitacionRepositorio.save(invitacion);
   }
   
       
    @Transactional
    public List <Invitacion> invitacionesRecibidasPendientes(Long idPerfil){
        List<Invitacion> recibidasPendientes = new ArrayList();
        for (Invitacion i : perfilServicio.buscarPorId(idPerfil).getInvitacionesRecibidas()) {
            if (i.getAceptada()==false) {
                recibidasPendientes.add(i);
            }
            
        }
         return recibidasPendientes;
        
    }
    
    @Transactional
    public List <Invitacion> invitacionesEnviadasPendientes(Long id){
        List<Invitacion> enviadasPendientes = new ArrayList();
        for (Invitacion i : perfilServicio.buscarPorId(id).getInvitacionesEnviadas()) {
        
            if (i.getAceptada()== false) {
                enviadasPendientes.add(i);
            }
            
        }
         return enviadasPendientes;
        
    }
   
   }
   
  

