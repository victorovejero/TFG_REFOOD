import dayjs from 'dayjs';
import { IAlimentoDeSalida } from 'app/shared/model/alimento-de-salida.model';
import { ITupper } from 'app/shared/model/tupper.model';
import { IDonante } from 'app/shared/model/donante.model';
import { ITipoDeAlimento } from 'app/shared/model/tipo-de-alimento.model';

export interface IAlimentoDeEntrada {
  id?: number;
  peso?: number;
  fechaYHoraEntrada?: string;
  fechaYHoraRecogida?: string | null;
  fechaYHoraPreparacion?: string | null;
  alimentoDeSalidas?: IAlimentoDeSalida[] | null;
  tupper?: ITupper | null;
  donante?: IDonante | null;
  tipoDeAlimento?: ITipoDeAlimento | null;
}

export const defaultValue: Readonly<IAlimentoDeEntrada> = {};
