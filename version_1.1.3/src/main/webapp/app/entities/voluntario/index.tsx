import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Voluntario from './voluntario';
import VoluntarioDetail from './voluntario-detail';
import VoluntarioUpdate from './voluntario-update';
import VoluntarioDeleteDialog from './voluntario-delete-dialog';

const VoluntarioRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Voluntario />} />
    <Route path="new" element={<VoluntarioUpdate />} />
    <Route path=":id">
      <Route index element={<VoluntarioDetail />} />
      <Route path="edit" element={<VoluntarioUpdate />} />
      <Route path="delete" element={<VoluntarioDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VoluntarioRoutes;
