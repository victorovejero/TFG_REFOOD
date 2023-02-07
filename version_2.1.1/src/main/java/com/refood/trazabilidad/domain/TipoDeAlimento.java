package com.refood.trazabilidad.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TipoDeAlimento.
 */
@Entity
@Table(name = "tipo_de_alimento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TipoDeAlimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre_alimento", nullable = false)
    private String nombreAlimento;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "tipoDeAlimento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "alimentoDeSalidas", "frutaYVerduras", "tupper", "donante", "tipoDeAlimento" }, allowSetters = true)
    private Set<AlimentoDeEntrada> alimentoDeEntradas = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_tipo_de_alimento__intolerancia",
        joinColumns = @JoinColumn(name = "tipo_de_alimento_id"),
        inverseJoinColumns = @JoinColumn(name = "intolerancia_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "beneficiarios", "personaBeneficiarias", "tipoDeAlimentos" }, allowSetters = true)
    private Set<Intolerancia> intolerancias = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TipoDeAlimento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreAlimento() {
        return this.nombreAlimento;
    }

    public TipoDeAlimento nombreAlimento(String nombreAlimento) {
        this.setNombreAlimento(nombreAlimento);
        return this;
    }

    public void setNombreAlimento(String nombreAlimento) {
        this.nombreAlimento = nombreAlimento;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public TipoDeAlimento descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<AlimentoDeEntrada> getAlimentoDeEntradas() {
        return this.alimentoDeEntradas;
    }

    public void setAlimentoDeEntradas(Set<AlimentoDeEntrada> alimentoDeEntradas) {
        if (this.alimentoDeEntradas != null) {
            this.alimentoDeEntradas.forEach(i -> i.setTipoDeAlimento(null));
        }
        if (alimentoDeEntradas != null) {
            alimentoDeEntradas.forEach(i -> i.setTipoDeAlimento(this));
        }
        this.alimentoDeEntradas = alimentoDeEntradas;
    }

    public TipoDeAlimento alimentoDeEntradas(Set<AlimentoDeEntrada> alimentoDeEntradas) {
        this.setAlimentoDeEntradas(alimentoDeEntradas);
        return this;
    }

    public TipoDeAlimento addAlimentoDeEntrada(AlimentoDeEntrada alimentoDeEntrada) {
        this.alimentoDeEntradas.add(alimentoDeEntrada);
        alimentoDeEntrada.setTipoDeAlimento(this);
        return this;
    }

    public TipoDeAlimento removeAlimentoDeEntrada(AlimentoDeEntrada alimentoDeEntrada) {
        this.alimentoDeEntradas.remove(alimentoDeEntrada);
        alimentoDeEntrada.setTipoDeAlimento(null);
        return this;
    }

    public Set<Intolerancia> getIntolerancias() {
        return this.intolerancias;
    }

    public void setIntolerancias(Set<Intolerancia> intolerancias) {
        this.intolerancias = intolerancias;
    }

    public TipoDeAlimento intolerancias(Set<Intolerancia> intolerancias) {
        this.setIntolerancias(intolerancias);
        return this;
    }

    public TipoDeAlimento addIntolerancia(Intolerancia intolerancia) {
        this.intolerancias.add(intolerancia);
        intolerancia.getTipoDeAlimentos().add(this);
        return this;
    }

    public TipoDeAlimento removeIntolerancia(Intolerancia intolerancia) {
        this.intolerancias.remove(intolerancia);
        intolerancia.getTipoDeAlimentos().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoDeAlimento)) {
            return false;
        }
        return id != null && id.equals(((TipoDeAlimento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoDeAlimento{" +
            "id=" + getId() +
            ", nombreAlimento='" + getNombreAlimento() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
