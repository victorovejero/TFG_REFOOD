import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Intolerancia from './intolerancia';
import IntoleranciaDetail from './intolerancia-detail';
import IntoleranciaUpdate from './intolerancia-update';
import IntoleranciaDeleteDialog from './intolerancia-delete-dialog';

const IntoleranciaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Intolerancia />} />
    <Route path="new" element={<IntoleranciaUpdate />} />
    <Route path=":id">
      <Route index element={<IntoleranciaDetail />} />
      <Route path="edit" element={<IntoleranciaUpdate />} />
      <Route path="delete" element={<IntoleranciaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default IntoleranciaRoutes;
