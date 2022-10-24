import dayjs from 'dayjs';
import { IAlimentoDeEntrada } from 'app/shared/model/alimento-de-entrada.model';
import { INucleo } from 'app/shared/model/nucleo.model';

export interface IDonante {
  id?: number;
  nombre?: string;
  tipo?: string;
  ruta?: number;
  direccion?: string;
  telefono?: string;
  email?: string;
  responsable?: string;
  fechaAlta?: string;
  fechaBaja?: string | null;
  comentarios?: string | null;
  alimentoDeEntradas?: IAlimentoDeEntrada[] | null;
  nucleo?: INucleo | null;
}

export const defaultValue: Readonly<IDonante> = {};
