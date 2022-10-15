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
 * A Donante.
 */
@Entity
@Table(name = "donante")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Donante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre_donante", nullable = false)
    private String nombreDonante;

    @NotNull
    @Column(name = "tipo_donante", nullable = false)
    private String tipoDonante;

    @NotNull
    @Column(name = "ruta", nullable = false)
    private Integer ruta;

    @NotNull
    @Column(name = "direccion_donante", nullable = false)
    private String direccionDonante;

    @NotNull
    @Column(name = "telefono_donante", nullable = false)
    private String telefonoDonante;

    @NotNull
    @Column(name = "email_donante", nullable = false)
    private String emailDonante;

    @NotNull
    @Column(name = "responsable_donante", nullable = false)
    private String responsableDonante;

    @NotNull
    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;

    @Column(name = "fecha_baja")
    private LocalDate fechaBaja;

    @Column(name = "comentarios")
    private String comentarios;

    @OneToMany(mappedBy = "donante")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "alimentoDeEntrada", "alimentoDeSalidas", "donante", "tipoDeAlimento" }, allowSetters = true)
    private Set<AlimentoDeEntrada> alimentoDeEntradas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "donantes", "beneficiarios", "voluntarios", "socios", "registros" }, allowSetters = true)
    private Nucleo nucleo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Donante id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreDonante() {
        return this.nombreDonante;
    }

    public Donante nombreDonante(String nombreDonante) {
        this.setNombreDonante(nombreDonante);
        return this;
    }

    public void setNombreDonante(String nombreDonante) {
        this.nombreDonante = nombreDonante;
    }

    public String getTipoDonante() {
        return this.tipoDonante;
    }

    public Donante tipoDonante(String tipoDonante) {
        this.setTipoDonante(tipoDonante);
        return this;
    }

    public void setTipoDonante(String tipoDonante) {
        this.tipoDonante = tipoDonante;
    }

    public Integer getRuta() {
        return this.ruta;
    }

    public Donante ruta(Integer ruta) {
        this.setRuta(ruta);
        return this;
    }

    public void setRuta(Integer ruta) {
        this.ruta = ruta;
    }

    public String getDireccionDonante() {
        return this.direccionDonante;
    }

    public Donante direccionDonante(String direccionDonante) {
        this.setDireccionDonante(direccionDonante);
        return this;
    }

    public void setDireccionDonante(String direccionDonante) {
        this.direccionDonante = direccionDonante;
    }

    public String getTelefonoDonante() {
        return this.telefonoDonante;
    }

    public Donante telefonoDonante(String telefonoDonante) {
        this.setTelefonoDonante(telefonoDonante);
        return this;
    }

    public void setTelefonoDonante(String telefonoDonante) {
        this.telefonoDonante = telefonoDonante;
    }

    public String getEmailDonante() {
        return this.emailDonante;
    }

    public Donante emailDonante(String emailDonante) {
        this.setEmailDonante(emailDonante);
        return this;
    }

    public void setEmailDonante(String emailDonante) {
        this.emailDonante = emailDonante;
    }

    public String getResponsableDonante() {
        return this.responsableDonante;
    }

    public Donante responsableDonante(String responsableDonante) {
        this.setResponsableDonante(responsableDonante);
        return this;
    }

    public void setResponsableDonante(String responsableDonante) {
        this.responsableDonante = responsableDonante;
    }

    public LocalDate getFechaAlta() {
        return this.fechaAlta;
    }

    public Donante fechaAlta(LocalDate fechaAlta) {
        this.setFechaAlta(fechaAlta);
        return this;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public LocalDate getFechaBaja() {
        return this.fechaBaja;
    }

    public Donante fechaBaja(LocalDate fechaBaja) {
        this.setFechaBaja(fechaBaja);
        return this;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getComentarios() {
        return this.comentarios;
    }

    public Donante comentarios(String comentarios) {
        this.setComentarios(comentarios);
        return this;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Set<AlimentoDeEntrada> getAlimentoDeEntradas() {
        return this.alimentoDeEntradas;
    }

    public void setAlimentoDeEntradas(Set<AlimentoDeEntrada> alimentoDeEntradas) {
        if (this.alimentoDeEntradas != null) {
            this.alimentoDeEntradas.forEach(i -> i.setDonante(null));
        }
        if (alimentoDeEntradas != null) {
            alimentoDeEntradas.forEach(i -> i.setDonante(this));
        }
        this.alimentoDeEntradas = alimentoDeEntradas;
    }

    public Donante alimentoDeEntradas(Set<AlimentoDeEntrada> alimentoDeEntradas) {
        this.setAlimentoDeEntradas(alimentoDeEntradas);
        return this;
    }

    public Donante addAlimentoDeEntrada(AlimentoDeEntrada alimentoDeEntrada) {
        this.alimentoDeEntradas.add(alimentoDeEntrada);
        alimentoDeEntrada.setDonante(this);
        return this;
    }

    public Donante removeAlimentoDeEntrada(AlimentoDeEntrada alimentoDeEntrada) {
        this.alimentoDeEntradas.remove(alimentoDeEntrada);
        alimentoDeEntrada.setDonante(null);
        return this;
    }

    public Nucleo getNucleo() {
        return this.nucleo;
    }

    public void setNucleo(Nucleo nucleo) {
        this.nucleo = nucleo;
    }

    public Donante nucleo(Nucleo nucleo) {
        this.setNucleo(nucleo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Donante)) {
            return false;
        }
        return id != null && id.equals(((Donante) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Donante{" +
            "id=" + getId() +
            ", nombreDonante='" + getNombreDonante() + "'" +
            ", tipoDonante='" + getTipoDonante() + "'" +
            ", ruta=" + getRuta() +
            ", direccionDonante='" + getDireccionDonante() + "'" +
            ", telefonoDonante='" + getTelefonoDonante() + "'" +
            ", emailDonante='" + getEmailDonante() + "'" +
            ", responsableDonante='" + getResponsableDonante() + "'" +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", fechaBaja='" + getFechaBaja() + "'" +
            ", comentarios='" + getComentarios() + "'" +
            "}";
    }
}
