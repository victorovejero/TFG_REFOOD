import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Nucleo from './nucleo';
import Donante from './donante';
import Benef from './benef';
import PBenef from './p-benef';
import AlEnt from './al-ent';
import AlSal from './al-sal';
import Checkout from './checkout';
import TipoAl from './tipo-al';
import Tupper from './tupper';
import Intol from './intol';
import Voluntario from './voluntario';
import Socio from './socio';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="nucleo/*" element={<Nucleo />} />
        <Route path="donante/*" element={<Donante />} />
        <Route path="benef/*" element={<Benef />} />
        <Route path="p-benef/*" element={<PBenef />} />
        <Route path="al-ent/*" element={<AlEnt />} />
        <Route path="al-sal/*" element={<AlSal />} />
        <Route path="checkout/*" element={<Checkout />} />
        <Route path="tipo-al/*" element={<TipoAl />} />
        <Route path="tupper/*" element={<Tupper />} />
        <Route path="intol/*" element={<Intol />} />
        <Route path="voluntario/*" element={<Voluntario />} />
        <Route path="socio/*" element={<Socio />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
