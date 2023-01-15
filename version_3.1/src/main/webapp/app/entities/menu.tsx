import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = ({ Admin }) => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/al-ent">
        Alimento De Entrada
      </MenuItem>
      <MenuItem icon="asterisk" to="/al-sal">
        Alimento De Salida
      </MenuItem>
      <MenuItem icon="asterisk" to="/checkout">
        Checkout
      </MenuItem>
      {Admin ? (
        <MenuItem icon="asterisk" to="/nucleo">
          Nucleo
        </MenuItem>
      ) : null}
      {Admin ? (
        <MenuItem icon="asterisk" to="/donante">
          Donante
        </MenuItem>
      ) : null}
      {Admin ? (
        <MenuItem icon="asterisk" to="/benef">
          Beneficiario
        </MenuItem>
      ) : null}
      
      {Admin ? (
        <MenuItem icon="asterisk" to="/tipo-al">
          Tipo De Alimento
        </MenuItem>
      ) : null}
      {Admin ? (
        <MenuItem icon="asterisk" to="/tupper">
          Tupper
        </MenuItem>
      ) : null}
      {Admin ? (
        <MenuItem icon="asterisk" to="/intol">
          Intolerancia
        </MenuItem>
      ) : null}
      {Admin ? (
        <MenuItem icon="asterisk" to="/voluntario">
          Voluntario
        </MenuItem>
      ) : null}
      {Admin ? (
        <MenuItem icon="asterisk" to="/socio">
          Socio
        </MenuItem>
      ) : null}
      {Admin ? (
      <MenuItem icon="asterisk" to="/p-benef">
        Persona Beneficiaria
      </MenuItem>
      ) : null}
      
    
      
      
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
