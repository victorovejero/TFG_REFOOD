import dayjs from 'dayjs';
import { ITupper } from 'app/shared/model/tupper.model';
import { IBeneficiario } from 'app/shared/model/beneficiario.model';
import { IAlimentoDeEntrada } from 'app/shared/model/alimento-de-entrada.model';
import { ITipoDeAlimento } from 'app/shared/model/tipo-de-alimento.model';

export interface IAlimentoDeSalida {
  id?: number;
  peso?: number;
  fechaSalida?: string;
  tupper?: ITupper | null;
  beneficiario?: IBeneficiario | null;
  alimentoDeEntrada?: IAlimentoDeEntrada | null;
  tipoDeAlimento?: ITipoDeAlimento | null;
}

export const defaultValue: Readonly<IAlimentoDeSalida> = {};
