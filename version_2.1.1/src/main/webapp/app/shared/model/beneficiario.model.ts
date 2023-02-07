import dayjs from 'dayjs';
import { IAlimentoDeSalida } from 'app/shared/model/alimento-de-salida.model';
import { IPersonaBeneficiaria } from 'app/shared/model/persona-beneficiaria.model';
import { ICheckout } from 'app/shared/model/checkout.model';
import { IIntolerancia } from 'app/shared/model/intolerancia.model';
import { INucleo } from 'app/shared/model/nucleo.model';

export interface IBeneficiario {
  id?: number;
  idBeneficiario?: string;
  nombreRepresentante?: string;
  primerApellidoRepresentante?: string;
  segundoApellidoRepresentante?: string | null;
  numeroPersonas?: number;
  email?: string;
  telefono?: string;
  telefonoSecundario?: string;
  direccion?: string;
  codigoPostal?: string;
  fechaAlta?: string;
  fechaBaja?: string | null;
  numeroNinios?: number;
  idDual?: string | null;
  activo?: boolean;
  alimentoDeSalidas?: IAlimentoDeSalida[] | null;
  personaBeneficiarias?: IPersonaBeneficiaria[] | null;
  checkouts?: ICheckout[] | null;
  intolerancias?: IIntolerancia[] | null;
  nucleo?: INucleo | null;
}

export const defaultValue: Readonly<IBeneficiario> = {
  activo: false,
};
