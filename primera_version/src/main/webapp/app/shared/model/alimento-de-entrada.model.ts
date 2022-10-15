import dayjs from 'dayjs';
import { ITipoDeAlimento } from 'app/shared/model/tipo-de-alimento.model';
import { IAlimentoDeSalida } from 'app/shared/model/alimento-de-salida.model';
import { IDonante } from 'app/shared/model/donante.model';

export interface IAlimentoDeEntrada {
  id?: number;
  peso?: number;
  fechaEntrada?: string;
  fechaYHoraLog?: string;
  fechaYHoraRecogida?: string | null;
  fechaYHoraPreparacion?: string | null;
  alimentoDeEntrada?: ITipoDeAlimento | null;
  alimentoDeSalidas?: IAlimentoDeSalida[] | null;
  donante?: IDonante | null;
  tipoDeAlimento?: ITipoDeAlimento | null;
}

export const defaultValue: Readonly<IAlimentoDeEntrada> = {};
