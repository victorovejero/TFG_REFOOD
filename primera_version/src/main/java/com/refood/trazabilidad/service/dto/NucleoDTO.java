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
    private String nombreNucleo;

    @NotNull
    private String direccionNucleo;

    @NotNull
    private String provinciaNucleo;

    @NotNull
    private String responsableNucleo;

    @NotNull
    private String telefonoNucleo;

    @NotNull
    private String emailNucleo;

    @NotNull
    private Integer numeroRutas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreNucleo() {
        return nombreNucleo;
    }

    public void setNombreNucleo(String nombreNucleo) {
        this.nombreNucleo = nombreNucleo;
    }

    public String getDireccionNucleo() {
        return direccionNucleo;
    }

    public void setDireccionNucleo(String direccionNucleo) {
        this.direccionNucleo = direccionNucleo;
    }

    public String getProvinciaNucleo() {
        return provinciaNucleo;
    }

    public void setProvinciaNucleo(String provinciaNucleo) {
        this.provinciaNucleo = provinciaNucleo;
    }

    public String getResponsableNucleo() {
        return responsableNucleo;
    }

    public void setResponsableNucleo(String responsableNucleo) {
        this.responsableNucleo = responsableNucleo;
    }

    public String getTelefonoNucleo() {
        return telefonoNucleo;
    }

    public void setTelefonoNucleo(String telefonoNucleo) {
        this.telefonoNucleo = telefonoNucleo;
    }

    public String getEmailNucleo() {
        return emailNucleo;
    }

    public void setEmailNucleo(String emailNucleo) {
        this.emailNucleo = emailNucleo;
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
            ", nombreNucleo='" + getNombreNucleo() + "'" +
            ", direccionNucleo='" + getDireccionNucleo() + "'" +
            ", provinciaNucleo='" + getProvinciaNucleo() + "'" +
            ", responsableNucleo='" + getResponsableNucleo() + "'" +
            ", telefonoNucleo='" + getTelefonoNucleo() + "'" +
            ", emailNucleo='" + getEmailNucleo() + "'" +
            ", numeroRutas=" + getNumeroRutas() +
            "}";
    }
}
