import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Checkout from './checkout';
import CheckoutDetail from './checkout-detail';
import CheckoutUpdate from './checkout-update';
import CheckoutDeleteDialog from './checkout-delete-dialog';

const CheckoutRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Checkout />} />
    <Route path="new" element={<CheckoutUpdate />} />
    <Route path=":id">
      <Route index element={<CheckoutDetail />} />
      <Route path="edit" element={<CheckoutUpdate />} />
      <Route path="delete" element={<CheckoutDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CheckoutRoutes;
