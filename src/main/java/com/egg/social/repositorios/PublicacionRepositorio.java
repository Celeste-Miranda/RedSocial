package com.egg.social.repositorios;

import com.egg.social.entidades.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicacionRepositorio extends JpaRepository<Publicacion, Long> {
}
