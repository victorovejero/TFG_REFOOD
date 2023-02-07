package com.refood.trazabilidad.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AlimentoDeEntrada.
 */
@Entity
@Table(name = "alimento_de_entrada")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlimentoDeEntrada implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "peso", nullable = false)
    private Double peso;

    @NotNull
    @Column(name = "fruta_y_verdura", nullable = false)
    private Boolean frutaYVerdura;

    @NotNull
    @Column(name = "fecha_y_hora_entrada", nullable = false)
    private ZonedDateTime fechaYHoraEntrada;

    @Column(name = "fecha_y_hora_recogida")
    private ZonedDateTime fechaYHoraRecogida;

    @Column(name = "fecha_y_hora_preparacion")
    private ZonedDateTime fechaYHoraPreparacion;

    @OneToMany(mappedBy = "alimentoDeEntrada")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tupper", "beneficiario", "alimentoDeEntrada", "checkouts" }, allowSetters = true)
    private Set<AlimentoDeSalida> alimentoDeSalidas = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_alimento_de_entrada__frutayverdura",
        joinColumns = @JoinColumn(name = "alimento_de_entrada_id"),
        inverseJoinColumns = @JoinColumn(name = "frutayverdura_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "alimentoDeEntradas" }, allowSetters = true)
    private Set<FrutaYVerdura> frutaYVerduras = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "alimentoDeSalidas", "alimentoDeEntradas" }, allowSetters = true)
    private Tupper tupper;

    @ManyToOne
    @JsonIgnoreProperties(value = { "alimentoDeEntradas", "nucleo" }, allowSetters = true)
    private Donante donante;

    @ManyToOne
    @JsonIgnoreProperties(value = { "alimentoDeEntradas", "intolerancias" }, allowSetters = true)
    private TipoDeAlimento tipoDeAlimento;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AlimentoDeEntrada id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPeso() {
        return this.peso;
    }

    public AlimentoDeEntrada peso(Double peso) {
        this.setPeso(peso);
        return this;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Boolean getFrutaYVerdura() {
        return this.frutaYVerdura;
    }

    public AlimentoDeEntrada frutaYVerdura(Boolean frutaYVerdura) {
        this.setFrutaYVerdura(frutaYVerdura);
        return this;
    }

    public void setFrutaYVerdura(Boolean frutaYVerdura) {
        this.frutaYVerdura = frutaYVerdura;
    }

    public ZonedDateTime getFechaYHoraEntrada() {
        return this.fechaYHoraEntrada;
    }

    public AlimentoDeEntrada fechaYHoraEntrada(ZonedDateTime fechaYHoraEntrada) {
        this.setFechaYHoraEntrada(fechaYHoraEntrada);
        return this;
    }

    public void setFechaYHoraEntrada(ZonedDateTime fechaYHoraEntrada) {
        this.fechaYHoraEntrada = fechaYHoraEntrada;
    }

    public ZonedDateTime getFechaYHoraRecogida() {
        return this.fechaYHoraRecogida;
    }

    public AlimentoDeEntrada fechaYHoraRecogida(ZonedDateTime fechaYHoraRecogida) {
        this.setFechaYHoraRecogida(fechaYHoraRecogida);
        return this;
    }

    public void setFechaYHoraRecogida(ZonedDateTime fechaYHoraRecogida) {
        this.fechaYHoraRecogida = fechaYHoraRecogida;
    }

    public ZonedDateTime getFechaYHoraPreparacion() {
        return this.fechaYHoraPreparacion;
    }

    public AlimentoDeEntrada fechaYHoraPreparacion(ZonedDateTime fechaYHoraPreparacion) {
        this.setFechaYHoraPreparacion(fechaYHoraPreparacion);
        return this;
    }

    public void setFechaYHoraPreparacion(ZonedDateTime fechaYHoraPreparacion) {
        this.fechaYHoraPreparacion = fechaYHoraPreparacion;
    }

    public Set<AlimentoDeSalida> getAlimentoDeSalidas() {
        return this.alimentoDeSalidas;
    }

    public void setAlimentoDeSalidas(Set<AlimentoDeSalida> alimentoDeSalidas) {
        if (this.alimentoDeSalidas != null) {
            this.alimentoDeSalidas.forEach(i -> i.setAlimentoDeEntrada(null));
        }
        if (alimentoDeSalidas != null) {
            alimentoDeSalidas.forEach(i -> i.setAlimentoDeEntrada(this));
        }
        this.alimentoDeSalidas = alimentoDeSalidas;
    }

    public AlimentoDeEntrada alimentoDeSalidas(Set<AlimentoDeSalida> alimentoDeSalidas) {
        this.setAlimentoDeSalidas(alimentoDeSalidas);
        return this;
    }

    public AlimentoDeEntrada addAlimentoDeSalida(AlimentoDeSalida alimentoDeSalida) {
        this.alimentoDeSalidas.add(alimentoDeSalida);
        alimentoDeSalida.setAlimentoDeEntrada(this);
        return this;
    }

    public AlimentoDeEntrada removeAlimentoDeSalida(AlimentoDeSalida alimentoDeSalida) {
        this.alimentoDeSalidas.remove(alimentoDeSalida);
        alimentoDeSalida.setAlimentoDeEntrada(null);
        return this;
    }

    public Set<FrutaYVerdura> getFrutaYVerduras() {
        return this.frutaYVerduras;
    }

    public void setFrutaYVerduras(Set<FrutaYVerdura> frutaYVerduras) {
        this.frutaYVerduras = frutaYVerduras;
    }

    public AlimentoDeEntrada frutaYVerduras(Set<FrutaYVerdura> frutaYVerduras) {
        this.setFrutaYVerduras(frutaYVerduras);
        return this;
    }

    public AlimentoDeEntrada addFrutaYVerdura(FrutaYVerdura frutaYVerdura) {
        this.frutaYVerduras.add(frutaYVerdura);
        frutaYVerdura.getAlimentoDeEntradas().add(this);
        return this;
    }

    public AlimentoDeEntrada removeFrutaYVerdura(FrutaYVerdura frutaYVerdura) {
        this.frutaYVerduras.remove(frutaYVerdura);
        frutaYVerdura.getAlimentoDeEntradas().remove(this);
        return this;
    }

    public Tupper getTupper() {
        return this.tupper;
    }

    public void setTupper(Tupper tupper) {
        this.tupper = tupper;
    }

    public AlimentoDeEntrada tupper(Tupper tupper) {
        this.setTupper(tupper);
        return this;
    }

    public Donante getDonante() {
        return this.donante;
    }

    public void setDonante(Donante donante) {
        this.donante = donante;
    }

    public AlimentoDeEntrada donante(Donante donante) {
        this.setDonante(donante);
        return this;
    }

    public TipoDeAlimento getTipoDeAlimento() {
        return this.tipoDeAlimento;
    }

    public void setTipoDeAlimento(TipoDeAlimento tipoDeAlimento) {
        this.tipoDeAlimento = tipoDeAlimento;
    }

    public AlimentoDeEntrada tipoDeAlimento(TipoDeAlimento tipoDeAlimento) {
        this.setTipoDeAlimento(tipoDeAlimento);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlimentoDeEntrada)) {
            return false;
        }
        return id != null && id.equals(((AlimentoDeEntrada) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlimentoDeEntrada{" +
            "id=" + getId() +
            ", peso=" + getPeso() +
            ", frutaYVerdura='" + getFrutaYVerdura() + "'" +
            ", fechaYHoraEntrada='" + getFechaYHoraEntrada() + "'" +
            ", fechaYHoraRecogida='" + getFechaYHoraRecogida() + "'" +
            ", fechaYHoraPreparacion='" + getFechaYHoraPreparacion() + "'" +
            "}";
    }
}
