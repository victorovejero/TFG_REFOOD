package com.refood.trazabilidad.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.refood.trazabilidad.domain.Voluntario} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VoluntarioDTO implements Serializable {

    private Long id;

    @NotNull
    private String idVoluntario;

    @NotNull
    private String nombre;

    @NotNull
    private String primerApellido;

    private String segundoApellido;

    @NotNull
    private String email;

    @NotNull
    private String telefonoContacto;

    private String dni;

    @NotNull
    private LocalDate fechaNacimiento;

    @NotNull
    private String sexo;

    @NotNull
    private LocalDate fechaAlta;

    private LocalDate fechaBaja;

    @NotNull
    private String categoriaPerfil;

    private String descripcionCategoria;

    @NotNull
    private String diaRefood;

    private String origen;

    @NotNull
    private Boolean manipuladorAlimentos;

    @NotNull
    private String direccion;

    @NotNull
    private String codigoPostal;

    @NotNull
    private Boolean activo;

    private NucleoDTO nucleo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdVoluntario() {
        return idVoluntario;
    }

    public void setIdVoluntario(String idVoluntario) {
        this.idVoluntario = idVoluntario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public LocalDate getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getCategoriaPerfil() {
        return categoriaPerfil;
    }

    public void setCategoriaPerfil(String categoriaPerfil) {
        this.categoriaPerfil = categoriaPerfil;
    }

    public String getDescripcionCategoria() {
        return descripcionCategoria;
    }

    public void setDescripcionCategoria(String descripcionCategoria) {
        this.descripcionCategoria = descripcionCategoria;
    }

    public String getDiaRefood() {
        return diaRefood;
    }

    public void setDiaRefood(String diaRefood) {
        this.diaRefood = diaRefood;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Boolean getManipuladorAlimentos() {
        return manipuladorAlimentos;
    }

    public void setManipuladorAlimentos(Boolean manipuladorAlimentos) {
        this.manipuladorAlimentos = manipuladorAlimentos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public NucleoDTO getNucleo() {
        return nucleo;
    }

    public void setNucleo(NucleoDTO nucleo) {
        this.nucleo = nucleo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VoluntarioDTO)) {
            return false;
        }

        VoluntarioDTO voluntarioDTO = (VoluntarioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, voluntarioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoluntarioDTO{" +
            "id=" + getId() +
            ", idVoluntario='" + getIdVoluntario() + "'" +
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
            ", categoriaPerfil='" + getCategoriaPerfil() + "'" +
            ", descripcionCategoria='" + getDescripcionCategoria() + "'" +
            ", diaRefood='" + getDiaRefood() + "'" +
            ", origen='" + getOrigen() + "'" +
            ", manipuladorAlimentos='" + getManipuladorAlimentos() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", codigoPostal='" + getCodigoPostal() + "'" +
            ", activo='" + getActivo() + "'" +
            ", nucleo=" + getNucleo() +
            "}";
    }
}
