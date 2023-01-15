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
      <MenuItem icon="asterisk" to="/benef">
        Benef
      </MenuItem>
      <MenuItem icon="asterisk" to="/p-benef">
        P Benef
      </MenuItem>
      <MenuItem icon="asterisk" to="/al-ent">
        Al Ent
      </MenuItem>
      <MenuItem icon="asterisk" to="/al-sal">
        Al Sal
      </MenuItem>
      <MenuItem icon="asterisk" to="/checkout">
        Checkout
      </MenuItem>
      <MenuItem icon="asterisk" to="/tipo-al">
        Tipo Al
      </MenuItem>
      <MenuItem icon="asterisk" to="/tupper">
        Tupper
      </MenuItem>
      <MenuItem icon="asterisk" to="/intol">
        Intol
      </MenuItem>
      <MenuItem icon="asterisk" to="/voluntario">
        Voluntario
      </MenuItem>
      <MenuItem icon="asterisk" to="/socio">
        Socio
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
