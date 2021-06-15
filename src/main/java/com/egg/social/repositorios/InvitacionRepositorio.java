package com.egg.social.repositorios;

import com.egg.social.entidades.Invitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitacionRepositorio extends JpaRepository<Invitacion, Long> {
}
