import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PBenef from './p-benef';
import PBenefDetail from './p-benef-detail';
import PBenefUpdate from './p-benef-update';
import PBenefDeleteDialog from './p-benef-delete-dialog';

const PBenefRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PBenef />} />
    <Route path="new" element={<PBenefUpdate />} />
    <Route path=":id">
      <Route index element={<PBenefDetail />} />
      <Route path="edit" element={<PBenefUpdate />} />
      <Route path="delete" element={<PBenefDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PBenefRoutes;
