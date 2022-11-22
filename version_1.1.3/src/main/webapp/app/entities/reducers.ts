import nucleo from 'app/entities/nucleo/nucleo.reducer';
import donante from 'app/entities/donante/donante.reducer';
import beneficiario from 'app/entities/beneficiario/beneficiario.reducer';
import alimentoDeEntrada from 'app/entities/alimento-de-entrada/alimento-de-entrada.reducer';
import alimentoDeSalida from 'app/entities/alimento-de-salida/alimento-de-salida.reducer';
import tipoDeAlimento from 'app/entities/tipo-de-alimento/tipo-de-alimento.reducer';
import tupper from 'app/entities/tupper/tupper.reducer';
import intolerancia from 'app/entities/intolerancia/intolerancia.reducer';
import voluntario from 'app/entities/voluntario/voluntario.reducer';
import socio from 'app/entities/socio/socio.reducer';
import registro from 'app/entities/registro/registro.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  nucleo,
  donante,
  beneficiario,
  alimentoDeEntrada,
  alimentoDeSalida,
  tipoDeAlimento,
  tupper,
  intolerancia,
  voluntario,
  socio,
  registro,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
