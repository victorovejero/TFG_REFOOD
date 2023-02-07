import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INucleo } from 'app/shared/model/nucleo.model';
import { getEntities as getNucleos } from 'app/entities/nucleo/nucleo.reducer';
import { IVoluntario } from 'app/shared/model/voluntario.model';
import { getEntity, updateEntity, createEntity, reset } from './voluntario.reducer';

export const VoluntarioUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const nucleos = useAppSelector(state => state.nucleo.entities);
  const voluntarioEntity = useAppSelector(state => state.voluntario.entity);
  const loading = useAppSelector(state => state.voluntario.loading);
  const updating = useAppSelector(state => state.voluntario.updating);
  const updateSuccess = useAppSelector(state => state.voluntario.updateSuccess);

  const handleClose = () => {
    navigate('/voluntario' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getNucleos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...voluntarioEntity,
      ...values,
      nucleo: nucleos.find(it => it.id.toString() === values.nucleo.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...voluntarioEntity,
          nucleo: voluntarioEntity?.nucleo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="refoodTrazabilidadApp.voluntario.home.createOrEditLabel" data-cy="VoluntarioCreateUpdateHeading">
            Crear o editar Voluntario
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="voluntario-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Id Voluntario"
                id="voluntario-idVoluntario"
                name="idVoluntario"
                data-cy="idVoluntario"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Nombre"
                id="voluntario-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Primer Apellido"
                id="voluntario-primerApellido"
                name="primerApellido"
                data-cy="primerApellido"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Segundo Apellido"
                id="voluntario-segundoApellido"
                name="segundoApellido"
                data-cy="segundoApellido"
                type="text"
              />
              <ValidatedField
                label="Email"
                id="voluntario-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Telefono Contacto"
                id="voluntario-telefonoContacto"
                name="telefonoContacto"
                data-cy="telefonoContacto"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField label="Dni" id="voluntario-dni" name="dni" data-cy="dni" type="text" />
              <ValidatedField
                label="Fecha Nacimiento"
                id="voluntario-fechaNacimiento"
                name="fechaNacimiento"
                data-cy="fechaNacimiento"
                type="date"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Sexo"
                id="voluntario-sexo"
                name="sexo"
                data-cy="sexo"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Fecha Alta"
                id="voluntario-fechaAlta"
                name="fechaAlta"
                data-cy="fechaAlta"
                type="date"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField label="Fecha Baja" id="voluntario-fechaBaja" name="fechaBaja" data-cy="fechaBaja" type="date" />
              <ValidatedField
                label="Categoria Perfil"
                id="voluntario-categoriaPerfil"
                name="categoriaPerfil"
                data-cy="categoriaPerfil"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Descripcion Categoria"
                id="voluntario-descripcionCategoria"
                name="descripcionCategoria"
                data-cy="descripcionCategoria"
                type="text"
              />
              <ValidatedField
                label="Dia Refood"
                id="voluntario-diaRefood"
                name="diaRefood"
                data-cy="diaRefood"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField label="Origen" id="voluntario-origen" name="origen" data-cy="origen" type="text" />
              <ValidatedField
                label="Manipulador Alimentos"
                id="voluntario-manipuladorAlimentos"
                name="manipuladorAlimentos"
                data-cy="manipuladorAlimentos"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Direccion"
                id="voluntario-direccion"
                name="direccion"
                data-cy="direccion"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Codigo Postal"
                id="voluntario-codigoPostal"
                name="codigoPostal"
                data-cy="codigoPostal"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField label="Activo" id="voluntario-activo" name="activo" data-cy="activo" check type="checkbox" />
              <ValidatedField id="voluntario-nucleo" name="nucleo" data-cy="nucleo" label="Nucleo" type="select">
                <option value="" key="0" />
                {nucleos
                  ? nucleos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/voluntario" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Volver</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Guardar
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default VoluntarioUpdate;
