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
import { INucleo } from 'app/shared/model/nucleo.model';
import { getEntities as getNucleos } from 'app/entities/nucleo/nucleo.reducer';
import { IBeneficiario } from 'app/shared/model/beneficiario.model';
import { getEntity, updateEntity, createEntity, reset } from './beneficiario.reducer';

export const BeneficiarioUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const intolerancias = useAppSelector(state => state.intolerancia.entities);
  const nucleos = useAppSelector(state => state.nucleo.entities);
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

    dispatch(getIntolerancias({}));
    dispatch(getNucleos({}));
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
      intolerancias: mapIdList(values.intolerancias),
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
          intolerancias: beneficiarioEntity?.intolerancias?.map(e => e.id.toString()),
          nucleo: beneficiarioEntity?.nucleo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="refoodTrazabilidadApp.beneficiario.home.createOrEditLabel" data-cy="BeneficiarioCreateUpdateHeading">
            Crear o editar Beneficiario
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="beneficiario-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Id Beneficiario"
                id="beneficiario-idBeneficiario"
                name="idBeneficiario"
                data-cy="idBeneficiario"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Nombre"
                id="beneficiario-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Numero Personas"
                id="beneficiario-numeroPersonas"
                name="numeroPersonas"
                data-cy="numeroPersonas"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  validate: v => isNumber(v) || 'Este campo debe ser un número.',
                }}
              />
              <ValidatedField
                label="Numero Ninios"
                id="beneficiario-numeroNinios"
                name="numeroNinios"
                data-cy="numeroNinios"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  validate: v => isNumber(v) || 'Este campo debe ser un número.',
                }}
              />
              <ValidatedField label="Id Dual" id="beneficiario-idDual" name="idDual" data-cy="idDual" type="text" />
              <ValidatedField label="Activo" id="beneficiario-activo" name="activo" data-cy="activo" check type="checkbox" />
              <ValidatedField
                label="Intolerancia"
                id="beneficiario-intolerancia"
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
              <ValidatedField id="beneficiario-nucleo" name="nucleo" data-cy="nucleo" label="Nucleo" type="select">
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

export default BeneficiarioUpdate;
