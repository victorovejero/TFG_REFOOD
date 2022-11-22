import { IBeneficiario } from 'app/shared/model/beneficiario.model';
import { ITipoDeAlimento } from 'app/shared/model/tipo-de-alimento.model';

export interface IIntolerancia {
  id?: number;
  nombre?: string;
  descripcion?: string | null;
  beneficiarios?: IBeneficiario[] | null;
  tipoDeAlimentos?: ITipoDeAlimento[] | null;
}

export const defaultValue: Readonly<IIntolerancia> = {};
