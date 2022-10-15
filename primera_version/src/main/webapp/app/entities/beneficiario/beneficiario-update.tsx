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
import { IIntolerancia } from 'app/shared/model/intolerancia.model';
import { getEntities as getIntolerancias } from 'app/entities/intolerancia/intolerancia.reducer';
import { IBeneficiario } from 'app/shared/model/beneficiario.model';
import { getEntity, updateEntity, createEntity, reset } from './beneficiario.reducer';

export const BeneficiarioUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const nucleos = useAppSelector(state => state.nucleo.entities);
  const intolerancias = useAppSelector(state => state.intolerancia.entities);
  const beneficiarioEntity = useAppSelector(state => state.beneficiario.entity);
  const loading = useAppSelector(state => state.beneficiario.loading);
  const updating = useAppSelector(state => state.beneficiario.updating);
  const updateSuccess = useAppSelector(state => state.beneficiario.updateSuccess);

  const handleClose = () => {
    navigate('/beneficiario' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getNucleos({}));
    dispatch(getIntolerancias({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...beneficiarioEntity,
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
          ...beneficiarioEntity,
          nucleo: beneficiarioEntity?.nucleo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="refoodTrazabilidadApp.beneficiario.home.createOrEditLabel" data-cy="BeneficiarioCreateUpdateHeading">
            <Translate contentKey="refoodTrazabilidadApp.beneficiario.home.createOrEditLabel">Create or edit a Beneficiario</Translate>
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
                  id="beneficiario-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('refoodTrazabilidadApp.beneficiario.nombreBeneficiario')}
                id="beneficiario-nombreBeneficiario"
                name="nombreBeneficiario"
                data-cy="nombreBeneficiario"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.beneficiario.numPersonas')}
                id="beneficiario-numPersonas"
                name="numPersonas"
                data-cy="numPersonas"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.beneficiario.numNinios')}
                id="beneficiario-numNinios"
                name="numNinios"
                data-cy="numNinios"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.beneficiario.idDual')}
                id="beneficiario-idDual"
                name="idDual"
                data-cy="idDual"
                type="text"
              />
              <ValidatedField
                id="beneficiario-nucleo"
                name="nucleo"
                data-cy="nucleo"
                label={translate('refoodTrazabilidadApp.beneficiario.nucleo')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/beneficiario" replace color="info">
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

export default BeneficiarioUpdate;
