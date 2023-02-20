import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Intol from './intol';
import IntolDetail from './intol-detail';
import IntolUpdate from './intol-update';
import IntolDeleteDialog from './intol-delete-dialog';

const IntolRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Intol />} />
    <Route path="new" element={<IntolUpdate />} />
    <Route path=":id">
      <Route index element={<IntolDetail />} />
      <Route path="edit" element={<IntolUpdate />} />
      <Route path="delete" element={<IntolDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default IntolRoutes;
