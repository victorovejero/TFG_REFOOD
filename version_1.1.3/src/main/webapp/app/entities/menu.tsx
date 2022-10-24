import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/nucleo">
        Nucleo
      </MenuItem>
      <MenuItem icon="asterisk" to="/donante">
        Donante
      </MenuItem>
      <MenuItem icon="asterisk" to="/beneficiario">
        Beneficiario
      </MenuItem>
      <MenuItem icon="asterisk" to="/alimento-de-entrada">
        Alimento De Entrada
      </MenuItem>
      <MenuItem icon="asterisk" to="/alimento-de-salida">
        Alimento De Salida
      </MenuItem>
      <MenuItem icon="asterisk" to="/tipo-de-alimento">
        Tipo De Alimento
      </MenuItem>
      <MenuItem icon="asterisk" to="/tupper">
        Tupper
      </MenuItem>
      <MenuItem icon="asterisk" to="/intolerancia">
        Intolerancia
      </MenuItem>
      <MenuItem icon="asterisk" to="/voluntario">
        Voluntario
      </MenuItem>
      <MenuItem icon="asterisk" to="/socio">
        Socio
      </MenuItem>
      <MenuItem icon="asterisk" to="/registro">
        Registro
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
