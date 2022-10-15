package com.refood.trazabilidad.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.refood.trazabilidad.domain.AlimentoDeSalida} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlimentoDeSalidaDTO implements Serializable {

    private Long id;

    @NotNull
    private Double peso;

    @NotNull
    private LocalDate fechaEntrada;

    @NotNull
    private LocalDate fechaSalida;

    @NotNull
    private ZonedDateTime fechaYHoraLog;

    private ZonedDateTime fechaYHoraPreparacion;

    private ZonedDateTime fechaYHoraRecogida;

    private TupperDTO tupper;

    private BeneficiarioDTO beneficiario;

    private AlimentoDeEntradaDTO alimentoDeEntrada;

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

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public ZonedDateTime getFechaYHoraLog() {
        return fechaYHoraLog;
    }

    public void setFechaYHoraLog(ZonedDateTime fechaYHoraLog) {
        this.fechaYHoraLog = fechaYHoraLog;
    }

    public ZonedDateTime getFechaYHoraPreparacion() {
        return fechaYHoraPreparacion;
    }

    public void setFechaYHoraPreparacion(ZonedDateTime fechaYHoraPreparacion) {
        this.fechaYHoraPreparacion = fechaYHoraPreparacion;
    }

    public ZonedDateTime getFechaYHoraRecogida() {
        return fechaYHoraRecogida;
    }

    public void setFechaYHoraRecogida(ZonedDateTime fechaYHoraRecogida) {
        this.fechaYHoraRecogida = fechaYHoraRecogida;
    }

    public TupperDTO getTupper() {
        return tupper;
    }

    public void setTupper(TupperDTO tupper) {
        this.tupper = tupper;
    }

    public BeneficiarioDTO getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(BeneficiarioDTO beneficiario) {
        this.beneficiario = beneficiario;
    }

    public AlimentoDeEntradaDTO getAlimentoDeEntrada() {
        return alimentoDeEntrada;
    }

    public void setAlimentoDeEntrada(AlimentoDeEntradaDTO alimentoDeEntrada) {
        this.alimentoDeEntrada = alimentoDeEntrada;
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
        if (!(o instanceof AlimentoDeSalidaDTO)) {
            return false;
        }

        AlimentoDeSalidaDTO alimentoDeSalidaDTO = (AlimentoDeSalidaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, alimentoDeSalidaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlimentoDeSalidaDTO{" +
            "id=" + getId() +
            ", peso=" + getPeso() +
            ", fechaEntrada='" + getFechaEntrada() + "'" +
            ", fechaSalida='" + getFechaSalida() + "'" +
            ", fechaYHoraLog='" + getFechaYHoraLog() + "'" +
            ", fechaYHoraPreparacion='" + getFechaYHoraPreparacion() + "'" +
            ", fechaYHoraRecogida='" + getFechaYHoraRecogida() + "'" +
            ", tupper=" + getTupper() +
            ", beneficiario=" + getBeneficiario() +
            ", alimentoDeEntrada=" + getAlimentoDeEntrada() +
            ", tipoDeAlimento=" + getTipoDeAlimento() +
            "}";
    }
}
