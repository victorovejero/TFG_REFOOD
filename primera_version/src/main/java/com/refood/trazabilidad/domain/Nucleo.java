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
 * A Nucleo.
 */
@Entity
@Table(name = "nucleo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Nucleo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre_nucleo", nullable = false)
    private String nombreNucleo;

    @NotNull
    @Column(name = "direccion_nucleo", nullable = false)
    private String direccionNucleo;

    @NotNull
    @Column(name = "provincia_nucleo", nullable = false)
    private String provinciaNucleo;

    @NotNull
    @Column(name = "responsable_nucleo", nullable = false)
    private String responsableNucleo;

    @NotNull
    @Column(name = "telefono_nucleo", nullable = false)
    private String telefonoNucleo;

    @NotNull
    @Column(name = "email_nucleo", nullable = false)
    private String emailNucleo;

    @NotNull
    @Column(name = "numero_rutas", nullable = false)
    private Integer numeroRutas;

    @OneToMany(mappedBy = "nucleo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "alimentoDeEntradas", "nucleo" }, allowSetters = true)
    private Set<Donante> donantes = new HashSet<>();

    @OneToMany(mappedBy = "nucleo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "alimentoDeSalidas", "nucleo", "intolerancias" }, allowSetters = true)
    private Set<Beneficiario> beneficiarios = new HashSet<>();

    @OneToMany(mappedBy = "nucleo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nucleo", "registros" }, allowSetters = true)
    private Set<Voluntario> voluntarios = new HashSet<>();

    @OneToMany(mappedBy = "nucleo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nucleo" }, allowSetters = true)
    private Set<Socio> socios = new HashSet<>();

    @OneToMany(mappedBy = "nucleo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "voluntarios", "nucleo" }, allowSetters = true)
    private Set<Registro> registros = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Nucleo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreNucleo() {
        return this.nombreNucleo;
    }

    public Nucleo nombreNucleo(String nombreNucleo) {
        this.setNombreNucleo(nombreNucleo);
        return this;
    }

    public void setNombreNucleo(String nombreNucleo) {
        this.nombreNucleo = nombreNucleo;
    }

    public String getDireccionNucleo() {
        return this.direccionNucleo;
    }

    public Nucleo direccionNucleo(String direccionNucleo) {
        this.setDireccionNucleo(direccionNucleo);
        return this;
    }

    public void setDireccionNucleo(String direccionNucleo) {
        this.direccionNucleo = direccionNucleo;
    }

    public String getProvinciaNucleo() {
        return this.provinciaNucleo;
    }

    public Nucleo provinciaNucleo(String provinciaNucleo) {
        this.setProvinciaNucleo(provinciaNucleo);
        return this;
    }

    public void setProvinciaNucleo(String provinciaNucleo) {
        this.provinciaNucleo = provinciaNucleo;
    }

    public String getResponsableNucleo() {
        return this.responsableNucleo;
    }

    public Nucleo responsableNucleo(String responsableNucleo) {
        this.setResponsableNucleo(responsableNucleo);
        return this;
    }

    public void setResponsableNucleo(String responsableNucleo) {
        this.responsableNucleo = responsableNucleo;
    }

    public String getTelefonoNucleo() {
        return this.telefonoNucleo;
    }

    public Nucleo telefonoNucleo(String telefonoNucleo) {
        this.setTelefonoNucleo(telefonoNucleo);
        return this;
    }

    public void setTelefonoNucleo(String telefonoNucleo) {
        this.telefonoNucleo = telefonoNucleo;
    }

    public String getEmailNucleo() {
        return this.emailNucleo;
    }

    public Nucleo emailNucleo(String emailNucleo) {
        this.setEmailNucleo(emailNucleo);
        return this;
    }

    public void setEmailNucleo(String emailNucleo) {
        this.emailNucleo = emailNucleo;
    }

    public Integer getNumeroRutas() {
        return this.numeroRutas;
    }

    public Nucleo numeroRutas(Integer numeroRutas) {
        this.setNumeroRutas(numeroRutas);
        return this;
    }

    public void setNumeroRutas(Integer numeroRutas) {
        this.numeroRutas = numeroRutas;
    }

    public Set<Donante> getDonantes() {
        return this.donantes;
    }

    public void setDonantes(Set<Donante> donantes) {
        if (this.donantes != null) {
            this.donantes.forEach(i -> i.setNucleo(null));
        }
        if (donantes != null) {
            donantes.forEach(i -> i.setNucleo(this));
        }
        this.donantes = donantes;
    }

    public Nucleo donantes(Set<Donante> donantes) {
        this.setDonantes(donantes);
        return this;
    }

    public Nucleo addDonante(Donante donante) {
        this.donantes.add(donante);
        donante.setNucleo(this);
        return this;
    }

    public Nucleo removeDonante(Donante donante) {
        this.donantes.remove(donante);
        donante.setNucleo(null);
        return this;
    }

    public Set<Beneficiario> getBeneficiarios() {
        return this.beneficiarios;
    }

    public void setBeneficiarios(Set<Beneficiario> beneficiarios) {
        if (this.beneficiarios != null) {
            this.beneficiarios.forEach(i -> i.setNucleo(null));
        }
        if (beneficiarios != null) {
            beneficiarios.forEach(i -> i.setNucleo(this));
        }
        this.beneficiarios = beneficiarios;
    }

    public Nucleo beneficiarios(Set<Beneficiario> beneficiarios) {
        this.setBeneficiarios(beneficiarios);
        return this;
    }

    public Nucleo addBeneficiario(Beneficiario beneficiario) {
        this.beneficiarios.add(beneficiario);
        beneficiario.setNucleo(this);
        return this;
    }

    public Nucleo removeBeneficiario(Beneficiario beneficiario) {
        this.beneficiarios.remove(beneficiario);
        beneficiario.setNucleo(null);
        return this;
    }

    public Set<Voluntario> getVoluntarios() {
        return this.voluntarios;
    }

    public void setVoluntarios(Set<Voluntario> voluntarios) {
        if (this.voluntarios != null) {
            this.voluntarios.forEach(i -> i.setNucleo(null));
        }
        if (voluntarios != null) {
            voluntarios.forEach(i -> i.setNucleo(this));
        }
        this.voluntarios = voluntarios;
    }

    public Nucleo voluntarios(Set<Voluntario> voluntarios) {
        this.setVoluntarios(voluntarios);
        return this;
    }

    public Nucleo addVoluntario(Voluntario voluntario) {
        this.voluntarios.add(voluntario);
        voluntario.setNucleo(this);
        return this;
    }

    public Nucleo removeVoluntario(Voluntario voluntario) {
        this.voluntarios.remove(voluntario);
        voluntario.setNucleo(null);
        return this;
    }

    public Set<Socio> getSocios() {
        return this.socios;
    }

    public void setSocios(Set<Socio> socios) {
        if (this.socios != null) {
            this.socios.forEach(i -> i.setNucleo(null));
        }
        if (socios != null) {
            socios.forEach(i -> i.setNucleo(this));
        }
        this.socios = socios;
    }

    public Nucleo socios(Set<Socio> socios) {
        this.setSocios(socios);
        return this;
    }

    public Nucleo addSocio(Socio socio) {
        this.socios.add(socio);
        socio.setNucleo(this);
        return this;
    }

    public Nucleo removeSocio(Socio socio) {
        this.socios.remove(socio);
        socio.setNucleo(null);
        return this;
    }

    public Set<Registro> getRegistros() {
        return this.registros;
    }

    public void setRegistros(Set<Registro> registros) {
        if (this.registros != null) {
            this.registros.forEach(i -> i.setNucleo(null));
        }
        if (registros != null) {
            registros.forEach(i -> i.setNucleo(this));
        }
        this.registros = registros;
    }

    public Nucleo registros(Set<Registro> registros) {
        this.setRegistros(registros);
        return this;
    }

    public Nucleo addRegistro(Registro registro) {
        this.registros.add(registro);
        registro.setNucleo(this);
        return this;
    }

    public Nucleo removeRegistro(Registro registro) {
        this.registros.remove(registro);
        registro.setNucleo(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nucleo)) {
            return false;
        }
        return id != null && id.equals(((Nucleo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nucleo{" +
            "id=" + getId() +
            ", nombreNucleo='" + getNombreNucleo() + "'" +
            ", direccionNucleo='" + getDireccionNucleo() + "'" +
            ", provinciaNucleo='" + getProvinciaNucleo() + "'" +
            ", responsableNucleo='" + getResponsableNucleo() + "'" +
            ", telefonoNucleo='" + getTelefonoNucleo() + "'" +
            ", emailNucleo='" + getEmailNucleo() + "'" +
            ", numeroRutas=" + getNumeroRutas() +
            "}";
    }
}
