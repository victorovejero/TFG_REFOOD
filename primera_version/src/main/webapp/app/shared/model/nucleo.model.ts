import { IDonante } from 'app/shared/model/donante.model';
import { IBeneficiario } from 'app/shared/model/beneficiario.model';
import { IVoluntario } from 'app/shared/model/voluntario.model';
import { ISocio } from 'app/shared/model/socio.model';
import { IRegistro } from 'app/shared/model/registro.model';

export interface INucleo {
  id?: number;
  nombreNucleo?: string;
  direccionNucleo?: string;
  provinciaNucleo?: string;
  responsableNucleo?: string;
  telefonoNucleo?: string;
  emailNucleo?: string;
  numeroRutas?: number;
  donantes?: IDonante[] | null;
  beneficiarios?: IBeneficiario[] | null;
  voluntarios?: IVoluntario[] | null;
  socios?: ISocio[] | null;
  registros?: IRegistro[] | null;
}

export const defaultValue: Readonly<INucleo> = {};
