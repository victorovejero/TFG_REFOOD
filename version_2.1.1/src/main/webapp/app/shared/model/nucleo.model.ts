import { IDonante } from 'app/shared/model/donante.model';
import { IBeneficiario } from 'app/shared/model/beneficiario.model';
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
  beneficiarios?: IBeneficiario[] | null;
  voluntarios?: IVoluntario[] | null;
}

export const defaultValue: Readonly<INucleo> = {
  activo: false,
};
