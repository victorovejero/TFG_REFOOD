import dayjs from 'dayjs';
import { IAlSal } from 'app/shared/model/al-sal.model';
import { ITupper } from 'app/shared/model/tupper.model';
import { IDonante } from 'app/shared/model/donante.model';
import { ITipoAl } from 'app/shared/model/tipo-al.model';

export interface IAlEnt {
  id?: number;
  peso?: number;
  frutaYVerdura?: boolean;
  fechaYHoraEntrada?: string;
  fechaYHoraRecogida?: string | null;
  fechaYHoraPreparacion?: string | null;
  alSals?: IAlSal[] | null;
  tupper?: ITupper | null;
  donante?: IDonante | null;
  tipoAl?: ITipoAl | null;
}

export const defaultValue: Readonly<IAlEnt> = {
  frutaYVerdura: false,
};
