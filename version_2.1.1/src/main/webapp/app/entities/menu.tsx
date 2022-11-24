import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = ({ Admin }) => {
  return (
    <>
      {/* prettier-ignore */}
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
        <MenuItem icon="asterisk" to="/beneficiario">
          Beneficiario
        </MenuItem>
      ) : null}
      <MenuItem icon="asterisk" to="/alimento-de-entrada">
        Alimento De Entrada
      </MenuItem>
      <MenuItem icon="asterisk" to="/alimento-de-salida">
        Alimento De Salida
      </MenuItem>
      {Admin ? (
        <MenuItem icon="asterisk" to="/tipo-de-alimento">
          Tipo De Alimento
        </MenuItem>
      ) : null}
      {Admin ? (
        <MenuItem icon="asterisk" to="/tupper">
          Tupper
        </MenuItem>
      ) : null}
      {Admin ? (
        <MenuItem icon="asterisk" to="/intolerancia">
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
      <MenuItem icon="asterisk" to="/persona-beneficiaria">
        Persona Beneficiaria
      </MenuItem>
      ) : null}
      <MenuItem icon="asterisk" to="/checkout">
        Checkout
      </MenuItem>
      {Admin ? (
      <MenuItem icon="asterisk" to="/fruta-y-verdura">
        Fruta Y Verdura
      </MenuItem>
      ) : null}
      
      
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
