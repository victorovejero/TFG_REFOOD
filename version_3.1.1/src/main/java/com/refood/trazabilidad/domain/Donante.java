package com.refood.trazabilidad.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Donante.
 */
@Entity
@Table(name = "donante")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Donante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_donante", nullable = false)
    private String idDonante;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "categoria", nullable = false)
    private String categoria;

    @NotNull
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @NotNull
    @Column(name = "codigo_postal", nullable = false)
    private String codigoPostal;

    @NotNull
    @Column(name = "provincia", nullable = false)
    private String provincia;

    @NotNull
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "responsable", nullable = false)
    private String responsable;

    @NotNull
    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;

    @Column(name = "fecha_baja")
    private LocalDate fechaBaja;

    @Column(name = "comentarios")
    private String comentarios;

    @NotNull
    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "donante")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "alSals", "tupper", "donante", "tipoAl" }, allowSetters = true)
    private Set<AlEnt> alEnts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "donantes", "benefs", "voluntarios" }, allowSetters = true)
    private Nucleo nucleo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Donante id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdDonante() {
        return this.idDonante;
    }

    public Donante idDonante(String idDonante) {
        this.setIdDonante(idDonante);
        return this;
    }

    public void setIdDonante(String idDonante) {
        this.idDonante = idDonante;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Donante nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public Donante categoria(String categoria) {
        this.setCategoria(categoria);
        return this;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Donante direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoPostal() {
        return this.codigoPostal;
    }

    public Donante codigoPostal(String codigoPostal) {
        this.setCodigoPostal(codigoPostal);
        return this;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getProvincia() {
        return this.provincia;
    }

    public Donante provincia(String provincia) {
        this.setProvincia(provincia);
        return this;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Donante telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return this.email;
    }

    public Donante email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResponsable() {
        return this.responsable;
    }

    public Donante responsable(String responsable) {
        this.setResponsable(responsable);
        return this;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public LocalDate getFechaAlta() {
        return this.fechaAlta;
    }

    public Donante fechaAlta(LocalDate fechaAlta) {
        this.setFechaAlta(fechaAlta);
        return this;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public LocalDate getFechaBaja() {
        return this.fechaBaja;
    }

    public Donante fechaBaja(LocalDate fechaBaja) {
        this.setFechaBaja(fechaBaja);
        return this;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getComentarios() {
        return this.comentarios;
    }

    public Donante comentarios(String comentarios) {
        this.setComentarios(comentarios);
        return this;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Boolean getActivo() {
        return this.activo;
    }

    public Donante activo(Boolean activo) {
        this.setActivo(activo);
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Set<AlEnt> getAlEnts() {
        return this.alEnts;
    }

    public void setAlEnts(Set<AlEnt> alEnts) {
        if (this.alEnts != null) {
            this.alEnts.forEach(i -> i.setDonante(null));
        }
        if (alEnts != null) {
            alEnts.forEach(i -> i.setDonante(this));
        }
        this.alEnts = alEnts;
    }

    public Donante alEnts(Set<AlEnt> alEnts) {
        this.setAlEnts(alEnts);
        return this;
    }

    public Donante addAlEnt(AlEnt alEnt) {
        this.alEnts.add(alEnt);
        alEnt.setDonante(this);
        return this;
    }

    public Donante removeAlEnt(AlEnt alEnt) {
        this.alEnts.remove(alEnt);
        alEnt.setDonante(null);
        return this;
    }

    public Nucleo getNucleo() {
        return this.nucleo;
    }

    public void setNucleo(Nucleo nucleo) {
        this.nucleo = nucleo;
    }

    public Donante nucleo(Nucleo nucleo) {
        this.setNucleo(nucleo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Donante)) {
            return false;
        }
        return id != null && id.equals(((Donante) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Donante{" +
            "id=" + getId() +
            ", idDonante='" + getIdDonante() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", categoria='" + getCategoria() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", codigoPostal='" + getCodigoPostal() + "'" +
            ", provincia='" + getProvincia() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", email='" + getEmail() + "'" +
            ", responsable='" + getResponsable() + "'" +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", fechaBaja='" + getFechaBaja() + "'" +
            ", comentarios='" + getComentarios() + "'" +
            ", activo='" + getActivo() + "'" +
            "}";
    }
}
