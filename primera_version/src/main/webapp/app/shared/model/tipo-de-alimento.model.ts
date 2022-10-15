import { IAlimentoDeEntrada } from 'app/shared/model/alimento-de-entrada.model';
import { IAlimentoDeSalida } from 'app/shared/model/alimento-de-salida.model';
import { IIntolerancia } from 'app/shared/model/intolerancia.model';

export interface ITipoDeAlimento {
  id?: number;
  nombreAlimento?: string;
  alimentoDeEntradas?: IAlimentoDeEntrada[] | null;
  alimentoDeSalidas?: IAlimentoDeSalida[] | null;
  intolerancias?: IIntolerancia[] | null;
}

export const defaultValue: Readonly<ITipoDeAlimento> = {};
