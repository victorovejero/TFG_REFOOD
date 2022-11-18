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
 * A Voluntario.
 */
@Entity
@Table(name = "voluntario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Voluntario implements Serializable {

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
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "telefono_contacto", nullable = false)
    private String telefonoContacto;

    @Column(name = "dni")
    private String dni;

    @NotNull
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @NotNull
    @Column(name = "sexo", nullable = false)
    private String sexo;

    @NotNull
    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;

    @Column(name = "fecha_baja")
    private LocalDate fechaBaja;

    @NotNull
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "tipo_turno")
    private String tipoTurno;

    @Column(name = "responsable_dia")
    private Boolean responsableDia;

    @Column(name = "origen")
    private String origen;

    @NotNull
    @Column(name = "manipulador_alimentos", nullable = false)
    private Boolean manipuladorAlimentos;

    @NotNull
    @Column(name = "codigo_postal", nullable = false)
    private String codigoPostal;

    @NotNull
    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @ManyToOne
    @JsonIgnoreProperties(value = { "donantes", "beneficiarios", "voluntarios", "socios", "registros" }, allowSetters = true)
    private Nucleo nucleo;

    @ManyToMany(mappedBy = "voluntarios")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "voluntarios", "nucleo" }, allowSetters = true)
    private Set<Registro> registros = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Voluntario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Voluntario nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return this.primerApellido;
    }

    public Voluntario primerApellido(String primerApellido) {
        this.setPrimerApellido(primerApellido);
        return this;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return this.segundoApellido;
    }

    public Voluntario segundoApellido(String segundoApellido) {
        this.setSegundoApellido(segundoApellido);
        return this;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getEmail() {
        return this.email;
    }

    public Voluntario email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefonoContacto() {
        return this.telefonoContacto;
    }

    public Voluntario telefonoContacto(String telefonoContacto) {
        this.setTelefonoContacto(telefonoContacto);
        return this;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getDni() {
        return this.dni;
    }

    public Voluntario dni(String dni) {
        this.setDni(dni);
        return this;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public Voluntario fechaNacimiento(LocalDate fechaNacimiento) {
        this.setFechaNacimiento(fechaNacimiento);
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return this.sexo;
    }

    public Voluntario sexo(String sexo) {
        this.setSexo(sexo);
        return this;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public LocalDate getFechaAlta() {
        return this.fechaAlta;
    }

    public Voluntario fechaAlta(LocalDate fechaAlta) {
        this.setFechaAlta(fechaAlta);
        return this;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public LocalDate getFechaBaja() {
        return this.fechaBaja;
    }

    public Voluntario fechaBaja(LocalDate fechaBaja) {
        this.setFechaBaja(fechaBaja);
        return this;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getTipo() {
        return this.tipo;
    }

    public Voluntario tipo(String tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipoTurno() {
        return this.tipoTurno;
    }

    public Voluntario tipoTurno(String tipoTurno) {
        this.setTipoTurno(tipoTurno);
        return this;
    }

    public void setTipoTurno(String tipoTurno) {
        this.tipoTurno = tipoTurno;
    }

    public Boolean getResponsableDia() {
        return this.responsableDia;
    }

    public Voluntario responsableDia(Boolean responsableDia) {
        this.setResponsableDia(responsableDia);
        return this;
    }

    public void setResponsableDia(Boolean responsableDia) {
        this.responsableDia = responsableDia;
    }

    public String getOrigen() {
        return this.origen;
    }

    public Voluntario origen(String origen) {
        this.setOrigen(origen);
        return this;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Boolean getManipuladorAlimentos() {
        return this.manipuladorAlimentos;
    }

    public Voluntario manipuladorAlimentos(Boolean manipuladorAlimentos) {
        this.setManipuladorAlimentos(manipuladorAlimentos);
        return this;
    }

    public void setManipuladorAlimentos(Boolean manipuladorAlimentos) {
        this.manipuladorAlimentos = manipuladorAlimentos;
    }

    public String getCodigoPostal() {
        return this.codigoPostal;
    }

    public Voluntario codigoPostal(String codigoPostal) {
        this.setCodigoPostal(codigoPostal);
        return this;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Boolean getActivo() {
        return this.activo;
    }

    public Voluntario activo(Boolean activo) {
        this.setActivo(activo);
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Nucleo getNucleo() {
        return this.nucleo;
    }

    public void setNucleo(Nucleo nucleo) {
        this.nucleo = nucleo;
    }

    public Voluntario nucleo(Nucleo nucleo) {
        this.setNucleo(nucleo);
        return this;
    }

    public Set<Registro> getRegistros() {
        return this.registros;
    }

    public void setRegistros(Set<Registro> registros) {
        if (this.registros != null) {
            this.registros.forEach(i -> i.removeVoluntario(this));
        }
        if (registros != null) {
            registros.forEach(i -> i.addVoluntario(this));
        }
        this.registros = registros;
    }

    public Voluntario registros(Set<Registro> registros) {
        this.setRegistros(registros);
        return this;
    }

    public Voluntario addRegistro(Registro registro) {
        this.registros.add(registro);
        registro.getVoluntarios().add(this);
        return this;
    }

    public Voluntario removeRegistro(Registro registro) {
        this.registros.remove(registro);
        registro.getVoluntarios().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Voluntario)) {
            return false;
        }
        return id != null && id.equals(((Voluntario) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Voluntario{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", primerApellido='" + getPrimerApellido() + "'" +
            ", segundoApellido='" + getSegundoApellido() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefonoContacto='" + getTelefonoContacto() + "'" +
            ", dni='" + getDni() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", fechaBaja='" + getFechaBaja() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", tipoTurno='" + getTipoTurno() + "'" +
            ", responsableDia='" + getResponsableDia() + "'" +
            ", origen='" + getOrigen() + "'" +
            ", manipuladorAlimentos='" + getManipuladorAlimentos() + "'" +
            ", codigoPostal='" + getCodigoPostal() + "'" +
            ", activo='" + getActivo() + "'" +
            "}";
    }
}
