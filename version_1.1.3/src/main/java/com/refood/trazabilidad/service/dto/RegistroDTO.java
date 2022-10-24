package com.refood.trazabilidad.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.refood.trazabilidad.domain.Registro} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RegistroDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate diaActividad;

    @NotNull
    private String ruta;

    private Set<VoluntarioDTO> voluntarios = new HashSet<>();

    private NucleoDTO nucleo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDiaActividad() {
        return diaActividad;
    }

    public void setDiaActividad(LocalDate diaActividad) {
        this.diaActividad = diaActividad;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Set<VoluntarioDTO> getVoluntarios() {
        return voluntarios;
    }

    public void setVoluntarios(Set<VoluntarioDTO> voluntarios) {
        this.voluntarios = voluntarios;
    }

    public NucleoDTO getNucleo() {
        return nucleo;
    }

    public void setNucleo(NucleoDTO nucleo) {
        this.nucleo = nucleo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegistroDTO)) {
            return false;
        }

        RegistroDTO registroDTO = (RegistroDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, registroDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegistroDTO{" +
            "id=" + getId() +
            ", diaActividad='" + getDiaActividad() + "'" +
            ", ruta='" + getRuta() + "'" +
            ", voluntarios=" + getVoluntarios() +
            ", nucleo=" + getNucleo() +
            "}";
    }
}
