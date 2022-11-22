package com.refood.trazabilidad.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.refood.trazabilidad.domain.Tupper} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TupperDTO implements Serializable {

    private Long id;

    @NotNull
    private Double peso;

    @NotNull
    private String productor;

    @NotNull
    private String modelo;

    @NotNull
    private Double precio;

    private String descripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getProductor() {
        return productor;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TupperDTO)) {
            return false;
        }

        TupperDTO tupperDTO = (TupperDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tupperDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TupperDTO{" +
            "id=" + getId() +
            ", peso=" + getPeso() +
            ", productor='" + getProductor() + "'" +
            ", modelo='" + getModelo() + "'" +
            ", precio=" + getPrecio() +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
