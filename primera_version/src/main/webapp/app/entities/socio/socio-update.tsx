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
import { ISocio } from 'app/shared/model/socio.model';
import { getEntity, updateEntity, createEntity, reset } from './socio.reducer';

export const SocioUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const nucleos = useAppSelector(state => state.nucleo.entities);
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

    dispatch(getNucleos({}));
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
          ...socioEntity,
          nucleo: socioEntity?.nucleo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="refoodTrazabilidadApp.socio.home.createOrEditLabel" data-cy="SocioCreateUpdateHeading">
            <Translate contentKey="refoodTrazabilidadApp.socio.home.createOrEditLabel">Create or edit a Socio</Translate>
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
                  id="socio-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('refoodTrazabilidadApp.socio.nombreSocio')}
                id="socio-nombreSocio"
                name="nombreSocio"
                data-cy="nombreSocio"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.socio.primerApellidoSocio')}
                id="socio-primerApellidoSocio"
                name="primerApellidoSocio"
                data-cy="primerApellidoSocio"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.socio.segundoApellidoSocio')}
                id="socio-segundoApellidoSocio"
                name="segundoApellidoSocio"
                data-cy="segundoApellidoSocio"
                type="text"
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.socio.emailSocio')}
                id="socio-emailSocio"
                name="emailSocio"
                data-cy="emailSocio"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.socio.telefonoContactoSocio')}
                id="socio-telefonoContactoSocio"
                name="telefonoContactoSocio"
                data-cy="telefonoContactoSocio"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.socio.dniSocio')}
                id="socio-dniSocio"
                name="dniSocio"
                data-cy="dniSocio"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.socio.fechaNacimientoSocio')}
                id="socio-fechaNacimientoSocio"
                name="fechaNacimientoSocio"
                data-cy="fechaNacimientoSocio"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.socio.sexoSocio')}
                id="socio-sexoSocio"
                name="sexoSocio"
                data-cy="sexoSocio"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.socio.fechaAltaSocio')}
                id="socio-fechaAltaSocio"
                name="fechaAltaSocio"
                data-cy="fechaAltaSocio"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.socio.fechaBajaSocio')}
                id="socio-fechaBajaSocio"
                name="fechaBajaSocio"
                data-cy="fechaBajaSocio"
                type="date"
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.socio.contribucionMensual')}
                id="socio-contribucionMensual"
                name="contribucionMensual"
                data-cy="contribucionMensual"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.socio.periodoPago')}
                id="socio-periodoPago"
                name="periodoPago"
                data-cy="periodoPago"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="socio-nucleo"
                name="nucleo"
                data-cy="nucleo"
                label={translate('refoodTrazabilidadApp.socio.nucleo')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/socio" replace color="info">
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

export default SocioUpdate;
