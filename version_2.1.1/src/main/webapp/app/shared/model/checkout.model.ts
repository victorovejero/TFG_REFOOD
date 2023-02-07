import dayjs from 'dayjs';
import { IAlimentoDeSalida } from 'app/shared/model/alimento-de-salida.model';
import { IBeneficiario } from 'app/shared/model/beneficiario.model';

export interface ICheckout {
  id?: number;
  fechaSalida?: string;
  peso?: number;
  alimentoDeSalidas?: IAlimentoDeSalida[] | null;
  beneficiario?: IBeneficiario | null;
}

export const defaultValue: Readonly<ICheckout> = {};
