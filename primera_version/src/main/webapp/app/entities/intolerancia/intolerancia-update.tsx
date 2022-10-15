import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITipoDeAlimento } from 'app/shared/model/tipo-de-alimento.model';
import { getEntities as getTipoDeAlimentos } from 'app/entities/tipo-de-alimento/tipo-de-alimento.reducer';
import { IBeneficiario } from 'app/shared/model/beneficiario.model';
import { getEntities as getBeneficiarios } from 'app/entities/beneficiario/beneficiario.reducer';
import { IIntolerancia } from 'app/shared/model/intolerancia.model';
import { getEntity, updateEntity, createEntity, reset } from './intolerancia.reducer';

export const IntoleranciaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tipoDeAlimentos = useAppSelector(state => state.tipoDeAlimento.entities);
  const beneficiarios = useAppSelector(state => state.beneficiario.entities);
  const intoleranciaEntity = useAppSelector(state => state.intolerancia.entity);
  const loading = useAppSelector(state => state.intolerancia.loading);
  const updating = useAppSelector(state => state.intolerancia.updating);
  const updateSuccess = useAppSelector(state => state.intolerancia.updateSuccess);

  const handleClose = () => {
    navigate('/intolerancia');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getTipoDeAlimentos({}));
    dispatch(getBeneficiarios({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...intoleranciaEntity,
      ...values,
      tipoDeAlimentos: mapIdList(values.tipoDeAlimentos),
      beneficiarios: mapIdList(values.beneficiarios),
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
          ...intoleranciaEntity,
          tipoDeAlimentos: intoleranciaEntity?.tipoDeAlimentos?.map(e => e.id.toString()),
          beneficiarios: intoleranciaEntity?.beneficiarios?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="refoodTrazabilidadApp.intolerancia.home.createOrEditLabel" data-cy="IntoleranciaCreateUpdateHeading">
            <Translate contentKey="refoodTrazabilidadApp.intolerancia.home.createOrEditLabel">Create or edit a Intolerancia</Translate>
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
                  id="intolerancia-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('refoodTrazabilidadApp.intolerancia.nombreIntolerancia')}
                id="intolerancia-nombreIntolerancia"
                name="nombreIntolerancia"
                data-cy="nombreIntolerancia"
                type="text"
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.intolerancia.tipoDeAlimento')}
                id="intolerancia-tipoDeAlimento"
                data-cy="tipoDeAlimento"
                type="select"
                multiple
                name="tipoDeAlimentos"
              >
                <option value="" key="0" />
                {tipoDeAlimentos
                  ? tipoDeAlimentos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('refoodTrazabilidadApp.intolerancia.beneficiario')}
                id="intolerancia-beneficiario"
                data-cy="beneficiario"
                type="select"
                multiple
                name="beneficiarios"
              >
                <option value="" key="0" />
                {beneficiarios
                  ? beneficiarios.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/intolerancia" replace color="info">
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

export default IntoleranciaUpdate;
