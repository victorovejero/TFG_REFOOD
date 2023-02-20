package com.refood.trazabilidad.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.refood.trazabilidad.domain.Donante} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DonanteDTO implements Serializable {

    private Long id;

    @NotNull
    private String idDonante;

    @NotNull
    private String nombre;

    @NotNull
    private String categoria;

    @NotNull
    private String direccion;

    @NotNull
    private String codigoPostal;

    @NotNull
    private String provincia;

    @NotNull
    private String telefono;

    @NotNull
    private String email;

    @NotNull
    private String responsable;

    @NotNull
    private LocalDate fechaAlta;

    private LocalDate fechaBaja;

    private String comentarios;

    @NotNull
    private Boolean activo;

    private NucleoDTO nucleo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdDonante() {
        return idDonante;
    }

    public void setIdDonante(String idDonante) {
        this.idDonante = idDonante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public LocalDate getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public NucleoDTO getNucleo() {
        return nucleo;
    }

    public void setNucleo(NucleoDTO nucleo) {
        this.nucleo = nucleo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonanteDTO)) {
            return false;
        }

        DonanteDTO donanteDTO = (DonanteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, donanteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonanteDTO{" +
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
            ", nucleo=" + getNucleo() +
            "}";
    }
}
