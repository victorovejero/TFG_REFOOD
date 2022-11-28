import { IAlimentoDeEntrada } from 'app/shared/model/alimento-de-entrada.model';

export interface IFrutaYVerdura {
  id?: number;
  nombreAlimento?: string;
  alimentoDeEntradas?: IAlimentoDeEntrada[] | null;
}

export const defaultValue: Readonly<IFrutaYVerdura> = {};
