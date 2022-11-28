import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FrutaYVerdura from './fruta-y-verdura';
import FrutaYVerduraDetail from './fruta-y-verdura-detail';
import FrutaYVerduraUpdate from './fruta-y-verdura-update';
import FrutaYVerduraDeleteDialog from './fruta-y-verdura-delete-dialog';

const FrutaYVerduraRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FrutaYVerdura />} />
    <Route path="new" element={<FrutaYVerduraUpdate />} />
    <Route path=":id">
      <Route index element={<FrutaYVerduraDetail />} />
      <Route path="edit" element={<FrutaYVerduraUpdate />} />
      <Route path="delete" element={<FrutaYVerduraDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FrutaYVerduraRoutes;
