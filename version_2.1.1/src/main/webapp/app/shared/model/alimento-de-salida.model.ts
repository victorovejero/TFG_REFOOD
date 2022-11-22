import dayjs from 'dayjs';
import { ITupper } from 'app/shared/model/tupper.model';
import { IBeneficiario } from 'app/shared/model/beneficiario.model';
import { IAlimentoDeEntrada } from 'app/shared/model/alimento-de-entrada.model';
import { ICheckout } from 'app/shared/model/checkout.model';

export interface IAlimentoDeSalida {
  id?: number;
  fechaSalida?: string;
  tupper?: ITupper | null;
  beneficiario?: IBeneficiario | null;
  alimentoDeEntrada?: IAlimentoDeEntrada | null;
  checkouts?: ICheckout[] | null;
}

export const defaultValue: Readonly<IAlimentoDeSalida> = {};
