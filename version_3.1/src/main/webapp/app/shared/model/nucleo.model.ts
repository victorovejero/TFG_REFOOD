import { IDonante } from 'app/shared/model/donante.model';
import { IBenef } from 'app/shared/model/benef.model';
import { IVoluntario } from 'app/shared/model/voluntario.model';

export interface INucleo {
  id?: number;
  idNucleo?: string;
  nombre?: string;
  direccion?: string;
  codigoPostal?: string;
  provincia?: string;
  responsable?: string;
  telefono?: string;
  email?: string;
  activo?: boolean;
  donantes?: IDonante[] | null;
  benefs?: IBenef[] | null;
  voluntarios?: IVoluntario[] | null;
}

export const defaultValue: Readonly<INucleo> = {
  activo: false,
};
