import nucleo from 'app/entities/nucleo/nucleo.reducer';
import donante from 'app/entities/donante/donante.reducer';
import benef from 'app/entities/benef/benef.reducer';
import pBenef from 'app/entities/p-benef/p-benef.reducer';
import alEnt from 'app/entities/al-ent/al-ent.reducer';
import alSal from 'app/entities/al-sal/al-sal.reducer';
import checkout from 'app/entities/checkout/checkout.reducer';
import tipoAl from 'app/entities/tipo-al/tipo-al.reducer';
import tupper from 'app/entities/tupper/tupper.reducer';
import intol from 'app/entities/intol/intol.reducer';
import voluntario from 'app/entities/voluntario/voluntario.reducer';
import socio from 'app/entities/socio/socio.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  nucleo,
  donante,
  benef,
  pBenef,
  alEnt,
  alSal,
  checkout,
  tipoAl,
  tupper,
  intol,
  voluntario,
  socio,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
