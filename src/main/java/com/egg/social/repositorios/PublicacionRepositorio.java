package com.egg.social.repositorios;

import com.egg.social.entidades.Perfil;
import com.egg.social.entidades.Publicacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicacionRepositorio extends JpaRepository<Publicacion, Long> {

    List<Publicacion> findByFechaDeBajaIsNull();

    @Query("SELECT p FROM Publicacion p WHERE p.perfil.id = :idPerfil and p.fechaDeBaja IS NULL")
    List<Publicacion> publicacionesPorPerfil(@Param("idPerfil") Long idPerfil);

    List<Publicacion> findByPerfil_IdIn(List<Long> amigos);

    @Query("SELECT p FROM Publicacion p WHERE p.id = :idPublicacion")
    Publicacion buscarPublicacionPorId(@Param("idPublicacion") Long idPublicacion);
    
    List<Publicacion> findByPerfil(Perfil perfil);
}
