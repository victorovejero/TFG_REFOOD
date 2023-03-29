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
    @Column(name = "id_nucleo", nullable = false)
    private String idNucleo;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @NotNull
    @Column(name = "codigo_postal", nullable = false)
    private String codigoPostal;

    @NotNull
    @Column(name = "provincia", nullable = false)
    private String provincia;

    @NotNull
    @Column(name = "responsable", nullable = false)
    private String responsable;

    @NotNull
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "nucleo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "alEnts", "nucleo" }, allowSetters = true)
    private Set<Donante> donantes = new HashSet<>();

    @OneToMany(mappedBy = "nucleo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "alSals", "pBenefs", "checkouts", "intols", "nucleo" }, allowSetters = true)
    private Set<Benef> benefs = new HashSet<>();

    @OneToMany(mappedBy = "nucleo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nucleo" }, allowSetters = true)
    private Set<Voluntario> voluntarios = new HashSet<>();

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

    public String getIdNucleo() {
        return this.idNucleo;
    }

    public Nucleo idNucleo(String idNucleo) {
        this.setIdNucleo(idNucleo);
        return this;
    }

    public void setIdNucleo(String idNucleo) {
        this.idNucleo = idNucleo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Nucleo nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Nucleo direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoPostal() {
        return this.codigoPostal;
    }

    public Nucleo codigoPostal(String codigoPostal) {
        this.setCodigoPostal(codigoPostal);
        return this;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getProvincia() {
        return this.provincia;
    }

    public Nucleo provincia(String provincia) {
        this.setProvincia(provincia);
        return this;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getResponsable() {
        return this.responsable;
    }

    public Nucleo responsable(String responsable) {
        this.setResponsable(responsable);
        return this;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Nucleo telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return this.email;
    }

    public Nucleo email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActivo() {
        return this.activo;
    }

    public Nucleo activo(Boolean activo) {
        this.setActivo(activo);
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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

    public Set<Benef> getBenefs() {
        return this.benefs;
    }

    public void setBenefs(Set<Benef> benefs) {
        if (this.benefs != null) {
            this.benefs.forEach(i -> i.setNucleo(null));
        }
        if (benefs != null) {
            benefs.forEach(i -> i.setNucleo(this));
        }
        this.benefs = benefs;
    }

    public Nucleo benefs(Set<Benef> benefs) {
        this.setBenefs(benefs);
        return this;
    }

    public Nucleo addBenef(Benef benef) {
        this.benefs.add(benef);
        benef.setNucleo(this);
        return this;
    }

    public Nucleo removeBenef(Benef benef) {
        this.benefs.remove(benef);
        benef.setNucleo(null);
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
            ", idNucleo='" + getIdNucleo() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", codigoPostal='" + getCodigoPostal() + "'" +
            ", provincia='" + getProvincia() + "'" +
            ", responsable='" + getResponsable() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", email='" + getEmail() + "'" +
            ", activo='" + getActivo() + "'" +
            "}";
    }
}
