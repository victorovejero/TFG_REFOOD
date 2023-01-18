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
 * A Benef.
 */
@Entity
@Table(name = "benef")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Benef implements Serializable {

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

    @OneToMany(mappedBy = "benef")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tupper", "benef", "alEnt", "checkouts" }, allowSetters = true)
    private Set<AlSal> alSals = new HashSet<>();

    @OneToMany(mappedBy = "benef")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "intols", "benef" }, allowSetters = true)
    private Set<PBenef> pBenefs = new HashSet<>();

    @OneToMany(mappedBy = "benef")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "alSals", "benef" }, allowSetters = true)
    private Set<Checkout> checkouts = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "rel_benef__intol", joinColumns = @JoinColumn(name = "benef_id"), inverseJoinColumns = @JoinColumn(name = "intol_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "benefs", "pBenefs", "tipoAls" }, allowSetters = true)
    private Set<Intol> intols = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "donantes", "benefs", "voluntarios" }, allowSetters = true)
    private Nucleo nucleo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Benef id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdBeneficiario() {
        return this.idBeneficiario;
    }

    public Benef idBeneficiario(String idBeneficiario) {
        this.setIdBeneficiario(idBeneficiario);
        return this;
    }

    public void setIdBeneficiario(String idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    public String getNombreRepresentante() {
        return this.nombreRepresentante;
    }

    public Benef nombreRepresentante(String nombreRepresentante) {
        this.setNombreRepresentante(nombreRepresentante);
        return this;
    }

    public void setNombreRepresentante(String nombreRepresentante) {
        this.nombreRepresentante = nombreRepresentante;
    }

    public String getPrimerApellidoRepresentante() {
        return this.primerApellidoRepresentante;
    }

    public Benef primerApellidoRepresentante(String primerApellidoRepresentante) {
        this.setPrimerApellidoRepresentante(primerApellidoRepresentante);
        return this;
    }

    public void setPrimerApellidoRepresentante(String primerApellidoRepresentante) {
        this.primerApellidoRepresentante = primerApellidoRepresentante;
    }

    public String getSegundoApellidoRepresentante() {
        return this.segundoApellidoRepresentante;
    }

    public Benef segundoApellidoRepresentante(String segundoApellidoRepresentante) {
        this.setSegundoApellidoRepresentante(segundoApellidoRepresentante);
        return this;
    }

    public void setSegundoApellidoRepresentante(String segundoApellidoRepresentante) {
        this.segundoApellidoRepresentante = segundoApellidoRepresentante;
    }

    public Integer getNumeroPersonas() {
        return this.numeroPersonas;
    }

    public Benef numeroPersonas(Integer numeroPersonas) {
        this.setNumeroPersonas(numeroPersonas);
        return this;
    }

    public void setNumeroPersonas(Integer numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }

    public String getEmail() {
        return this.email;
    }

    public Benef email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Benef telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefonoSecundario() {
        return this.telefonoSecundario;
    }

    public Benef telefonoSecundario(String telefonoSecundario) {
        this.setTelefonoSecundario(telefonoSecundario);
        return this;
    }

    public void setTelefonoSecundario(String telefonoSecundario) {
        this.telefonoSecundario = telefonoSecundario;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Benef direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoPostal() {
        return this.codigoPostal;
    }

    public Benef codigoPostal(String codigoPostal) {
        this.setCodigoPostal(codigoPostal);
        return this;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public LocalDate getFechaAlta() {
        return this.fechaAlta;
    }

    public Benef fechaAlta(LocalDate fechaAlta) {
        this.setFechaAlta(fechaAlta);
        return this;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public LocalDate getFechaBaja() {
        return this.fechaBaja;
    }

    public Benef fechaBaja(LocalDate fechaBaja) {
        this.setFechaBaja(fechaBaja);
        return this;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public Integer getNumeroNinios() {
        return this.numeroNinios;
    }

    public Benef numeroNinios(Integer numeroNinios) {
        this.setNumeroNinios(numeroNinios);
        return this;
    }

    public void setNumeroNinios(Integer numeroNinios) {
        this.numeroNinios = numeroNinios;
    }

    public String getIdDual() {
        return this.idDual;
    }

    public Benef idDual(String idDual) {
        this.setIdDual(idDual);
        return this;
    }

    public void setIdDual(String idDual) {
        this.idDual = idDual;
    }

    public Boolean getActivo() {
        return this.activo;
    }

    public Benef activo(Boolean activo) {
        this.setActivo(activo);
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Set<AlSal> getAlSals() {
        return this.alSals;
    }

    public void setAlSals(Set<AlSal> alSals) {
        if (this.alSals != null) {
            this.alSals.forEach(i -> i.setBenef(null));
        }
        if (alSals != null) {
            alSals.forEach(i -> i.setBenef(this));
        }
        this.alSals = alSals;
    }

    public Benef alSals(Set<AlSal> alSals) {
        this.setAlSals(alSals);
        return this;
    }

    public Benef addAlSal(AlSal alSal) {
        this.alSals.add(alSal);
        alSal.setBenef(this);
        return this;
    }

    public Benef removeAlSal(AlSal alSal) {
        this.alSals.remove(alSal);
        alSal.setBenef(null);
        return this;
    }

    public Set<PBenef> getPBenefs() {
        return this.pBenefs;
    }

    public void setPBenefs(Set<PBenef> pBenefs) {
        if (this.pBenefs != null) {
            this.pBenefs.forEach(i -> i.setBenef(null));
        }
        if (pBenefs != null) {
            pBenefs.forEach(i -> i.setBenef(this));
        }
        this.pBenefs = pBenefs;
    }

    public Benef pBenefs(Set<PBenef> pBenefs) {
        this.setPBenefs(pBenefs);
        return this;
    }

    public Benef addPBenef(PBenef pBenef) {
        this.pBenefs.add(pBenef);
        pBenef.setBenef(this);
        return this;
    }

    public Benef removePBenef(PBenef pBenef) {
        this.pBenefs.remove(pBenef);
        pBenef.setBenef(null);
        return this;
    }

    public Set<Checkout> getCheckouts() {
        return this.checkouts;
    }

    public void setCheckouts(Set<Checkout> checkouts) {
        if (this.checkouts != null) {
            this.checkouts.forEach(i -> i.setBenef(null));
        }
        if (checkouts != null) {
            checkouts.forEach(i -> i.setBenef(this));
        }
        this.checkouts = checkouts;
    }

    public Benef checkouts(Set<Checkout> checkouts) {
        this.setCheckouts(checkouts);
        return this;
    }

    public Benef addCheckout(Checkout checkout) {
        this.checkouts.add(checkout);
        checkout.setBenef(this);
        return this;
    }

    public Benef removeCheckout(Checkout checkout) {
        this.checkouts.remove(checkout);
        checkout.setBenef(null);
        return this;
    }

    public Set<Intol> getIntols() {
        return this.intols;
    }

    public void setIntols(Set<Intol> intols) {
        this.intols = intols;
    }

    public Benef intols(Set<Intol> intols) {
        this.setIntols(intols);
        return this;
    }

    public Benef addIntol(Intol intol) {
        this.intols.add(intol);
        intol.getBenefs().add(this);
        return this;
    }

    public Benef removeIntol(Intol intol) {
        this.intols.remove(intol);
        intol.getBenefs().remove(this);
        return this;
    }

    public Nucleo getNucleo() {
        return this.nucleo;
    }

    public void setNucleo(Nucleo nucleo) {
        this.nucleo = nucleo;
    }

    public Benef nucleo(Nucleo nucleo) {
        this.setNucleo(nucleo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Benef)) {
            return false;
        }
        return id != null && id.equals(((Benef) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Benef{" +
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
