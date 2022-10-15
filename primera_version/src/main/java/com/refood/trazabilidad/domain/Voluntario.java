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
    @Column(name = "nombre_voluntario", nullable = false)
    private String nombreVoluntario;

    @NotNull
    @Column(name = "primer_apellido", nullable = false)
    private String primerApellido;

    @Column(name = "segundo_apellido")
    private String segundoApellido;

    @NotNull
    @Column(name = "email_voluntario", nullable = false)
    private String emailVoluntario;

    @NotNull
    @Column(name = "telefono_contacto_voluntario", nullable = false)
    private String telefonoContactoVoluntario;

    @Column(name = "dni_voluntario")
    private String dniVoluntario;

    @NotNull
    @Column(name = "fecha_nacimiento_voluntario", nullable = false)
    private LocalDate fechaNacimientoVoluntario;

    @NotNull
    @Column(name = "sexo_voluntario", nullable = false)
    private String sexoVoluntario;

    @NotNull
    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;

    @Column(name = "fecha_baja")
    private LocalDate fechaBaja;

    @NotNull
    @Column(name = "tipo_voluntario", nullable = false)
    private String tipoVoluntario;

    @Column(name = "tipo_turno")
    private String tipoTurno;

    @Column(name = "responsable_dia")
    private Boolean responsableDia;

    @Column(name = "origen_voluntario")
    private String origenVoluntario;

    @NotNull
    @Column(name = "manipulador_alimentos", nullable = false)
    private Boolean manipuladorAlimentos;

    @NotNull
    @Column(name = "codigo_postal", nullable = false)
    private String codigoPostal;

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

    public String getNombreVoluntario() {
        return this.nombreVoluntario;
    }

    public Voluntario nombreVoluntario(String nombreVoluntario) {
        this.setNombreVoluntario(nombreVoluntario);
        return this;
    }

    public void setNombreVoluntario(String nombreVoluntario) {
        this.nombreVoluntario = nombreVoluntario;
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

    public String getEmailVoluntario() {
        return this.emailVoluntario;
    }

    public Voluntario emailVoluntario(String emailVoluntario) {
        this.setEmailVoluntario(emailVoluntario);
        return this;
    }

    public void setEmailVoluntario(String emailVoluntario) {
        this.emailVoluntario = emailVoluntario;
    }

    public String getTelefonoContactoVoluntario() {
        return this.telefonoContactoVoluntario;
    }

    public Voluntario telefonoContactoVoluntario(String telefonoContactoVoluntario) {
        this.setTelefonoContactoVoluntario(telefonoContactoVoluntario);
        return this;
    }

    public void setTelefonoContactoVoluntario(String telefonoContactoVoluntario) {
        this.telefonoContactoVoluntario = telefonoContactoVoluntario;
    }

    public String getDniVoluntario() {
        return this.dniVoluntario;
    }

    public Voluntario dniVoluntario(String dniVoluntario) {
        this.setDniVoluntario(dniVoluntario);
        return this;
    }

    public void setDniVoluntario(String dniVoluntario) {
        this.dniVoluntario = dniVoluntario;
    }

    public LocalDate getFechaNacimientoVoluntario() {
        return this.fechaNacimientoVoluntario;
    }

    public Voluntario fechaNacimientoVoluntario(LocalDate fechaNacimientoVoluntario) {
        this.setFechaNacimientoVoluntario(fechaNacimientoVoluntario);
        return this;
    }

    public void setFechaNacimientoVoluntario(LocalDate fechaNacimientoVoluntario) {
        this.fechaNacimientoVoluntario = fechaNacimientoVoluntario;
    }

    public String getSexoVoluntario() {
        return this.sexoVoluntario;
    }

    public Voluntario sexoVoluntario(String sexoVoluntario) {
        this.setSexoVoluntario(sexoVoluntario);
        return this;
    }

    public void setSexoVoluntario(String sexoVoluntario) {
        this.sexoVoluntario = sexoVoluntario;
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

    public String getTipoVoluntario() {
        return this.tipoVoluntario;
    }

    public Voluntario tipoVoluntario(String tipoVoluntario) {
        this.setTipoVoluntario(tipoVoluntario);
        return this;
    }

    public void setTipoVoluntario(String tipoVoluntario) {
        this.tipoVoluntario = tipoVoluntario;
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

    public String getOrigenVoluntario() {
        return this.origenVoluntario;
    }

    public Voluntario origenVoluntario(String origenVoluntario) {
        this.setOrigenVoluntario(origenVoluntario);
        return this;
    }

    public void setOrigenVoluntario(String origenVoluntario) {
        this.origenVoluntario = origenVoluntario;
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
            ", nombreVoluntario='" + getNombreVoluntario() + "'" +
            ", primerApellido='" + getPrimerApellido() + "'" +
            ", segundoApellido='" + getSegundoApellido() + "'" +
            ", emailVoluntario='" + getEmailVoluntario() + "'" +
            ", telefonoContactoVoluntario='" + getTelefonoContactoVoluntario() + "'" +
            ", dniVoluntario='" + getDniVoluntario() + "'" +
            ", fechaNacimientoVoluntario='" + getFechaNacimientoVoluntario() + "'" +
            ", sexoVoluntario='" + getSexoVoluntario() + "'" +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", fechaBaja='" + getFechaBaja() + "'" +
            ", tipoVoluntario='" + getTipoVoluntario() + "'" +
            ", tipoTurno='" + getTipoTurno() + "'" +
            ", responsableDia='" + getResponsableDia() + "'" +
            ", origenVoluntario='" + getOrigenVoluntario() + "'" +
            ", manipuladorAlimentos='" + getManipuladorAlimentos() + "'" +
            ", codigoPostal='" + getCodigoPostal() + "'" +
            "}";
    }
}
