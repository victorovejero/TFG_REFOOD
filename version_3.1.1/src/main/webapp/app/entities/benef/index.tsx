import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Benef from './benef';
import BenefDetail from './benef-detail';
import BenefUpdate from './benef-update';
import BenefDeleteDialog from './benef-delete-dialog';

const BenefRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Benef />} />
    <Route path="new" element={<BenefUpdate />} />
    <Route path=":id">
      <Route index element={<BenefDetail />} />
      <Route path="edit" element={<BenefUpdate />} />
      <Route path="delete" element={<BenefDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BenefRoutes;
