package com.refood.trazabilidad.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.refood.trazabilidad.domain.AlEnt} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlEntDTO implements Serializable {

    private Long id;

    @NotNull
    private Double peso;

    @NotNull
    private Boolean frutaYVerdura;

    @NotNull
    private ZonedDateTime fechaYHoraEntrada;

    private ZonedDateTime fechaYHoraRecogida;

    private ZonedDateTime fechaYHoraPreparacion;

    private TupperDTO tupper;

    private DonanteDTO donante;

    private TipoAlDTO tipoAl;

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

    public TipoAlDTO getTipoAl() {
        return tipoAl;
    }

    public void setTipoAl(TipoAlDTO tipoAl) {
        this.tipoAl = tipoAl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlEntDTO)) {
            return false;
        }

        AlEntDTO alEntDTO = (AlEntDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, alEntDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlEntDTO{" +
            "id=" + getId() +
            ", peso=" + getPeso() +
            ", frutaYVerdura='" + getFrutaYVerdura() + "'" +
            ", fechaYHoraEntrada='" + getFechaYHoraEntrada() + "'" +
            ", fechaYHoraRecogida='" + getFechaYHoraRecogida() + "'" +
            ", fechaYHoraPreparacion='" + getFechaYHoraPreparacion() + "'" +
            ", tupper=" + getTupper() +
            ", donante=" + getDonante() +
            ", tipoAl=" + getTipoAl() +
            "}";
    }
}
