import dayjs from 'dayjs';
import { INucleo } from 'app/shared/model/nucleo.model';

export interface ISocio {
  id?: number;
  nombreSocio?: string;
  primerApellidoSocio?: string;
  segundoApellidoSocio?: string | null;
  emailSocio?: string;
  telefonoContactoSocio?: string;
  dniSocio?: string;
  fechaNacimientoSocio?: string;
  sexoSocio?: string;
  fechaAltaSocio?: string;
  fechaBajaSocio?: string | null;
  contribucionMensual?: number;
  periodoPago?: string;
  nucleo?: INucleo | null;
}

export const defaultValue: Readonly<ISocio> = {};
