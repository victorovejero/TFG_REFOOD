package com.refood.trazabilidad.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.refood.trazabilidad.domain.Nucleo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NucleoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String direccion;

    @NotNull
    private String provincia;

    @NotNull
    private String responsable;

    @NotNull
    private String telefono;

    @NotNull
    private String email;

    @NotNull
    private Integer numeroRutas;

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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
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

    public Integer getNumeroRutas() {
        return numeroRutas;
    }

    public void setNumeroRutas(Integer numeroRutas) {
        this.numeroRutas = numeroRutas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NucleoDTO)) {
            return false;
        }

        NucleoDTO nucleoDTO = (NucleoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, nucleoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NucleoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", provincia='" + getProvincia() + "'" +
            ", responsable='" + getResponsable() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", email='" + getEmail() + "'" +
            ", numeroRutas=" + getNumeroRutas() +
            "}";
    }
}
