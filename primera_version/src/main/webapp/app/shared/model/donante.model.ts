import dayjs from 'dayjs';
import { IAlimentoDeEntrada } from 'app/shared/model/alimento-de-entrada.model';
import { INucleo } from 'app/shared/model/nucleo.model';

export interface IDonante {
  id?: number;
  nombreDonante?: string;
  tipoDonante?: string;
  ruta?: number;
  direccionDonante?: string;
  telefonoDonante?: string;
  emailDonante?: string;
  responsableDonante?: string;
  fechaAlta?: string;
  fechaBaja?: string | null;
  comentarios?: string | null;
  alimentoDeEntradas?: IAlimentoDeEntrada[] | null;
  nucleo?: INucleo | null;
}

export const defaultValue: Readonly<IDonante> = {};
