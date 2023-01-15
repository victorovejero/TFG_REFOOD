import { IAlEnt } from 'app/shared/model/al-ent.model';
import { IIntol } from 'app/shared/model/intol.model';

export interface ITipoAl {
  id?: number;
  nombreAlimento?: string;
  frutaYVerdura?: boolean;
  descripcion?: string | null;
  alEnts?: IAlEnt[] | null;
  intols?: IIntol[] | null;
}

export const defaultValue: Readonly<ITipoAl> = {
  frutaYVerdura: false,
};
