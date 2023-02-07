import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AlimentoDeSalida from './alimento-de-salida';
import AlimentoDeSalidaDetail from './alimento-de-salida-detail';
import AlimentoDeSalidaUpdate from './alimento-de-salida-update';
import AlimentoDeSalidaDeleteDialog from './alimento-de-salida-delete-dialog';

const AlimentoDeSalidaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AlimentoDeSalida />} />
    <Route path="new" element={<AlimentoDeSalidaUpdate />} />
    <Route path=":id">
      <Route index element={<AlimentoDeSalidaDetail />} />
      <Route path="edit" element={<AlimentoDeSalidaUpdate />} />
      <Route path="delete" element={<AlimentoDeSalidaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AlimentoDeSalidaRoutes;
