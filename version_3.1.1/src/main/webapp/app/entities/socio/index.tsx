import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Socio from './socio';
import SocioDetail from './socio-detail';
import SocioUpdate from './socio-update';
import SocioDeleteDialog from './socio-delete-dialog';

const SocioRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Socio />} />
    <Route path="new" element={<SocioUpdate />} />
    <Route path=":id">
      <Route index element={<SocioDetail />} />
      <Route path="edit" element={<SocioUpdate />} />
      <Route path="delete" element={<SocioDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SocioRoutes;
