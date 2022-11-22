import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAlimentoDeEntrada } from 'app/shared/model/alimento-de-entrada.model';
import { getEntities as getAlimentoDeEntradas } from 'app/entities/alimento-de-entrada/alimento-de-entrada.reducer';
import { IFrutaYVerdura } from 'app/shared/model/fruta-y-verdura.model';
import { getEntity, updateEntity, createEntity, reset } from './fruta-y-verdura.reducer';

export const FrutaYVerduraUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const alimentoDeEntradas = useAppSelector(state => state.alimentoDeEntrada.entities);
  const frutaYVerduraEntity = useAppSelector(state => state.frutaYVerdura.entity);
  const loading = useAppSelector(state => state.frutaYVerdura.loading);
  const updating = useAppSelector(state => state.frutaYVerdura.updating);
  const updateSuccess = useAppSelector(state => state.frutaYVerdura.updateSuccess);

  const handleClose = () => {
    navigate('/fruta-y-verdura');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getAlimentoDeEntradas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...frutaYVerduraEntity,
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
          ...frutaYVerduraEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="refoodTrazabilidadApp.frutaYVerdura.home.createOrEditLabel" data-cy="FrutaYVerduraCreateUpdateHeading">
            Crear o editar Fruta Y Verdura
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
                <ValidatedField name="id" required readOnly id="fruta-y-verdura-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Nombre Alimento"
                id="fruta-y-verdura-nombreAlimento"
                name="nombreAlimento"
                data-cy="nombreAlimento"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/fruta-y-verdura" replace color="info">
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

export default FrutaYVerduraUpdate;
