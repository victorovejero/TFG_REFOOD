import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AlSal from './al-sal';
import AlSalDetail from './al-sal-detail';
import AlSalUpdate from './al-sal-update';
import AlSalDeleteDialog from './al-sal-delete-dialog';

const AlSalRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AlSal />} />
    <Route path="new" element={<AlSalUpdate />} />
    <Route path=":id">
      <Route index element={<AlSalDetail />} />
      <Route path="edit" element={<AlSalUpdate />} />
      <Route path="delete" element={<AlSalDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AlSalRoutes;
