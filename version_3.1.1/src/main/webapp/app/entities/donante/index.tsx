import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Donante from './donante';
import DonanteDetail from './donante-detail';
import DonanteUpdate from './donante-update';
import DonanteDeleteDialog from './donante-delete-dialog';

const DonanteRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Donante />} />
    <Route path="new" element={<DonanteUpdate />} />
    <Route path=":id">
      <Route index element={<DonanteDetail />} />
      <Route path="edit" element={<DonanteUpdate />} />
      <Route path="delete" element={<DonanteDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DonanteRoutes;
