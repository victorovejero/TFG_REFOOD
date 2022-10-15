import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INucleo } from 'app/shared/model/nucleo.model';
import { getEntities as getNucleos } from 'app/entities/nucleo/nucleo.reducer';
import { IRegistro } from 'app/shared/model/registro.model';
import { getEntities as getRegistros } from 'app/entities/registro/registro.reducer';
import { IVoluntario } from 'app/shared/model/voluntario.model';
import { getEntity, updateEntity, createEntity, reset } from './voluntario.reducer';

export const VoluntarioUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const nucleos = useAppSelector(state => state.nucleo.entities);
  const registros = useAppSelector(state => state.registro.entities);
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
    dispatch(getRegistros({}));
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
            <Translate contentKey="refoodTrazabilidadApp.voluntario.home.createOrEditLabel">Create or edit a Voluntario</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="voluntario-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('refoodTrazabilidadApp.voluntario.nombreVoluntario')}
                id="voluntario-nombreVoluntario"
                name="nombreVoluntario"
                data-cy="nombreVoluntario"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.voluntario.primerApellido')}
                id="voluntario-primerApellido"
                name="primerApellido"
                data-cy="primerApellido"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.voluntario.segundoApellido')}
                id="voluntario-segundoApellido"
                name="segundoApellido"
                data-cy="segundoApellido"
                type="text"
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.voluntario.emailVoluntario')}
                id="voluntario-emailVoluntario"
                name="emailVoluntario"
                data-cy="emailVoluntario"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.voluntario.telefonoContactoVoluntario')}
                id="voluntario-telefonoContactoVoluntario"
                name="telefonoContactoVoluntario"
                data-cy="telefonoContactoVoluntario"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.voluntario.dniVoluntario')}
                id="voluntario-dniVoluntario"
                name="dniVoluntario"
                data-cy="dniVoluntario"
                type="text"
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.voluntario.fechaNacimientoVoluntario')}
                id="voluntario-fechaNacimientoVoluntario"
                name="fechaNacimientoVoluntario"
                data-cy="fechaNacimientoVoluntario"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.voluntario.sexoVoluntario')}
                id="voluntario-sexoVoluntario"
                name="sexoVoluntario"
                data-cy="sexoVoluntario"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.voluntario.fechaAlta')}
                id="voluntario-fechaAlta"
                name="fechaAlta"
                data-cy="fechaAlta"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.voluntario.fechaBaja')}
                id="voluntario-fechaBaja"
                name="fechaBaja"
                data-cy="fechaBaja"
                type="date"
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.voluntario.tipoVoluntario')}
                id="voluntario-tipoVoluntario"
                name="tipoVoluntario"
                data-cy="tipoVoluntario"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.voluntario.tipoTurno')}
                id="voluntario-tipoTurno"
                name="tipoTurno"
                data-cy="tipoTurno"
                type="text"
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.voluntario.responsableDia')}
                id="voluntario-responsableDia"
                name="responsableDia"
                data-cy="responsableDia"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.voluntario.origenVoluntario')}
                id="voluntario-origenVoluntario"
                name="origenVoluntario"
                data-cy="origenVoluntario"
                type="text"
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.voluntario.manipuladorAlimentos')}
                id="voluntario-manipuladorAlimentos"
                name="manipuladorAlimentos"
                data-cy="manipuladorAlimentos"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.voluntario.codigoPostal')}
                id="voluntario-codigoPostal"
                name="codigoPostal"
                data-cy="codigoPostal"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="voluntario-nucleo"
                name="nucleo"
                data-cy="nucleo"
                label={translate('refoodTrazabilidadApp.voluntario.nucleo')}
                type="select"
              >
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
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default VoluntarioUpdate;
