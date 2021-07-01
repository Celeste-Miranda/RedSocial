package com.egg.social.repositorios;

import com.egg.social.entidades.Invitacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitacionRepositorio extends JpaRepository<Invitacion, Long> {
    
    @Query("SELECT i.remitente.id FROM Invitacion i WHERE i.aceptada = true AND i.fechaDeBaja is NULL AND i.destinatario.id =:id")
    List<Long> listaRemitente(@Param ("id") Long id);
    
    @Query("SELECT i.destinatario.id FROM Invitacion i WHERE i.aceptada = true AND i.fechaDeBaja is NULL AND i.remitente.id =:id")
    List<Long> listaDestinatario(@Param ("id") Long id);
}
