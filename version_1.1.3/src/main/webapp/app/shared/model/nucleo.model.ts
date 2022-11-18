import { IDonante } from 'app/shared/model/donante.model';
import { IBeneficiario } from 'app/shared/model/beneficiario.model';
import { IVoluntario } from 'app/shared/model/voluntario.model';
import { ISocio } from 'app/shared/model/socio.model';
import { IRegistro } from 'app/shared/model/registro.model';

export interface INucleo {
  id?: number;
  nombre?: string;
  direccion?: string;
  provincia?: string;
  responsable?: string;
  telefono?: string;
  email?: string;
  numeroRutas?: number;
  activo?: boolean;
  donantes?: IDonante[] | null;
  beneficiarios?: IBeneficiario[] | null;
  voluntarios?: IVoluntario[] | null;
  socios?: ISocio[] | null;
  registros?: IRegistro[] | null;
}

export const defaultValue: Readonly<INucleo> = {
  activo: false,
};
