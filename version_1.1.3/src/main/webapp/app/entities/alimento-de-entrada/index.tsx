import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AlimentoDeEntrada from './alimento-de-entrada';
import AlimentoDeEntradaDetail from './alimento-de-entrada-detail';
import AlimentoDeEntradaUpdate from './alimento-de-entrada-update';
import AlimentoDeEntradaDeleteDialog from './alimento-de-entrada-delete-dialog';

const AlimentoDeEntradaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AlimentoDeEntrada />} />
    <Route path="new" element={<AlimentoDeEntradaUpdate />} />
    <Route path=":id">
      <Route index element={<AlimentoDeEntradaDetail />} />
      <Route path="edit" element={<AlimentoDeEntradaUpdate />} />
      <Route path="delete" element={<AlimentoDeEntradaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AlimentoDeEntradaRoutes;
