import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBenef } from 'app/shared/model/benef.model';
import { getEntities as getBenefs } from 'app/entities/benef/benef.reducer';
import { IPBenef } from 'app/shared/model/p-benef.model';
import { getEntities as getPBenefs } from 'app/entities/p-benef/p-benef.reducer';
import { ITipoAl } from 'app/shared/model/tipo-al.model';
import { getEntities as getTipoAls } from 'app/entities/tipo-al/tipo-al.reducer';
import { IIntol } from 'app/shared/model/intol.model';
import { getEntity, updateEntity, createEntity, reset } from './intol.reducer';

export const IntolUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const benefs = useAppSelector(state => state.benef.entities);
  const pBenefs = useAppSelector(state => state.pBenef.entities);
  const tipoAls = useAppSelector(state => state.tipoAl.entities);
  const intolEntity = useAppSelector(state => state.intol.entity);
  const loading = useAppSelector(state => state.intol.loading);
  const updating = useAppSelector(state => state.intol.updating);
  const updateSuccess = useAppSelector(state => state.intol.updateSuccess);

  const handleClose = () => {
    navigate('/intol' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBenefs({}));
    dispatch(getPBenefs({}));
    dispatch(getTipoAls({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...intolEntity,
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
          ...intolEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="reefoodTrazabilidadAppV3App.intol.home.createOrEditLabel" data-cy="IntolCreateUpdateHeading">
            Crear o editar Intol
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="intol-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Nombre"
                id="intol-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField label="Descripcion" id="intol-descripcion" name="descripcion" data-cy="descripcion" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/intol" replace color="info">
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

export default IntolUpdate;
