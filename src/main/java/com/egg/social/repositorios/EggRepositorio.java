package com.egg.social.repositorios;

import com.egg.social.entidades.Egg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EggRepositorio extends JpaRepository<Egg, Long> {
}
