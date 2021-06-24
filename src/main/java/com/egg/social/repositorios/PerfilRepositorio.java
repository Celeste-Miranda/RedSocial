package com.egg.social.repositorios;

import com.egg.social.entidades.Perfil;
import com.egg.social.enumeraciones.Residencia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepositorio extends JpaRepository<Perfil, Long> {
    
    
    @Modifying
    @Query("UPDATE Perfil p SET p.nombre = :nombre, p.apellido = :apellido  WHERE p.id = :id")
     void modificar(@Param("id") Long id, @Param("nombre") String nombre, 
             @Param("apellido")String apellido );
     
     @Query("SELECT p FROM Perfil p WHERE p.apellido = :apellido AND p.nombre = :nombre")
     List<Perfil> buscarPorNombreYApellido(@Param("apellido") String apellido ,@Param("nombre")String nombre); 
     
     @Query("SELECT p FROM Perfil p WHERE p.usuario.id = :idUsuario")
     Perfil buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);
}
