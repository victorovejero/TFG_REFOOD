import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TipoAl from './tipo-al';
import TipoAlDetail from './tipo-al-detail';
import TipoAlUpdate from './tipo-al-update';
import TipoAlDeleteDialog from './tipo-al-delete-dialog';

const TipoAlRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TipoAl />} />
    <Route path="new" element={<TipoAlUpdate />} />
    <Route path=":id">
      <Route index element={<TipoAlDetail />} />
      <Route path="edit" element={<TipoAlUpdate />} />
      <Route path="delete" element={<TipoAlDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TipoAlRoutes;
