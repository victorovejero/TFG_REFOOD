package com.refood.trazabilidad.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Socio.
 */
@Entity
@Table(name = "socio")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Socio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre_socio", nullable = false)
    private String nombreSocio;

    @NotNull
    @Column(name = "primer_apellido_socio", nullable = false)
    private String primerApellidoSocio;

    @Column(name = "segundo_apellido_socio")
    private String segundoApellidoSocio;

    @NotNull
    @Column(name = "email_socio", nullable = false)
    private String emailSocio;

    @NotNull
    @Column(name = "telefono_contacto_socio", nullable = false)
    private String telefonoContactoSocio;

    @NotNull
    @Column(name = "dni_socio", nullable = false)
    private String dniSocio;

    @NotNull
    @Column(name = "fecha_nacimiento_socio", nullable = false)
    private LocalDate fechaNacimientoSocio;

    @NotNull
    @Column(name = "sexo_socio", nullable = false)
    private String sexoSocio;

    @NotNull
    @Column(name = "fecha_alta_socio", nullable = false)
    private LocalDate fechaAltaSocio;

    @Column(name = "fecha_baja_socio")
    private LocalDate fechaBajaSocio;

    @NotNull
    @Column(name = "contribucion_mensual", nullable = false)
    private Double contribucionMensual;

    @NotNull
    @Column(name = "periodo_pago", nullable = false)
    private String periodoPago;

    @ManyToOne
    @JsonIgnoreProperties(value = { "donantes", "beneficiarios", "voluntarios", "socios", "registros" }, allowSetters = true)
    private Nucleo nucleo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Socio id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreSocio() {
        return this.nombreSocio;
    }

    public Socio nombreSocio(String nombreSocio) {
        this.setNombreSocio(nombreSocio);
        return this;
    }

    public void setNombreSocio(String nombreSocio) {
        this.nombreSocio = nombreSocio;
    }

    public String getPrimerApellidoSocio() {
        return this.primerApellidoSocio;
    }

    public Socio primerApellidoSocio(String primerApellidoSocio) {
        this.setPrimerApellidoSocio(primerApellidoSocio);
        return this;
    }

    public void setPrimerApellidoSocio(String primerApellidoSocio) {
        this.primerApellidoSocio = primerApellidoSocio;
    }

    public String getSegundoApellidoSocio() {
        return this.segundoApellidoSocio;
    }

    public Socio segundoApellidoSocio(String segundoApellidoSocio) {
        this.setSegundoApellidoSocio(segundoApellidoSocio);
        return this;
    }

    public void setSegundoApellidoSocio(String segundoApellidoSocio) {
        this.segundoApellidoSocio = segundoApellidoSocio;
    }

    public String getEmailSocio() {
        return this.emailSocio;
    }

    public Socio emailSocio(String emailSocio) {
        this.setEmailSocio(emailSocio);
        return this;
    }

    public void setEmailSocio(String emailSocio) {
        this.emailSocio = emailSocio;
    }

    public String getTelefonoContactoSocio() {
        return this.telefonoContactoSocio;
    }

    public Socio telefonoContactoSocio(String telefonoContactoSocio) {
        this.setTelefonoContactoSocio(telefonoContactoSocio);
        return this;
    }

    public void setTelefonoContactoSocio(String telefonoContactoSocio) {
        this.telefonoContactoSocio = telefonoContactoSocio;
    }

    public String getDniSocio() {
        return this.dniSocio;
    }

    public Socio dniSocio(String dniSocio) {
        this.setDniSocio(dniSocio);
        return this;
    }

    public void setDniSocio(String dniSocio) {
        this.dniSocio = dniSocio;
    }

    public LocalDate getFechaNacimientoSocio() {
        return this.fechaNacimientoSocio;
    }

    public Socio fechaNacimientoSocio(LocalDate fechaNacimientoSocio) {
        this.setFechaNacimientoSocio(fechaNacimientoSocio);
        return this;
    }

    public void setFechaNacimientoSocio(LocalDate fechaNacimientoSocio) {
        this.fechaNacimientoSocio = fechaNacimientoSocio;
    }

    public String getSexoSocio() {
        return this.sexoSocio;
    }

    public Socio sexoSocio(String sexoSocio) {
        this.setSexoSocio(sexoSocio);
        return this;
    }

    public void setSexoSocio(String sexoSocio) {
        this.sexoSocio = sexoSocio;
    }

    public LocalDate getFechaAltaSocio() {
        return this.fechaAltaSocio;
    }

    public Socio fechaAltaSocio(LocalDate fechaAltaSocio) {
        this.setFechaAltaSocio(fechaAltaSocio);
        return this;
    }

    public void setFechaAltaSocio(LocalDate fechaAltaSocio) {
        this.fechaAltaSocio = fechaAltaSocio;
    }

    public LocalDate getFechaBajaSocio() {
        return this.fechaBajaSocio;
    }

    public Socio fechaBajaSocio(LocalDate fechaBajaSocio) {
        this.setFechaBajaSocio(fechaBajaSocio);
        return this;
    }

    public void setFechaBajaSocio(LocalDate fechaBajaSocio) {
        this.fechaBajaSocio = fechaBajaSocio;
    }

    public Double getContribucionMensual() {
        return this.contribucionMensual;
    }

    public Socio contribucionMensual(Double contribucionMensual) {
        this.setContribucionMensual(contribucionMensual);
        return this;
    }

    public void setContribucionMensual(Double contribucionMensual) {
        this.contribucionMensual = contribucionMensual;
    }

    public String getPeriodoPago() {
        return this.periodoPago;
    }

    public Socio periodoPago(String periodoPago) {
        this.setPeriodoPago(periodoPago);
        return this;
    }

    public void setPeriodoPago(String periodoPago) {
        this.periodoPago = periodoPago;
    }

    public Nucleo getNucleo() {
        return this.nucleo;
    }

    public void setNucleo(Nucleo nucleo) {
        this.nucleo = nucleo;
    }

    public Socio nucleo(Nucleo nucleo) {
        this.setNucleo(nucleo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Socio)) {
            return false;
        }
        return id != null && id.equals(((Socio) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Socio{" +
            "id=" + getId() +
            ", nombreSocio='" + getNombreSocio() + "'" +
            ", primerApellidoSocio='" + getPrimerApellidoSocio() + "'" +
            ", segundoApellidoSocio='" + getSegundoApellidoSocio() + "'" +
            ", emailSocio='" + getEmailSocio() + "'" +
            ", telefonoContactoSocio='" + getTelefonoContactoSocio() + "'" +
            ", dniSocio='" + getDniSocio() + "'" +
            ", fechaNacimientoSocio='" + getFechaNacimientoSocio() + "'" +
            ", sexoSocio='" + getSexoSocio() + "'" +
            ", fechaAltaSocio='" + getFechaAltaSocio() + "'" +
            ", fechaBajaSocio='" + getFechaBajaSocio() + "'" +
            ", contribucionMensual=" + getContribucionMensual() +
            ", periodoPago='" + getPeriodoPago() + "'" +
            "}";
    }
}
