import { IAlimentoDeSalida } from 'app/shared/model/alimento-de-salida.model';

export interface ITupper {
  id?: number;
  peso?: number;
  productor?: string;
  modelo?: string;
  precio?: number;
  descripcion?: string | null;
  alimentoDeSalidas?: IAlimentoDeSalida[] | null;
}

export const defaultValue: Readonly<ITupper> = {};
