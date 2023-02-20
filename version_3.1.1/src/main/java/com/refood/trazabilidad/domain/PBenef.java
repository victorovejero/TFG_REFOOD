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
 * A PBenef.
 */
@Entity
@Table(name = "p_benef")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PBenef implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "primer_apellido", nullable = false)
    private String primerApellido;

    @Column(name = "segundo_apellido")
    private String segundoApellido;

    @NotNull
    @Column(name = "fecha_nacimiento", nullable = false)
    private String fechaNacimiento;

    @NotNull
    @Column(name = "sexo", nullable = false)
    private String sexo;

    @NotNull
    @Column(name = "parentesco", nullable = false)
    private String parentesco;

    @NotNull
    @Column(name = "situacion_profesional", nullable = false)
    private String situacionProfesional;

    @ManyToMany
    @JoinTable(
        name = "rel_p_benef__intol",
        joinColumns = @JoinColumn(name = "p_benef_id"),
        inverseJoinColumns = @JoinColumn(name = "intol_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "benefs", "pBenefs", "tipoAls" }, allowSetters = true)
    private Set<Intol> intols = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "alSals", "pBenefs", "checkouts", "intols", "nucleo" }, allowSetters = true)
    private Benef benef;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PBenef id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public PBenef nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return this.primerApellido;
    }

    public PBenef primerApellido(String primerApellido) {
        this.setPrimerApellido(primerApellido);
        return this;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return this.segundoApellido;
    }

    public PBenef segundoApellido(String segundoApellido) {
        this.setSegundoApellido(segundoApellido);
        return this;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public PBenef fechaNacimiento(String fechaNacimiento) {
        this.setFechaNacimiento(fechaNacimiento);
        return this;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return this.sexo;
    }

    public PBenef sexo(String sexo) {
        this.setSexo(sexo);
        return this;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getParentesco() {
        return this.parentesco;
    }

    public PBenef parentesco(String parentesco) {
        this.setParentesco(parentesco);
        return this;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getSituacionProfesional() {
        return this.situacionProfesional;
    }

    public PBenef situacionProfesional(String situacionProfesional) {
        this.setSituacionProfesional(situacionProfesional);
        return this;
    }

    public void setSituacionProfesional(String situacionProfesional) {
        this.situacionProfesional = situacionProfesional;
    }

    public Set<Intol> getIntols() {
        return this.intols;
    }

    public void setIntols(Set<Intol> intols) {
        this.intols = intols;
    }

    public PBenef intols(Set<Intol> intols) {
        this.setIntols(intols);
        return this;
    }

    public PBenef addIntol(Intol intol) {
        this.intols.add(intol);
        intol.getPBenefs().add(this);
        return this;
    }

    public PBenef removeIntol(Intol intol) {
        this.intols.remove(intol);
        intol.getPBenefs().remove(this);
        return this;
    }

    public Benef getBenef() {
        return this.benef;
    }

    public void setBenef(Benef benef) {
        this.benef = benef;
    }

    public PBenef benef(Benef benef) {
        this.setBenef(benef);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PBenef)) {
            return false;
        }
        return id != null && id.equals(((PBenef) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PBenef{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", primerApellido='" + getPrimerApellido() + "'" +
            ", segundoApellido='" + getSegundoApellido() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", parentesco='" + getParentesco() + "'" +
            ", situacionProfesional='" + getSituacionProfesional() + "'" +
            "}";
    }
}
