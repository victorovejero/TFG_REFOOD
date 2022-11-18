import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
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
      intolerancias: mapIdList(values.intolerancias),
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
          intolerancias: tipoDeAlimentoEntity?.intolerancias?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="refoodTrazabilidadApp.tipoDeAlimento.home.createOrEditLabel" data-cy="TipoDeAlimentoCreateUpdateHeading">
            Crear o editar Tipo De Alimento
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
                <ValidatedField name="id" required readOnly id="tipo-de-alimento-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Nombre Alimento"
                id="tipo-de-alimento-nombreAlimento"
                name="nombreAlimento"
                data-cy="nombreAlimento"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Intolerancia"
                id="tipo-de-alimento-intolerancia"
                data-cy="intolerancia"
                type="select"
                multiple
                name="intolerancias"
              >
                <option value="" key="0" />
                {intolerancias
                  ? intolerancias.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tipo-de-alimento" replace color="info">
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

export default TipoDeAlimentoUpdate;
