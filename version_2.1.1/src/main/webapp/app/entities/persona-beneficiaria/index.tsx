import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PersonaBeneficiaria from './persona-beneficiaria';
import PersonaBeneficiariaDetail from './persona-beneficiaria-detail';
import PersonaBeneficiariaUpdate from './persona-beneficiaria-update';
import PersonaBeneficiariaDeleteDialog from './persona-beneficiaria-delete-dialog';

const PersonaBeneficiariaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PersonaBeneficiaria />} />
    <Route path="new" element={<PersonaBeneficiariaUpdate />} />
    <Route path=":id">
      <Route index element={<PersonaBeneficiariaDetail />} />
      <Route path="edit" element={<PersonaBeneficiariaUpdate />} />
      <Route path="delete" element={<PersonaBeneficiariaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PersonaBeneficiariaRoutes;
