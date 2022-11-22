import { IIntolerancia } from 'app/shared/model/intolerancia.model';
import { IBeneficiario } from 'app/shared/model/beneficiario.model';

export interface IPersonaBeneficiaria {
  id?: number;
  nombre?: string;
  primerApellido?: string;
  segundoApellido?: string | null;
  fechaNacimiento?: string;
  sexo?: string;
  parentesco?: string;
  situacionProfesional?: string;
  intolerancias?: IIntolerancia[] | null;
  beneficiario?: IBeneficiario | null;
}

export const defaultValue: Readonly<IPersonaBeneficiaria> = {};
