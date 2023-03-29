import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Nucleo from './nucleo';
import NucleoDetail from './nucleo-detail';
import NucleoUpdate from './nucleo-update';
import NucleoDeleteDialog from './nucleo-delete-dialog';

const NucleoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Nucleo />} />
    <Route path="new" element={<NucleoUpdate />} />
    <Route path=":id">
      <Route index element={<NucleoDetail />} />
      <Route path="edit" element={<NucleoUpdate />} />
      <Route path="delete" element={<NucleoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NucleoRoutes;
