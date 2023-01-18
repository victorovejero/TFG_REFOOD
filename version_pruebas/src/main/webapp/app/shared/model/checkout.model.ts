import dayjs from 'dayjs';
import { IAlSal } from 'app/shared/model/al-sal.model';
import { IBenef } from 'app/shared/model/benef.model';

export interface ICheckout {
  id?: number;
  fechaSalida?: string;
  peso?: number;
  alSals?: IAlSal[] | null;
  benef?: IBenef | null;
}

export const defaultValue: Readonly<ICheckout> = {};
