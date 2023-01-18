package com.refood.trazabilidad.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.refood.trazabilidad.domain.TipoAl} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TipoAlDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombreAlimento;

    @NotNull
    private Boolean frutaYVerdura;

    private String descripcion;

    private Set<IntolDTO> intols = new HashSet<>();

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

    public Boolean getFrutaYVerdura() {
        return frutaYVerdura;
    }

    public void setFrutaYVerdura(Boolean frutaYVerdura) {
        this.frutaYVerdura = frutaYVerdura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<IntolDTO> getIntols() {
        return intols;
    }

    public void setIntols(Set<IntolDTO> intols) {
        this.intols = intols;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoAlDTO)) {
            return false;
        }

        TipoAlDTO tipoAlDTO = (TipoAlDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tipoAlDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoAlDTO{" +
            "id=" + getId() +
            ", nombreAlimento='" + getNombreAlimento() + "'" +
            ", frutaYVerdura='" + getFrutaYVerdura() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", intols=" + getIntols() +
            "}";
    }
}
