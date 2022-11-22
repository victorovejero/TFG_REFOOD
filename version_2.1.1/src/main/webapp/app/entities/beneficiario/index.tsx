import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Beneficiario from './beneficiario';
import BeneficiarioDetail from './beneficiario-detail';
import BeneficiarioUpdate from './beneficiario-update';
import BeneficiarioDeleteDialog from './beneficiario-delete-dialog';

const BeneficiarioRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Beneficiario />} />
    <Route path="new" element={<BeneficiarioUpdate />} />
    <Route path=":id">
      <Route index element={<BeneficiarioDetail />} />
      <Route path="edit" element={<BeneficiarioUpdate />} />
      <Route path="delete" element={<BeneficiarioDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BeneficiarioRoutes;
