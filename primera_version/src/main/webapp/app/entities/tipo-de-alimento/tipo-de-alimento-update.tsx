import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IIntolerancia } from 'app/shared/model/intolerancia.model';
import { getEntities as getIntolerancias } from 'app/entities/intolerancia/intolerancia.reducer';
import { ITipoDeAlimento } from 'app/shared/model/tipo-de-alimento.model';
import { getEntity, updateEntity, createEntity, reset } from './tipo-de-alimento.reducer';

export const TipoDeAlimentoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const intolerancias = useAppSelector(state => state.intolerancia.entities);
  const tipoDeAlimentoEntity = useAppSelector(state => state.tipoDeAlimento.entity);
  const loading = useAppSelector(state => state.tipoDeAlimento.loading);
  const updating = useAppSelector(state => state.tipoDeAlimento.updating);
  const updateSuccess = useAppSelector(state => state.tipoDeAlimento.updateSuccess);

  const handleClose = () => {
    navigate('/tipo-de-alimento');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getIntolerancias({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...tipoDeAlimentoEntity,
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
          ...tipoDeAlimentoEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="refoodTrazabilidadApp.tipoDeAlimento.home.createOrEditLabel" data-cy="TipoDeAlimentoCreateUpdateHeading">
            <Translate contentKey="refoodTrazabilidadApp.tipoDeAlimento.home.createOrEditLabel">Create or edit a TipoDeAlimento</Translate>
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
                  id="tipo-de-alimento-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('refoodTrazabilidadApp.tipoDeAlimento.nombreAlimento')}
                id="tipo-de-alimento-nombreAlimento"
                name="nombreAlimento"
                data-cy="nombreAlimento"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tipo-de-alimento" replace color="info">
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

export default TipoDeAlimentoUpdate;
