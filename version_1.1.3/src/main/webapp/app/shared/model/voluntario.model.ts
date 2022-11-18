import dayjs from 'dayjs';
import { INucleo } from 'app/shared/model/nucleo.model';
import { IRegistro } from 'app/shared/model/registro.model';

export interface IVoluntario {
  id?: number;
  nombre?: string;
  primerApellido?: string;
  segundoApellido?: string | null;
  email?: string;
  telefonoContacto?: string;
  dni?: string | null;
  fechaNacimiento?: string;
  sexo?: string;
  fechaAlta?: string;
  fechaBaja?: string | null;
  tipo?: string;
  tipoTurno?: string | null;
  responsableDia?: boolean | null;
  origen?: string | null;
  manipuladorAlimentos?: boolean;
  codigoPostal?: string;
  activo?: boolean;
  nucleo?: INucleo | null;
  registros?: IRegistro[] | null;
}

export const defaultValue: Readonly<IVoluntario> = {
  responsableDia: false,
  manipuladorAlimentos: false,
  activo: false,
};
