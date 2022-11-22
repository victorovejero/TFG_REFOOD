import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TipoDeAlimento from './tipo-de-alimento';
import TipoDeAlimentoDetail from './tipo-de-alimento-detail';
import TipoDeAlimentoUpdate from './tipo-de-alimento-update';
import TipoDeAlimentoDeleteDialog from './tipo-de-alimento-delete-dialog';

const TipoDeAlimentoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TipoDeAlimento />} />
    <Route path="new" element={<TipoDeAlimentoUpdate />} />
    <Route path=":id">
      <Route index element={<TipoDeAlimentoDetail />} />
      <Route path="edit" element={<TipoDeAlimentoUpdate />} />
      <Route path="delete" element={<TipoDeAlimentoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TipoDeAlimentoRoutes;
