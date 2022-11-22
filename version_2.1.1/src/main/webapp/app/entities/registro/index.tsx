import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Registro from './registro';
import RegistroDetail from './registro-detail';
import RegistroUpdate from './registro-update';
import RegistroDeleteDialog from './registro-delete-dialog';

const RegistroRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Registro />} />
    <Route path="new" element={<RegistroUpdate />} />
    <Route path=":id">
      <Route index element={<RegistroDetail />} />
      <Route path="edit" element={<RegistroUpdate />} />
      <Route path="delete" element={<RegistroDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RegistroRoutes;
