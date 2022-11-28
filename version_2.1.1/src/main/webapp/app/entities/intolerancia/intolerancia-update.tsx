import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBeneficiario } from 'app/shared/model/beneficiario.model';
import { getEntities as getBeneficiarios } from 'app/entities/beneficiario/beneficiario.reducer';
import { IPersonaBeneficiaria } from 'app/shared/model/persona-beneficiaria.model';
import { getEntities as getPersonaBeneficiarias } from 'app/entities/persona-beneficiaria/persona-beneficiaria.reducer';
import { ITipoDeAlimento } from 'app/shared/model/tipo-de-alimento.model';
import { getEntities as getTipoDeAlimentos } from 'app/entities/tipo-de-alimento/tipo-de-alimento.reducer';
import { IIntolerancia } from 'app/shared/model/intolerancia.model';
import { getEntity, updateEntity, createEntity, reset } from './intolerancia.reducer';

export const IntoleranciaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const beneficiarios = useAppSelector(state => state.beneficiario.entities);
  const personaBeneficiarias = useAppSelector(state => state.personaBeneficiaria.entities);
  const tipoDeAlimentos = useAppSelector(state => state.tipoDeAlimento.entities);
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

    dispatch(getBeneficiarios({}));
    dispatch(getPersonaBeneficiarias({}));
    dispatch(getTipoDeAlimentos({}));
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
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="refoodTrazabilidadApp.intolerancia.home.createOrEditLabel" data-cy="IntoleranciaCreateUpdateHeading">
            Crear o editar Intolerancia
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="intolerancia-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Nombre"
                id="intolerancia-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField label="Descripcion" id="intolerancia-descripcion" name="descripcion" data-cy="descripcion" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/intolerancia" replace color="info">
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

export default IntoleranciaUpdate;
