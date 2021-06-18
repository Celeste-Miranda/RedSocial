package com.egg.social.entidades;

import com.egg.social.enumeraciones.Residencia;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Perfil implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    @Enumerated(EnumType.STRING)
    private Residencia residencia;
    @OneToOne
    private Usuario usuario;
    @OneToMany(mappedBy = "perfil")
    private List<Publicacion> publicaciones;
    @OneToMany(mappedBy = "remitente")
    private List<Invitacion> invitacionesRecibidas;
    @OneToMany(mappedBy = "destinatario")
    private List<Invitacion> invitacionesEnviadas;
    @OneToOne
    private Foto foto;

    public Perfil() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Residencia getResidencia() {
        return residencia;
    }

    public void setResidencia(Residencia residencia) {
        this.residencia = residencia;
    }

    public List<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(List<Publicacion> publicaciones) {
        this.publicaciones = publicaciones;
    }

    public List<Invitacion> getInvitacionesRecibidas() {
        return invitacionesRecibidas;
    }

    public void setInvitacionesRecibidas(List<Invitacion> invitacionesRecibidas) {
        this.invitacionesRecibidas = invitacionesRecibidas;
    }

    public List<Invitacion> getInvitacionesEnviadas() {
        return invitacionesEnviadas;
    }

    public void setInvitacionesEnviadas(List<Invitacion> invitacionesEnviadas) {
        this.invitacionesEnviadas = invitacionesEnviadas;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
