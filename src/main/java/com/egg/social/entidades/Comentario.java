package com.egg.social.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Comentario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Perfil perfil;
    @ManyToOne
    private Publicacion publicacion;
    @Temporal(TemporalType.DATE)
    private Date fechaDeComentario;
    
    @Temporal(TemporalType.DATE)
    private Date fechaDeBaja;
    

    @PrePersist
    public void prePersist() {
        fechaDeComentario = new Date();
    }

    private String descripcion;

    public Comentario() {
    }

    public Date getFechaDeBaja() {
        return fechaDeBaja;
    }

    public void setFechaDeBaja(Date fechaDeBaja) {
        this.fechaDeBaja = fechaDeBaja;
    }

    
    
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public Date getFechaDeComentario() {
        return fechaDeComentario;
    }

    public void setFechaDeComentario(Date fechaDeComentario) {
        this.fechaDeComentario = fechaDeComentario;
    }
}
