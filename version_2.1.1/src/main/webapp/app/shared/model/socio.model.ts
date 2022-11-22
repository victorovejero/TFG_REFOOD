import dayjs from 'dayjs';

export interface ISocio {
  id?: number;
  nombre?: string;
  primerApellido?: string;
  segundoApellido?: string | null;
  email?: string;
  telefonoContacto?: string;
  dni?: string;
  fechaNacimiento?: string;
  sexo?: string;
  fechaAlta?: string;
  fechaBaja?: string | null;
  contribucionMensual?: number;
  periodoPago?: string;
  activo?: boolean;
  nucleoAsociado?: string | null;
  comunicacion?: boolean;
  direccion?: string;
  codigoPostal?: string;
  provincia?: string | null;
  pais?: string | null;
}

export const defaultValue: Readonly<ISocio> = {
  activo: false,
  comunicacion: false,
};
