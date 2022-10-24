import dayjs from 'dayjs';
import { INucleo } from 'app/shared/model/nucleo.model';

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
  nucleo?: INucleo | null;
}

export const defaultValue: Readonly<ISocio> = {};
