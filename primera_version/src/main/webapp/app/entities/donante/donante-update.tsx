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
import { IDonante } from 'app/shared/model/donante.model';
import { getEntity, updateEntity, createEntity, reset } from './donante.reducer';

export const DonanteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const nucleos = useAppSelector(state => state.nucleo.entities);
  const donanteEntity = useAppSelector(state => state.donante.entity);
  const loading = useAppSelector(state => state.donante.loading);
  const updating = useAppSelector(state => state.donante.updating);
  const updateSuccess = useAppSelector(state => state.donante.updateSuccess);

  const handleClose = () => {
    navigate('/donante' + location.search);
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
      ...donanteEntity,
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
          ...donanteEntity,
          nucleo: donanteEntity?.nucleo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="refoodTrazabilidadApp.donante.home.createOrEditLabel" data-cy="DonanteCreateUpdateHeading">
            <Translate contentKey="refoodTrazabilidadApp.donante.home.createOrEditLabel">Create or edit a Donante</Translate>
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
                  id="donante-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('refoodTrazabilidadApp.donante.nombreDonante')}
                id="donante-nombreDonante"
                name="nombreDonante"
                data-cy="nombreDonante"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.donante.tipoDonante')}
                id="donante-tipoDonante"
                name="tipoDonante"
                data-cy="tipoDonante"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.donante.ruta')}
                id="donante-ruta"
                name="ruta"
                data-cy="ruta"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.donante.direccionDonante')}
                id="donante-direccionDonante"
                name="direccionDonante"
                data-cy="direccionDonante"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.donante.telefonoDonante')}
                id="donante-telefonoDonante"
                name="telefonoDonante"
                data-cy="telefonoDonante"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.donante.emailDonante')}
                id="donante-emailDonante"
                name="emailDonante"
                data-cy="emailDonante"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.donante.responsableDonante')}
                id="donante-responsableDonante"
                name="responsableDonante"
                data-cy="responsableDonante"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.donante.fechaAlta')}
                id="donante-fechaAlta"
                name="fechaAlta"
                data-cy="fechaAlta"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.donante.fechaBaja')}
                id="donante-fechaBaja"
                name="fechaBaja"
                data-cy="fechaBaja"
                type="date"
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.donante.comentarios')}
                id="donante-comentarios"
                name="comentarios"
                data-cy="comentarios"
                type="text"
              />
              <ValidatedField
                id="donante-nucleo"
                name="nucleo"
                data-cy="nucleo"
                label={translate('refoodTrazabilidadApp.donante.nucleo')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/donante" replace color="info">
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

export default DonanteUpdate;
