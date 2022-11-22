import dayjs from 'dayjs';
import { IVoluntario } from 'app/shared/model/voluntario.model';
import { INucleo } from 'app/shared/model/nucleo.model';

export interface IRegistro {
  id?: number;
  diaActividad?: string;
  ruta?: string;
  voluntarios?: IVoluntario[] | null;
  nucleo?: INucleo | null;
}

export const defaultValue: Readonly<IRegistro> = {};
