import dayjs from 'dayjs';
import { INucleo } from 'app/shared/model/nucleo.model';
import { IRegistro } from 'app/shared/model/registro.model';

export interface IVoluntario {
  id?: number;
  nombreVoluntario?: string;
  primerApellido?: string;
  segundoApellido?: string | null;
  emailVoluntario?: string;
  telefonoContactoVoluntario?: string;
  dniVoluntario?: string | null;
  fechaNacimientoVoluntario?: string;
  sexoVoluntario?: string;
  fechaAlta?: string;
  fechaBaja?: string | null;
  tipoVoluntario?: string;
  tipoTurno?: string | null;
  responsableDia?: boolean | null;
  origenVoluntario?: string | null;
  manipuladorAlimentos?: boolean;
  codigoPostal?: string;
  nucleo?: INucleo | null;
  registros?: IRegistro[] | null;
}

export const defaultValue: Readonly<IVoluntario> = {
  responsableDia: false,
  manipuladorAlimentos: false,
};
