import dayjs from 'dayjs';
import { INucleo } from 'app/shared/model/nucleo.model';

export interface IVoluntario {
  id?: number;
  idVoluntario?: string;
  nombre?: string;
  primerApellido?: string;
  segundoApellido?: string | null;
  email?: string;
  telefonoContacto?: string;
  dni?: string | null;
  fechaNacimiento?: string;
  sexo?: string;
  fechaAlta?: string;
  fechaBaja?: string | null;
  categoriaPerfil?: string;
  descripcionCategoria?: string | null;
  diaRefood?: string;
  origen?: string | null;
  manipuladorAlimentos?: boolean;
  direccion?: string;
  codigoPostal?: string;
  activo?: boolean;
  nucleo?: INucleo | null;
}

export const defaultValue: Readonly<IVoluntario> = {
  manipuladorAlimentos: false,
  activo: false,
};
