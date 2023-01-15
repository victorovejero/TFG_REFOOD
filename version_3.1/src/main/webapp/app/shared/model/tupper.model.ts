import { IAlSal } from 'app/shared/model/al-sal.model';
import { IAlEnt } from 'app/shared/model/al-ent.model';

export interface ITupper {
  id?: number;
  peso?: number;
  productor?: string;
  modelo?: string;
  precio?: number;
  descripcion?: string | null;
  alSals?: IAlSal[] | null;
  alEnts?: IAlEnt[] | null;
}

export const defaultValue: Readonly<ITupper> = {};
