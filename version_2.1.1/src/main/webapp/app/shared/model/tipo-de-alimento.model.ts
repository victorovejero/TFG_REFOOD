import { IAlimentoDeEntrada } from 'app/shared/model/alimento-de-entrada.model';
import { IIntolerancia } from 'app/shared/model/intolerancia.model';

export interface ITipoDeAlimento {
  id?: number;
  nombreAlimento?: string;
  descripcion?: string | null;
  alimentoDeEntradas?: IAlimentoDeEntrada[] | null;
  intolerancias?: IIntolerancia[] | null;
}

export const defaultValue: Readonly<ITipoDeAlimento> = {};
