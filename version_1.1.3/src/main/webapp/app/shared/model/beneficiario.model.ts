import { IAlimentoDeSalida } from 'app/shared/model/alimento-de-salida.model';
import { INucleo } from 'app/shared/model/nucleo.model';
import { IIntolerancia } from 'app/shared/model/intolerancia.model';

export interface IBeneficiario {
  id?: number;
  nombre?: string;
  numeroPersonas?: number;
  numeroNinios?: number;
  idDual?: string | null;
  alimentoDeSalidas?: IAlimentoDeSalida[] | null;
  nucleo?: INucleo | null;
  intolerancias?: IIntolerancia[] | null;
}

export const defaultValue: Readonly<IBeneficiario> = {};
