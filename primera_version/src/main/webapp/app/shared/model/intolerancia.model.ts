import { ITipoDeAlimento } from 'app/shared/model/tipo-de-alimento.model';
import { IBeneficiario } from 'app/shared/model/beneficiario.model';

export interface IIntolerancia {
  id?: number;
  nombreIntolerancia?: string | null;
  tipoDeAlimentos?: ITipoDeAlimento[] | null;
  beneficiarios?: IBeneficiario[] | null;
}

export const defaultValue: Readonly<IIntolerancia> = {};
