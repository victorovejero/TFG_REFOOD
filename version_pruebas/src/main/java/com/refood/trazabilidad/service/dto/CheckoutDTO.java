package com.refood.trazabilidad.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.refood.trazabilidad.domain.Checkout} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CheckoutDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate fechaSalida;

    @NotNull
    private Double peso;

    private Set<AlSalDTO> alSals = new HashSet<>();

    private BenefDTO benef;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Set<AlSalDTO> getAlSals() {
        return alSals;
    }

    public void setAlSals(Set<AlSalDTO> alSals) {
        this.alSals = alSals;
    }

    public BenefDTO getBenef() {
        return benef;
    }

    public void setBenef(BenefDTO benef) {
        this.benef = benef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckoutDTO)) {
            return false;
        }

        CheckoutDTO checkoutDTO = (CheckoutDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, checkoutDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckoutDTO{" +
            "id=" + getId() +
            ", fechaSalida='" + getFechaSalida() + "'" +
            ", peso=" + getPeso() +
            ", alSals=" + getAlSals() +
            ", benef=" + getBenef() +
            "}";
    }
}
