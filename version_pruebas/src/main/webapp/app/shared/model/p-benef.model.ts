import { IIntol } from 'app/shared/model/intol.model';
import { IBenef } from 'app/shared/model/benef.model';

export interface IPBenef {
  id?: number;
  nombre?: string;
  primerApellido?: string;
  segundoApellido?: string | null;
  fechaNacimiento?: string;
  sexo?: string;
  parentesco?: string;
  situacionProfesional?: string;
  intols?: IIntol[] | null;
  benef?: IBenef | null;
}

export const defaultValue: Readonly<IPBenef> = {};
