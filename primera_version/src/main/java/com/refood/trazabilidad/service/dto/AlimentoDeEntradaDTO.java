package com.refood.trazabilidad.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
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
    private LocalDate fechaEntrada;

    @NotNull
    private ZonedDateTime fechaYHoraLog;

    private ZonedDateTime fechaYHoraRecogida;

    private ZonedDateTime fechaYHoraPreparacion;

    private TipoDeAlimentoDTO alimentoDeEntrada;

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

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public ZonedDateTime getFechaYHoraLog() {
        return fechaYHoraLog;
    }

    public void setFechaYHoraLog(ZonedDateTime fechaYHoraLog) {
        this.fechaYHoraLog = fechaYHoraLog;
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

    public TipoDeAlimentoDTO getAlimentoDeEntrada() {
        return alimentoDeEntrada;
    }

    public void setAlimentoDeEntrada(TipoDeAlimentoDTO alimentoDeEntrada) {
        this.alimentoDeEntrada = alimentoDeEntrada;
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
            ", fechaEntrada='" + getFechaEntrada() + "'" +
            ", fechaYHoraLog='" + getFechaYHoraLog() + "'" +
            ", fechaYHoraRecogida='" + getFechaYHoraRecogida() + "'" +
            ", fechaYHoraPreparacion='" + getFechaYHoraPreparacion() + "'" +
            ", alimentoDeEntrada=" + getAlimentoDeEntrada() +
            ", donante=" + getDonante() +
            ", tipoDeAlimento=" + getTipoDeAlimento() +
            "}";
    }
}
