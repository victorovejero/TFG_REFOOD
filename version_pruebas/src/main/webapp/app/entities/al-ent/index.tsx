import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AlEnt from './al-ent';
import AlEntDetail from './al-ent-detail';
import AlEntUpdate from './al-ent-update';
import AlEntDeleteDialog from './al-ent-delete-dialog';

const AlEntRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AlEnt />} />
    <Route path="new" element={<AlEntUpdate />} />
    <Route path=":id">
      <Route index element={<AlEntDetail />} />
      <Route path="edit" element={<AlEntUpdate />} />
      <Route path="delete" element={<AlEntDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AlEntRoutes;
