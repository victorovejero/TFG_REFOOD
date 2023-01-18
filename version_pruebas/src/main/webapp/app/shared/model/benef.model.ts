import dayjs from 'dayjs';
import { IAlSal } from 'app/shared/model/al-sal.model';
import { IPBenef } from 'app/shared/model/p-benef.model';
import { ICheckout } from 'app/shared/model/checkout.model';
import { IIntol } from 'app/shared/model/intol.model';
import { INucleo } from 'app/shared/model/nucleo.model';

export interface IBenef {
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
  alSals?: IAlSal[] | null;
  pBenefs?: IPBenef[] | null;
  checkouts?: ICheckout[] | null;
  intols?: IIntol[] | null;
  nucleo?: INucleo | null;
}

export const defaultValue: Readonly<IBenef> = {
  activo: false,
};
