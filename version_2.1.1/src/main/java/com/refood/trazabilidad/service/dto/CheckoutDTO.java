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
    private Integer peso;

    private Set<AlimentoDeSalidaDTO> alimentoDeSalidas = new HashSet<>();

    private BeneficiarioDTO beneficiario;

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

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Set<AlimentoDeSalidaDTO> getAlimentoDeSalidas() {
        return alimentoDeSalidas;
    }

    public void setAlimentoDeSalidas(Set<AlimentoDeSalidaDTO> alimentoDeSalidas) {
        this.alimentoDeSalidas = alimentoDeSalidas;
    }

    public BeneficiarioDTO getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(BeneficiarioDTO beneficiario) {
        this.beneficiario = beneficiario;
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
            ", alimentoDeSalidas=" + getAlimentoDeSalidas() +
            ", beneficiario=" + getBeneficiario() +
            "}";
    }
}
