import React from 'react';

import { NavDropdown } from './menu-components';
import EntitiesMenuItems from 'app/entities/menu';

export const EntitiesMenu = ({Admin}) => (
  <NavDropdown icon="th-list" name="Histórico" id="entity-menu" data-cy="entity" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <EntitiesMenuItems Admin={Admin}/>
  </NavDropdown>
);
