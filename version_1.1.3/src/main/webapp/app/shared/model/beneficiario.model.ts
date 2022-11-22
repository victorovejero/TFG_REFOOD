import { IAlimentoDeSalida } from 'app/shared/model/alimento-de-salida.model';
import { IIntolerancia } from 'app/shared/model/intolerancia.model';
import { INucleo } from 'app/shared/model/nucleo.model';

export interface IBeneficiario {
  id?: number;
  idBeneficiario?: string;
  nombre?: string;
  numeroPersonas?: number;
  numeroNinios?: number;
  idDual?: string | null;
  activo?: boolean;
  alimentoDeSalidas?: IAlimentoDeSalida[] | null;
  intolerancias?: IIntolerancia[] | null;
  nucleo?: INucleo | null;
}

export const defaultValue: Readonly<IBeneficiario> = {
  activo: false,
};
