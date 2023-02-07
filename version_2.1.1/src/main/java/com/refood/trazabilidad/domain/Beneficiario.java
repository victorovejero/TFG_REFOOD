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
 * A Beneficiario.
 */
@Entity
@Table(name = "beneficiario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Beneficiario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_beneficiario", nullable = false)
    private String idBeneficiario;

    @NotNull
    @Column(name = "nombre_representante", nullable = false)
    private String nombreRepresentante;

    @NotNull
    @Column(name = "primer_apellido_representante", nullable = false)
    private String primerApellidoRepresentante;

    @Column(name = "segundo_apellido_representante")
    private String segundoApellidoRepresentante;

    @NotNull
    @Column(name = "numero_personas", nullable = false)
    private Integer numeroPersonas;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @NotNull
    @Column(name = "telefono_secundario", nullable = false)
    private String telefonoSecundario;

    @NotNull
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @NotNull
    @Column(name = "codigo_postal", nullable = false)
    private String codigoPostal;

    @NotNull
    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;

    @Column(name = "fecha_baja")
    private LocalDate fechaBaja;

    @NotNull
    @Column(name = "numero_ninios", nullable = false)
    private Integer numeroNinios;

    @Column(name = "id_dual")
    private String idDual;

    @NotNull
    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "beneficiario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tupper", "beneficiario", "alimentoDeEntrada", "checkouts" }, allowSetters = true)
    private Set<AlimentoDeSalida> alimentoDeSalidas = new HashSet<>();

    @OneToMany(mappedBy = "beneficiario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "intolerancias", "beneficiario" }, allowSetters = true)
    private Set<PersonaBeneficiaria> personaBeneficiarias = new HashSet<>();

    @OneToMany(mappedBy = "beneficiario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "alimentoDeSalidas", "beneficiario" }, allowSetters = true)
    private Set<Checkout> checkouts = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_beneficiario__intolerancia",
        joinColumns = @JoinColumn(name = "beneficiario_id"),
        inverseJoinColumns = @JoinColumn(name = "intolerancia_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "beneficiarios", "personaBeneficiarias", "tipoDeAlimentos" }, allowSetters = true)
    private Set<Intolerancia> intolerancias = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "donantes", "beneficiarios", "voluntarios" }, allowSetters = true)
    private Nucleo nucleo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Beneficiario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdBeneficiario() {
        return this.idBeneficiario;
    }

    public Beneficiario idBeneficiario(String idBeneficiario) {
        this.setIdBeneficiario(idBeneficiario);
        return this;
    }

    public void setIdBeneficiario(String idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    public String getNombreRepresentante() {
        return this.nombreRepresentante;
    }

    public Beneficiario nombreRepresentante(String nombreRepresentante) {
        this.setNombreRepresentante(nombreRepresentante);
        return this;
    }

    public void setNombreRepresentante(String nombreRepresentante) {
        this.nombreRepresentante = nombreRepresentante;
    }

    public String getPrimerApellidoRepresentante() {
        return this.primerApellidoRepresentante;
    }

    public Beneficiario primerApellidoRepresentante(String primerApellidoRepresentante) {
        this.setPrimerApellidoRepresentante(primerApellidoRepresentante);
        return this;
    }

    public void setPrimerApellidoRepresentante(String primerApellidoRepresentante) {
        this.primerApellidoRepresentante = primerApellidoRepresentante;
    }

    public String getSegundoApellidoRepresentante() {
        return this.segundoApellidoRepresentante;
    }

    public Beneficiario segundoApellidoRepresentante(String segundoApellidoRepresentante) {
        this.setSegundoApellidoRepresentante(segundoApellidoRepresentante);
        return this;
    }

    public void setSegundoApellidoRepresentante(String segundoApellidoRepresentante) {
        this.segundoApellidoRepresentante = segundoApellidoRepresentante;
    }

    public Integer getNumeroPersonas() {
        return this.numeroPersonas;
    }

    public Beneficiario numeroPersonas(Integer numeroPersonas) {
        this.setNumeroPersonas(numeroPersonas);
        return this;
    }

    public void setNumeroPersonas(Integer numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }

    public String getEmail() {
        return this.email;
    }

    public Beneficiario email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Beneficiario telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefonoSecundario() {
        return this.telefonoSecundario;
    }

    public Beneficiario telefonoSecundario(String telefonoSecundario) {
        this.setTelefonoSecundario(telefonoSecundario);
        return this;
    }

    public void setTelefonoSecundario(String telefonoSecundario) {
        this.telefonoSecundario = telefonoSecundario;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Beneficiario direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoPostal() {
        return this.codigoPostal;
    }

    public Beneficiario codigoPostal(String codigoPostal) {
        this.setCodigoPostal(codigoPostal);
        return this;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public LocalDate getFechaAlta() {
        return this.fechaAlta;
    }

    public Beneficiario fechaAlta(LocalDate fechaAlta) {
        this.setFechaAlta(fechaAlta);
        return this;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public LocalDate getFechaBaja() {
        return this.fechaBaja;
    }

    public Beneficiario fechaBaja(LocalDate fechaBaja) {
        this.setFechaBaja(fechaBaja);
        return this;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public Integer getNumeroNinios() {
        return this.numeroNinios;
    }

    public Beneficiario numeroNinios(Integer numeroNinios) {
        this.setNumeroNinios(numeroNinios);
        return this;
    }

    public void setNumeroNinios(Integer numeroNinios) {
        this.numeroNinios = numeroNinios;
    }

    public String getIdDual() {
        return this.idDual;
    }

    public Beneficiario idDual(String idDual) {
        this.setIdDual(idDual);
        return this;
    }

    public void setIdDual(String idDual) {
        this.idDual = idDual;
    }

    public Boolean getActivo() {
        return this.activo;
    }

    public Beneficiario activo(Boolean activo) {
        this.setActivo(activo);
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Set<AlimentoDeSalida> getAlimentoDeSalidas() {
        return this.alimentoDeSalidas;
    }

    public void setAlimentoDeSalidas(Set<AlimentoDeSalida> alimentoDeSalidas) {
        if (this.alimentoDeSalidas != null) {
            this.alimentoDeSalidas.forEach(i -> i.setBeneficiario(null));
        }
        if (alimentoDeSalidas != null) {
            alimentoDeSalidas.forEach(i -> i.setBeneficiario(this));
        }
        this.alimentoDeSalidas = alimentoDeSalidas;
    }

    public Beneficiario alimentoDeSalidas(Set<AlimentoDeSalida> alimentoDeSalidas) {
        this.setAlimentoDeSalidas(alimentoDeSalidas);
        return this;
    }

    public Beneficiario addAlimentoDeSalida(AlimentoDeSalida alimentoDeSalida) {
        this.alimentoDeSalidas.add(alimentoDeSalida);
        alimentoDeSalida.setBeneficiario(this);
        return this;
    }

    public Beneficiario removeAlimentoDeSalida(AlimentoDeSalida alimentoDeSalida) {
        this.alimentoDeSalidas.remove(alimentoDeSalida);
        alimentoDeSalida.setBeneficiario(null);
        return this;
    }

    public Set<PersonaBeneficiaria> getPersonaBeneficiarias() {
        return this.personaBeneficiarias;
    }

    public void setPersonaBeneficiarias(Set<PersonaBeneficiaria> personaBeneficiarias) {
        if (this.personaBeneficiarias != null) {
            this.personaBeneficiarias.forEach(i -> i.setBeneficiario(null));
        }
        if (personaBeneficiarias != null) {
            personaBeneficiarias.forEach(i -> i.setBeneficiario(this));
        }
        this.personaBeneficiarias = personaBeneficiarias;
    }

    public Beneficiario personaBeneficiarias(Set<PersonaBeneficiaria> personaBeneficiarias) {
        this.setPersonaBeneficiarias(personaBeneficiarias);
        return this;
    }

    public Beneficiario addPersonaBeneficiaria(PersonaBeneficiaria personaBeneficiaria) {
        this.personaBeneficiarias.add(personaBeneficiaria);
        personaBeneficiaria.setBeneficiario(this);
        return this;
    }

    public Beneficiario removePersonaBeneficiaria(PersonaBeneficiaria personaBeneficiaria) {
        this.personaBeneficiarias.remove(personaBeneficiaria);
        personaBeneficiaria.setBeneficiario(null);
        return this;
    }

    public Set<Checkout> getCheckouts() {
        return this.checkouts;
    }

    public void setCheckouts(Set<Checkout> checkouts) {
        if (this.checkouts != null) {
            this.checkouts.forEach(i -> i.setBeneficiario(null));
        }
        if (checkouts != null) {
            checkouts.forEach(i -> i.setBeneficiario(this));
        }
        this.checkouts = checkouts;
    }

    public Beneficiario checkouts(Set<Checkout> checkouts) {
        this.setCheckouts(checkouts);
        return this;
    }

    public Beneficiario addCheckout(Checkout checkout) {
        this.checkouts.add(checkout);
        checkout.setBeneficiario(this);
        return this;
    }

    public Beneficiario removeCheckout(Checkout checkout) {
        this.checkouts.remove(checkout);
        checkout.setBeneficiario(null);
        return this;
    }

    public Set<Intolerancia> getIntolerancias() {
        return this.intolerancias;
    }

    public void setIntolerancias(Set<Intolerancia> intolerancias) {
        this.intolerancias = intolerancias;
    }

    public Beneficiario intolerancias(Set<Intolerancia> intolerancias) {
        this.setIntolerancias(intolerancias);
        return this;
    }

    public Beneficiario addIntolerancia(Intolerancia intolerancia) {
        this.intolerancias.add(intolerancia);
        intolerancia.getBeneficiarios().add(this);
        return this;
    }

    public Beneficiario removeIntolerancia(Intolerancia intolerancia) {
        this.intolerancias.remove(intolerancia);
        intolerancia.getBeneficiarios().remove(this);
        return this;
    }

    public Nucleo getNucleo() {
        return this.nucleo;
    }

    public void setNucleo(Nucleo nucleo) {
        this.nucleo = nucleo;
    }

    public Beneficiario nucleo(Nucleo nucleo) {
        this.setNucleo(nucleo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Beneficiario)) {
            return false;
        }
        return id != null && id.equals(((Beneficiario) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Beneficiario{" +
            "id=" + getId() +
            ", idBeneficiario='" + getIdBeneficiario() + "'" +
            ", nombreRepresentante='" + getNombreRepresentante() + "'" +
            ", primerApellidoRepresentante='" + getPrimerApellidoRepresentante() + "'" +
            ", segundoApellidoRepresentante='" + getSegundoApellidoRepresentante() + "'" +
            ", numeroPersonas=" + getNumeroPersonas() +
            ", email='" + getEmail() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", telefonoSecundario='" + getTelefonoSecundario() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", codigoPostal='" + getCodigoPostal() + "'" +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", fechaBaja='" + getFechaBaja() + "'" +
            ", numeroNinios=" + getNumeroNinios() +
            ", idDual='" + getIdDual() + "'" +
            ", activo='" + getActivo() + "'" +
            "}";
    }
}
