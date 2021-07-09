package com.egg.social.repositorios;

import com.egg.social.entidades.Perfil;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepositorio extends JpaRepository<Perfil, Long> {

    /*
    @Modifying
    @Query("UPDATE Perfil p SET p.nombre = :nombre, p.apellido = :apellido, p.residencia = :residencia , p.foto = :foto  WHERE p.id = :id")
    void modificar(@Param("id") Long id, @Param("nombre") String nombre, @Param("apellido") String apellido, @Param("residencia") String residencia, @Param("foto") String foto);
    */
    
    /*
    @Query("SELECT p FROM Perfil p WHERE p.id = :idPerfil AND p.fechaDeBaja IS NULL")
    Perfil buscarPerfilPorId(@Param("idPerfil") Long idPerfil);
    */
    
    @Query("SELECT p FROM Perfil p WHERE p.usuario.id = :idUsuario")
    Perfil buscarPerfilPorIdDeUsuario(@Param("idUsuario") Long idUsuario);
    
    @Query("SELECT p FROM Perfil p WHERE p.apellido = :apellido AND p.nombre = :nombre")
    List<Perfil> buscarPorNombreYApellido(@Param("apellido") String apellido, @Param("nombre") String nombre);

    @Query("SELECT p FROM Perfil p WHERE p.usuario.fechaDeBaja IS NULL")
    List<Perfil> buscarPerfiles();
//    
//    @Modifying
//    @Query("UPDATE Perfil p SET p.nombre = :nombre, p.apellido = :apellido, p.residencia = :residencia WHERE p.id = :id")
//    void modificarSinFoto(@Param("id") Long id, @Param("nombre") String nombre, @Param("apellido") String apellido,@Param("residencia")String residencia);

}
