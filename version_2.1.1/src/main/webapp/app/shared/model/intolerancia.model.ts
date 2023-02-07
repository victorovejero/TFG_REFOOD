import { IBeneficiario } from 'app/shared/model/beneficiario.model';
import { IPersonaBeneficiaria } from 'app/shared/model/persona-beneficiaria.model';
import { ITipoDeAlimento } from 'app/shared/model/tipo-de-alimento.model';

export interface IIntolerancia {
  id?: number;
  nombre?: string;
  descripcion?: string | null;
  beneficiarios?: IBeneficiario[] | null;
  personaBeneficiarias?: IPersonaBeneficiaria[] | null;
  tipoDeAlimentos?: ITipoDeAlimento[] | null;
}

export const defaultValue: Readonly<IIntolerancia> = {};
