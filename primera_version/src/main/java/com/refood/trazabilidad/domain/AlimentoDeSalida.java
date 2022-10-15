package com.refood.trazabilidad.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AlimentoDeSalida.
 */
@Entity
@Table(name = "alimento_de_salida")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlimentoDeSalida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "peso", nullable = false)
    private Double peso;

    @NotNull
    @Column(name = "fecha_entrada", nullable = false)
    private LocalDate fechaEntrada;

    @NotNull
    @Column(name = "fecha_salida", nullable = false)
    private LocalDate fechaSalida;

    @NotNull
    @Column(name = "fecha_y_hora_log", nullable = false)
    private ZonedDateTime fechaYHoraLog;

    @Column(name = "fecha_y_hora_preparacion")
    private ZonedDateTime fechaYHoraPreparacion;

    @Column(name = "fecha_y_hora_recogida")
    private ZonedDateTime fechaYHoraRecogida;

    @ManyToOne
    @JsonIgnoreProperties(value = { "alimentoDeSalidas" }, allowSetters = true)
    private Tupper tupper;

    @ManyToOne
    @JsonIgnoreProperties(value = { "alimentoDeSalidas", "nucleo", "intolerancias" }, allowSetters = true)
    private Beneficiario beneficiario;

    @ManyToOne
    @JsonIgnoreProperties(value = { "alimentoDeEntrada", "alimentoDeSalidas", "donante", "tipoDeAlimento" }, allowSetters = true)
    private AlimentoDeEntrada alimentoDeEntrada;

    @ManyToOne
    @JsonIgnoreProperties(value = { "alimentoDeEntradas", "alimentoDeSalidas", "intolerancias" }, allowSetters = true)
    private TipoDeAlimento tipoDeAlimento;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AlimentoDeSalida id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPeso() {
        return this.peso;
    }

    public AlimentoDeSalida peso(Double peso) {
        this.setPeso(peso);
        return this;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public LocalDate getFechaEntrada() {
        return this.fechaEntrada;
    }

    public AlimentoDeSalida fechaEntrada(LocalDate fechaEntrada) {
        this.setFechaEntrada(fechaEntrada);
        return this;
    }

    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return this.fechaSalida;
    }

    public AlimentoDeSalida fechaSalida(LocalDate fechaSalida) {
        this.setFechaSalida(fechaSalida);
        return this;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public ZonedDateTime getFechaYHoraLog() {
        return this.fechaYHoraLog;
    }

    public AlimentoDeSalida fechaYHoraLog(ZonedDateTime fechaYHoraLog) {
        this.setFechaYHoraLog(fechaYHoraLog);
        return this;
    }

    public void setFechaYHoraLog(ZonedDateTime fechaYHoraLog) {
        this.fechaYHoraLog = fechaYHoraLog;
    }

    public ZonedDateTime getFechaYHoraPreparacion() {
        return this.fechaYHoraPreparacion;
    }

    public AlimentoDeSalida fechaYHoraPreparacion(ZonedDateTime fechaYHoraPreparacion) {
        this.setFechaYHoraPreparacion(fechaYHoraPreparacion);
        return this;
    }

    public void setFechaYHoraPreparacion(ZonedDateTime fechaYHoraPreparacion) {
        this.fechaYHoraPreparacion = fechaYHoraPreparacion;
    }

    public ZonedDateTime getFechaYHoraRecogida() {
        return this.fechaYHoraRecogida;
    }

    public AlimentoDeSalida fechaYHoraRecogida(ZonedDateTime fechaYHoraRecogida) {
        this.setFechaYHoraRecogida(fechaYHoraRecogida);
        return this;
    }

    public void setFechaYHoraRecogida(ZonedDateTime fechaYHoraRecogida) {
        this.fechaYHoraRecogida = fechaYHoraRecogida;
    }

    public Tupper getTupper() {
        return this.tupper;
    }

    public void setTupper(Tupper tupper) {
        this.tupper = tupper;
    }

    public AlimentoDeSalida tupper(Tupper tupper) {
        this.setTupper(tupper);
        return this;
    }

    public Beneficiario getBeneficiario() {
        return this.beneficiario;
    }

    public void setBeneficiario(Beneficiario beneficiario) {
        this.beneficiario = beneficiario;
    }

    public AlimentoDeSalida beneficiario(Beneficiario beneficiario) {
        this.setBeneficiario(beneficiario);
        return this;
    }

    public AlimentoDeEntrada getAlimentoDeEntrada() {
        return this.alimentoDeEntrada;
    }

    public void setAlimentoDeEntrada(AlimentoDeEntrada alimentoDeEntrada) {
        this.alimentoDeEntrada = alimentoDeEntrada;
    }

    public AlimentoDeSalida alimentoDeEntrada(AlimentoDeEntrada alimentoDeEntrada) {
        this.setAlimentoDeEntrada(alimentoDeEntrada);
        return this;
    }

    public TipoDeAlimento getTipoDeAlimento() {
        return this.tipoDeAlimento;
    }

    public void setTipoDeAlimento(TipoDeAlimento tipoDeAlimento) {
        this.tipoDeAlimento = tipoDeAlimento;
    }

    public AlimentoDeSalida tipoDeAlimento(TipoDeAlimento tipoDeAlimento) {
        this.setTipoDeAlimento(tipoDeAlimento);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlimentoDeSalida)) {
            return false;
        }
        return id != null && id.equals(((AlimentoDeSalida) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlimentoDeSalida{" +
            "id=" + getId() +
            ", peso=" + getPeso() +
            ", fechaEntrada='" + getFechaEntrada() + "'" +
            ", fechaSalida='" + getFechaSalida() + "'" +
            ", fechaYHoraLog='" + getFechaYHoraLog() + "'" +
            ", fechaYHoraPreparacion='" + getFechaYHoraPreparacion() + "'" +
            ", fechaYHoraRecogida='" + getFechaYHoraRecogida() + "'" +
            "}";
    }
}
