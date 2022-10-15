package com.refood.trazabilidad.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.refood.trazabilidad.domain.Intolerancia} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntoleranciaDTO implements Serializable {

    private Long id;

    private String nombreIntolerancia;

    private Set<TipoDeAlimentoDTO> tipoDeAlimentos = new HashSet<>();

    private Set<BeneficiarioDTO> beneficiarios = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreIntolerancia() {
        return nombreIntolerancia;
    }

    public void setNombreIntolerancia(String nombreIntolerancia) {
        this.nombreIntolerancia = nombreIntolerancia;
    }

    public Set<TipoDeAlimentoDTO> getTipoDeAlimentos() {
        return tipoDeAlimentos;
    }

    public void setTipoDeAlimentos(Set<TipoDeAlimentoDTO> tipoDeAlimentos) {
        this.tipoDeAlimentos = tipoDeAlimentos;
    }

    public Set<BeneficiarioDTO> getBeneficiarios() {
        return beneficiarios;
    }

    public void setBeneficiarios(Set<BeneficiarioDTO> beneficiarios) {
        this.beneficiarios = beneficiarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntoleranciaDTO)) {
            return false;
        }

        IntoleranciaDTO intoleranciaDTO = (IntoleranciaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, intoleranciaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntoleranciaDTO{" +
            "id=" + getId() +
            ", nombreIntolerancia='" + getNombreIntolerancia() + "'" +
            ", tipoDeAlimentos=" + getTipoDeAlimentos() +
            ", beneficiarios=" + getBeneficiarios() +
            "}";
    }
}
