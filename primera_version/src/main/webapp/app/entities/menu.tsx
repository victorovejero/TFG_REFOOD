import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/nucleo">
        <Translate contentKey="global.menu.entities.nucleo" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/donante">
        <Translate contentKey="global.menu.entities.donante" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/beneficiario">
        <Translate contentKey="global.menu.entities.beneficiario" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/alimento-de-entrada">
        <Translate contentKey="global.menu.entities.alimentoDeEntrada" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/alimento-de-salida">
        <Translate contentKey="global.menu.entities.alimentoDeSalida" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tipo-de-alimento">
        <Translate contentKey="global.menu.entities.tipoDeAlimento" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tupper">
        <Translate contentKey="global.menu.entities.tupper" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/intolerancia">
        <Translate contentKey="global.menu.entities.intolerancia" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/voluntario">
        <Translate contentKey="global.menu.entities.voluntario" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/socio">
        <Translate contentKey="global.menu.entities.socio" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/registro">
        <Translate contentKey="global.menu.entities.registro" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
