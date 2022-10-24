import { IAlimentoDeSalida } from 'app/shared/model/alimento-de-salida.model';
import { IIntolerancia } from 'app/shared/model/intolerancia.model';

export interface ITipoDeAlimento {
  id?: number;
  nombreAlimento?: string;
  alimentoDeSalidas?: IAlimentoDeSalida[] | null;
  intolerancias?: IIntolerancia[] | null;
}

export const defaultValue: Readonly<ITipoDeAlimento> = {};
