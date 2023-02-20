import dayjs from 'dayjs';
import { ITupper } from 'app/shared/model/tupper.model';
import { IBenef } from 'app/shared/model/benef.model';
import { IAlEnt } from 'app/shared/model/al-ent.model';
import { ICheckout } from 'app/shared/model/checkout.model';

export interface IAlSal {
  id?: number;
  fechaSalida?: string;
  tupper?: ITupper | null;
  benef?: IBenef | null;
  alEnt?: IAlEnt | null;
  checkouts?: ICheckout[] | null;
}

export const defaultValue: Readonly<IAlSal> = {};
