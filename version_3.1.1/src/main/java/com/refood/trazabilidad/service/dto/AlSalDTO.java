package com.refood.trazabilidad.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.refood.trazabilidad.domain.AlSal} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlSalDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate fechaSalida;

    private TupperDTO tupper;

    private BenefDTO benef;

    private AlEntDTO alEnt;

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

    public TupperDTO getTupper() {
        return tupper;
    }

    public void setTupper(TupperDTO tupper) {
        this.tupper = tupper;
    }

    public BenefDTO getBenef() {
        return benef;
    }

    public void setBenef(BenefDTO benef) {
        this.benef = benef;
    }

    public AlEntDTO getAlEnt() {
        return alEnt;
    }

    public void setAlEnt(AlEntDTO alEnt) {
        this.alEnt = alEnt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlSalDTO)) {
            return false;
        }

        AlSalDTO alSalDTO = (AlSalDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, alSalDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlSalDTO{" +
            "id=" + getId() +
            ", fechaSalida='" + getFechaSalida() + "'" +
            ", tupper=" + getTupper() +
            ", benef=" + getBenef() +
            ", alEnt=" + getAlEnt() +
            "}";
    }
}
