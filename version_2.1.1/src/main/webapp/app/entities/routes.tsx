import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Nucleo from './nucleo';
import Donante from './donante';
import Beneficiario from './beneficiario';
import AlimentoDeEntrada from './alimento-de-entrada';
import AlimentoDeSalida from './alimento-de-salida';
import TipoDeAlimento from './tipo-de-alimento';
import Tupper from './tupper';
import Intolerancia from './intolerancia';
import Voluntario from './voluntario';
import Socio from './socio';
import Registro from './registro';
import PersonaBeneficiaria from './persona-beneficiaria';
import Checkout from './checkout';
import FrutaYVerdura from './fruta-y-verdura';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="nucleo/*" element={<Nucleo />} />
        <Route path="donante/*" element={<Donante />} />
        <Route path="beneficiario/*" element={<Beneficiario />} />
        <Route path="alimento-de-entrada/*" element={<AlimentoDeEntrada />} />
        <Route path="alimento-de-salida/*" element={<AlimentoDeSalida />} />
        <Route path="tipo-de-alimento/*" element={<TipoDeAlimento />} />
        <Route path="tupper/*" element={<Tupper />} />
        <Route path="intolerancia/*" element={<Intolerancia />} />
        <Route path="voluntario/*" element={<Voluntario />} />
        <Route path="socio/*" element={<Socio />} />
        <Route path="registro/*" element={<Registro />} />
        <Route path="persona-beneficiaria/*" element={<PersonaBeneficiaria />} />
        <Route path="checkout/*" element={<Checkout />} />
        <Route path="fruta-y-verdura/*" element={<FrutaYVerdura />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
