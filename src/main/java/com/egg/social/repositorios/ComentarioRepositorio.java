package com.egg.social.repositorios;

import com.egg.social.entidades.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepositorio extends JpaRepository<Comentario, Long> {
    
  //  select c from comentario c where c.publicacion.id =: idPublicaion and fechaDeBaja is null;  
    
    
    
    
}
