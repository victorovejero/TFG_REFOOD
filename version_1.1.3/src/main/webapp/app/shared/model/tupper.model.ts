import { IAlimentoDeSalida } from 'app/shared/model/alimento-de-salida.model';
import { IAlimentoDeEntrada } from 'app/shared/model/alimento-de-entrada.model';

export interface ITupper {
  id?: number;
  peso?: number;
  productor?: string;
  modelo?: string;
  precio?: number;
  descripcion?: string | null;
  alimentoDeSalidas?: IAlimentoDeSalida[] | null;
  alimentoDeEntradas?: IAlimentoDeEntrada[] | null;
}

export const defaultValue: Readonly<ITupper> = {};
