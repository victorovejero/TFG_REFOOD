package com.refood.trazabilidad.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Registro.
 */
@Entity
@Table(name = "registro")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Registro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "dia_actividad", nullable = false)
    private LocalDate diaActividad;

    @NotNull
    @Column(name = "ruta", nullable = false)
    private String ruta;

    @ManyToMany
    @JoinTable(
        name = "rel_registro__voluntario",
        joinColumns = @JoinColumn(name = "registro_id"),
        inverseJoinColumns = @JoinColumn(name = "voluntario_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nucleo" }, allowSetters = true)
    private Set<Voluntario> voluntarios = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "donantes", "beneficiarios", "voluntarios" }, allowSetters = true)
    private Nucleo nucleo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Registro id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDiaActividad() {
        return this.diaActividad;
    }

    public Registro diaActividad(LocalDate diaActividad) {
        this.setDiaActividad(diaActividad);
        return this;
    }

    public void setDiaActividad(LocalDate diaActividad) {
        this.diaActividad = diaActividad;
    }

    public String getRuta() {
        return this.ruta;
    }

    public Registro ruta(String ruta) {
        this.setRuta(ruta);
        return this;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Set<Voluntario> getVoluntarios() {
        return this.voluntarios;
    }

    public void setVoluntarios(Set<Voluntario> voluntarios) {
        this.voluntarios = voluntarios;
    }

    public Registro voluntarios(Set<Voluntario> voluntarios) {
        this.setVoluntarios(voluntarios);
        return this;
    }

    public Registro addVoluntario(Voluntario voluntario) {
        this.voluntarios.add(voluntario);
        return this;
    }

    public Registro removeVoluntario(Voluntario voluntario) {
        this.voluntarios.remove(voluntario);
        return this;
    }

    public Nucleo getNucleo() {
        return this.nucleo;
    }

    public void setNucleo(Nucleo nucleo) {
        this.nucleo = nucleo;
    }

    public Registro nucleo(Nucleo nucleo) {
        this.setNucleo(nucleo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Registro)) {
            return false;
        }
        return id != null && id.equals(((Registro) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Registro{" +
            "id=" + getId() +
            ", diaActividad='" + getDiaActividad() + "'" +
            ", ruta='" + getRuta() + "'" +
            "}";
    }
}
