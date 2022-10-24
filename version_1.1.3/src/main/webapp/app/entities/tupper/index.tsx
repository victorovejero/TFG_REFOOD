import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Tupper from './tupper';
import TupperDetail from './tupper-detail';
import TupperUpdate from './tupper-update';
import TupperDeleteDialog from './tupper-delete-dialog';

const TupperRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Tupper />} />
    <Route path="new" element={<TupperUpdate />} />
    <Route path=":id">
      <Route index element={<TupperDetail />} />
      <Route path="edit" element={<TupperUpdate />} />
      <Route path="delete" element={<TupperDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TupperRoutes;
