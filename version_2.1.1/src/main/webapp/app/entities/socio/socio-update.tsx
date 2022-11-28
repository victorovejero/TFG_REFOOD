import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISocio } from 'app/shared/model/socio.model';
import { getEntity, updateEntity, createEntity, reset } from './socio.reducer';

export const SocioUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const socioEntity = useAppSelector(state => state.socio.entity);
  const loading = useAppSelector(state => state.socio.loading);
  const updating = useAppSelector(state => state.socio.updating);
  const updateSuccess = useAppSelector(state => state.socio.updateSuccess);

  const handleClose = () => {
    navigate('/socio');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...socioEntity,
      ...values,
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
          ...socioEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="refoodTrazabilidadApp.socio.home.createOrEditLabel" data-cy="SocioCreateUpdateHeading">
            Crear o editar Socio
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="socio-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Nombre"
                id="socio-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Primer Apellido"
                id="socio-primerApellido"
                name="primerApellido"
                data-cy="primerApellido"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Segundo Apellido"
                id="socio-segundoApellido"
                name="segundoApellido"
                data-cy="segundoApellido"
                type="text"
              />
              <ValidatedField
                label="Email"
                id="socio-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Telefono Contacto"
                id="socio-telefonoContacto"
                name="telefonoContacto"
                data-cy="telefonoContacto"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="I BAN"
                id="socio-iBAN"
                name="iBAN"
                data-cy="iBAN"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Dni"
                id="socio-dni"
                name="dni"
                data-cy="dni"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Fecha Nacimiento"
                id="socio-fechaNacimiento"
                name="fechaNacimiento"
                data-cy="fechaNacimiento"
                type="date"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Sexo"
                id="socio-sexo"
                name="sexo"
                data-cy="sexo"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Fecha Alta"
                id="socio-fechaAlta"
                name="fechaAlta"
                data-cy="fechaAlta"
                type="date"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField label="Fecha Baja" id="socio-fechaBaja" name="fechaBaja" data-cy="fechaBaja" type="date" />
              <ValidatedField
                label="Contribucion Mensual"
                id="socio-contribucionMensual"
                name="contribucionMensual"
                data-cy="contribucionMensual"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  validate: v => isNumber(v) || 'Este campo debe ser un número.',
                }}
              />
              <ValidatedField
                label="Periodo Pago"
                id="socio-periodoPago"
                name="periodoPago"
                data-cy="periodoPago"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField label="Activo" id="socio-activo" name="activo" data-cy="activo" check type="checkbox" />
              <ValidatedField
                label="Nucleo Asociado"
                id="socio-nucleoAsociado"
                name="nucleoAsociado"
                data-cy="nucleoAsociado"
                type="text"
              />
              <ValidatedField
                label="Comunicacion"
                id="socio-comunicacion"
                name="comunicacion"
                data-cy="comunicacion"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Direccion"
                id="socio-direccion"
                name="direccion"
                data-cy="direccion"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Codigo Postal"
                id="socio-codigoPostal"
                name="codigoPostal"
                data-cy="codigoPostal"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField label="Provincia" id="socio-provincia" name="provincia" data-cy="provincia" type="text" />
              <ValidatedField label="Pais" id="socio-pais" name="pais" data-cy="pais" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/socio" replace color="info">
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

export default SocioUpdate;
