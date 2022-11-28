package com.refood.trazabilidad.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
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
    @Column(name = "id_voluntario", nullable = false)
    private String idVoluntario;

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
    @Column(name = "categoria_perfil", nullable = false)
    private String categoriaPerfil;

    @Column(name = "descripcion_categoria")
    private String descripcionCategoria;

    @NotNull
    @Column(name = "dia_refood", nullable = false)
    private String diaRefood;

    @Column(name = "origen")
    private String origen;

    @NotNull
    @Column(name = "manipulador_alimentos", nullable = false)
    private Boolean manipuladorAlimentos;

    @NotNull
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @NotNull
    @Column(name = "codigo_postal", nullable = false)
    private String codigoPostal;

    @NotNull
    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @ManyToOne
    @JsonIgnoreProperties(value = { "donantes", "beneficiarios", "voluntarios" }, allowSetters = true)
    private Nucleo nucleo;

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

    public String getIdVoluntario() {
        return this.idVoluntario;
    }

    public Voluntario idVoluntario(String idVoluntario) {
        this.setIdVoluntario(idVoluntario);
        return this;
    }

    public void setIdVoluntario(String idVoluntario) {
        this.idVoluntario = idVoluntario;
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

    public String getCategoriaPerfil() {
        return this.categoriaPerfil;
    }

    public Voluntario categoriaPerfil(String categoriaPerfil) {
        this.setCategoriaPerfil(categoriaPerfil);
        return this;
    }

    public void setCategoriaPerfil(String categoriaPerfil) {
        this.categoriaPerfil = categoriaPerfil;
    }

    public String getDescripcionCategoria() {
        return this.descripcionCategoria;
    }

    public Voluntario descripcionCategoria(String descripcionCategoria) {
        this.setDescripcionCategoria(descripcionCategoria);
        return this;
    }

    public void setDescripcionCategoria(String descripcionCategoria) {
        this.descripcionCategoria = descripcionCategoria;
    }

    public String getDiaRefood() {
        return this.diaRefood;
    }

    public Voluntario diaRefood(String diaRefood) {
        this.setDiaRefood(diaRefood);
        return this;
    }

    public void setDiaRefood(String diaRefood) {
        this.diaRefood = diaRefood;
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

    public String getDireccion() {
        return this.direccion;
    }

    public Voluntario direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
            "}";
    }
}
