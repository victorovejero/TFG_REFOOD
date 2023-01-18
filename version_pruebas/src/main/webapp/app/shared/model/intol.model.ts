import { IBenef } from 'app/shared/model/benef.model';
import { IPBenef } from 'app/shared/model/p-benef.model';
import { ITipoAl } from 'app/shared/model/tipo-al.model';

export interface IIntol {
  id?: number;
  nombre?: string;
  descripcion?: string | null;
  benefs?: IBenef[] | null;
  pBenefs?: IPBenef[] | null;
  tipoAls?: ITipoAl[] | null;
}

export const defaultValue: Readonly<IIntol> = {};
