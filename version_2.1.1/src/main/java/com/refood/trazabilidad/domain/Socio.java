package com.refood.trazabilidad.domain;

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

    @NotNull
    @Column(name = "i_ban", nullable = false)
    private String iBAN;

    @NotNull
    @Column(name = "dni", nullable = false)
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
    @Column(name = "contribucion_mensual", nullable = false)
    private Double contribucionMensual;

    @NotNull
    @Column(name = "periodo_pago", nullable = false)
    private String periodoPago;

    @NotNull
    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Column(name = "nucleo_asociado")
    private String nucleoAsociado;

    @NotNull
    @Column(name = "comunicacion", nullable = false)
    private Boolean comunicacion;

    @NotNull
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @NotNull
    @Column(name = "codigo_postal", nullable = false)
    private String codigoPostal;

    @Column(name = "provincia")
    private String provincia;

    @Column(name = "pais")
    private String pais;

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

    public String getNombre() {
        return this.nombre;
    }

    public Socio nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return this.primerApellido;
    }

    public Socio primerApellido(String primerApellido) {
        this.setPrimerApellido(primerApellido);
        return this;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return this.segundoApellido;
    }

    public Socio segundoApellido(String segundoApellido) {
        this.setSegundoApellido(segundoApellido);
        return this;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getEmail() {
        return this.email;
    }

    public Socio email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefonoContacto() {
        return this.telefonoContacto;
    }

    public Socio telefonoContacto(String telefonoContacto) {
        this.setTelefonoContacto(telefonoContacto);
        return this;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getiBAN() {
        return this.iBAN;
    }

    public Socio iBAN(String iBAN) {
        this.setiBAN(iBAN);
        return this;
    }

    public void setiBAN(String iBAN) {
        this.iBAN = iBAN;
    }

    public String getDni() {
        return this.dni;
    }

    public Socio dni(String dni) {
        this.setDni(dni);
        return this;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public Socio fechaNacimiento(LocalDate fechaNacimiento) {
        this.setFechaNacimiento(fechaNacimiento);
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return this.sexo;
    }

    public Socio sexo(String sexo) {
        this.setSexo(sexo);
        return this;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public LocalDate getFechaAlta() {
        return this.fechaAlta;
    }

    public Socio fechaAlta(LocalDate fechaAlta) {
        this.setFechaAlta(fechaAlta);
        return this;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public LocalDate getFechaBaja() {
        return this.fechaBaja;
    }

    public Socio fechaBaja(LocalDate fechaBaja) {
        this.setFechaBaja(fechaBaja);
        return this;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
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

    public Boolean getActivo() {
        return this.activo;
    }

    public Socio activo(Boolean activo) {
        this.setActivo(activo);
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getNucleoAsociado() {
        return this.nucleoAsociado;
    }

    public Socio nucleoAsociado(String nucleoAsociado) {
        this.setNucleoAsociado(nucleoAsociado);
        return this;
    }

    public void setNucleoAsociado(String nucleoAsociado) {
        this.nucleoAsociado = nucleoAsociado;
    }

    public Boolean getComunicacion() {
        return this.comunicacion;
    }

    public Socio comunicacion(Boolean comunicacion) {
        this.setComunicacion(comunicacion);
        return this;
    }

    public void setComunicacion(Boolean comunicacion) {
        this.comunicacion = comunicacion;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Socio direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoPostal() {
        return this.codigoPostal;
    }

    public Socio codigoPostal(String codigoPostal) {
        this.setCodigoPostal(codigoPostal);
        return this;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getProvincia() {
        return this.provincia;
    }

    public Socio provincia(String provincia) {
        this.setProvincia(provincia);
        return this;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getPais() {
        return this.pais;
    }

    public Socio pais(String pais) {
        this.setPais(pais);
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
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
            ", nombre='" + getNombre() + "'" +
            ", primerApellido='" + getPrimerApellido() + "'" +
            ", segundoApellido='" + getSegundoApellido() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefonoContacto='" + getTelefonoContacto() + "'" +
            ", iBAN='" + getiBAN() + "'" +
            ", dni='" + getDni() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", fechaBaja='" + getFechaBaja() + "'" +
            ", contribucionMensual=" + getContribucionMensual() +
            ", periodoPago='" + getPeriodoPago() + "'" +
            ", activo='" + getActivo() + "'" +
            ", nucleoAsociado='" + getNucleoAsociado() + "'" +
            ", comunicacion='" + getComunicacion() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", codigoPostal='" + getCodigoPostal() + "'" +
            ", provincia='" + getProvincia() + "'" +
            ", pais='" + getPais() + "'" +
            "}";
    }
}
