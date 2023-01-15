import dayjs from 'dayjs';
import { IAlEnt } from 'app/shared/model/al-ent.model';
import { INucleo } from 'app/shared/model/nucleo.model';

export interface IDonante {
  id?: number;
  idDonante?: string;
  nombre?: string;
  categoria?: string;
  direccion?: string;
  codigoPostal?: string;
  provincia?: string;
  telefono?: string;
  email?: string;
  responsable?: string;
  fechaAlta?: string;
  fechaBaja?: string | null;
  comentarios?: string | null;
  activo?: boolean;
  alEnts?: IAlEnt[] | null;
  nucleo?: INucleo | null;
}

export const defaultValue: Readonly<IDonante> = {
  activo: false,
};
