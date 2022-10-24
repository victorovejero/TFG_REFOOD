package com.refood.trazabilidad.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.refood.trazabilidad.domain.Beneficiario} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BeneficiarioDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private Integer numeroPersonas;

    @NotNull
    private Integer numeroNinios;

    private String idDual;

    private NucleoDTO nucleo;

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

    public Integer getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(Integer numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }

    public Integer getNumeroNinios() {
        return numeroNinios;
    }

    public void setNumeroNinios(Integer numeroNinios) {
        this.numeroNinios = numeroNinios;
    }

    public String getIdDual() {
        return idDual;
    }

    public void setIdDual(String idDual) {
        this.idDual = idDual;
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
        if (!(o instanceof BeneficiarioDTO)) {
            return false;
        }

        BeneficiarioDTO beneficiarioDTO = (BeneficiarioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, beneficiarioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BeneficiarioDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", numeroPersonas=" + getNumeroPersonas() +
            ", numeroNinios=" + getNumeroNinios() +
            ", idDual='" + getIdDual() + "'" +
            ", nucleo=" + getNucleo() +
            "}";
    }
}
