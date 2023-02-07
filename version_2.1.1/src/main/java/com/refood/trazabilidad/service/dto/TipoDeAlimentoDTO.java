package com.refood.trazabilidad.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.refood.trazabilidad.domain.TipoDeAlimento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TipoDeAlimentoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombreAlimento;

    private String descripcion;

    private Set<IntoleranciaDTO> intolerancias = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreAlimento() {
        return nombreAlimento;
    }

    public void setNombreAlimento(String nombreAlimento) {
        this.nombreAlimento = nombreAlimento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<IntoleranciaDTO> getIntolerancias() {
        return intolerancias;
    }

    public void setIntolerancias(Set<IntoleranciaDTO> intolerancias) {
        this.intolerancias = intolerancias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoDeAlimentoDTO)) {
            return false;
        }

        TipoDeAlimentoDTO tipoDeAlimentoDTO = (TipoDeAlimentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tipoDeAlimentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoDeAlimentoDTO{" +
            "id=" + getId() +
            ", nombreAlimento='" + getNombreAlimento() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", intolerancias=" + getIntolerancias() +
            "}";
    }
}
