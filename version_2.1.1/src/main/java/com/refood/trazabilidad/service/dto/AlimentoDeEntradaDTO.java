package com.refood.trazabilidad.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.refood.trazabilidad.domain.AlimentoDeEntrada} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlimentoDeEntradaDTO implements Serializable {

    private Long id;

    @NotNull
    private Double peso;

    @NotNull
    private Boolean frutaYVerdura;

    @NotNull
    private ZonedDateTime fechaYHoraEntrada;

    private ZonedDateTime fechaYHoraRecogida;

    private ZonedDateTime fechaYHoraPreparacion;

    private Set<FrutaYVerduraDTO> frutaYVerduras = new HashSet<>();

    private TupperDTO tupper;

    private DonanteDTO donante;

    private TipoDeAlimentoDTO tipoDeAlimento;

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

    public Boolean getFrutaYVerdura() {
        return frutaYVerdura;
    }

    public void setFrutaYVerdura(Boolean frutaYVerdura) {
        this.frutaYVerdura = frutaYVerdura;
    }

    public ZonedDateTime getFechaYHoraEntrada() {
        return fechaYHoraEntrada;
    }

    public void setFechaYHoraEntrada(ZonedDateTime fechaYHoraEntrada) {
        this.fechaYHoraEntrada = fechaYHoraEntrada;
    }

    public ZonedDateTime getFechaYHoraRecogida() {
        return fechaYHoraRecogida;
    }

    public void setFechaYHoraRecogida(ZonedDateTime fechaYHoraRecogida) {
        this.fechaYHoraRecogida = fechaYHoraRecogida;
    }

    public ZonedDateTime getFechaYHoraPreparacion() {
        return fechaYHoraPreparacion;
    }

    public void setFechaYHoraPreparacion(ZonedDateTime fechaYHoraPreparacion) {
        this.fechaYHoraPreparacion = fechaYHoraPreparacion;
    }

    public Set<FrutaYVerduraDTO> getFrutaYVerduras() {
        return frutaYVerduras;
    }

    public void setFrutaYVerduras(Set<FrutaYVerduraDTO> frutaYVerduras) {
        this.frutaYVerduras = frutaYVerduras;
    }

    public TupperDTO getTupper() {
        return tupper;
    }

    public void setTupper(TupperDTO tupper) {
        this.tupper = tupper;
    }

    public DonanteDTO getDonante() {
        return donante;
    }

    public void setDonante(DonanteDTO donante) {
        this.donante = donante;
    }

    public TipoDeAlimentoDTO getTipoDeAlimento() {
        return tipoDeAlimento;
    }

    public void setTipoDeAlimento(TipoDeAlimentoDTO tipoDeAlimento) {
        this.tipoDeAlimento = tipoDeAlimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlimentoDeEntradaDTO)) {
            return false;
        }

        AlimentoDeEntradaDTO alimentoDeEntradaDTO = (AlimentoDeEntradaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, alimentoDeEntradaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlimentoDeEntradaDTO{" +
            "id=" + getId() +
            ", peso=" + getPeso() +
            ", frutaYVerdura='" + getFrutaYVerdura() + "'" +
            ", fechaYHoraEntrada='" + getFechaYHoraEntrada() + "'" +
            ", fechaYHoraRecogida='" + getFechaYHoraRecogida() + "'" +
            ", fechaYHoraPreparacion='" + getFechaYHoraPreparacion() + "'" +
            ", frutaYVerduras=" + getFrutaYVerduras() +
            ", tupper=" + getTupper() +
            ", donante=" + getDonante() +
            ", tipoDeAlimento=" + getTipoDeAlimento() +
            "}";
    }
}
