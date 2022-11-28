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
import { IBeneficiario } from 'app/shared/model/beneficiario.model';
import { getEntities as getBeneficiarios } from 'app/entities/beneficiario/beneficiario.reducer';
import { IPersonaBeneficiaria } from 'app/shared/model/persona-beneficiaria.model';
import { getEntity, updateEntity, createEntity, reset } from './persona-beneficiaria.reducer';

export const PersonaBeneficiariaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const intolerancias = useAppSelector(state => state.intolerancia.entities);
  const beneficiarios = useAppSelector(state => state.beneficiario.entities);
  const personaBeneficiariaEntity = useAppSelector(state => state.personaBeneficiaria.entity);
  const loading = useAppSelector(state => state.personaBeneficiaria.loading);
  const updating = useAppSelector(state => state.personaBeneficiaria.updating);
  const updateSuccess = useAppSelector(state => state.personaBeneficiaria.updateSuccess);

  const handleClose = () => {
    navigate('/persona-beneficiaria');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getIntolerancias({}));
    dispatch(getBeneficiarios({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...personaBeneficiariaEntity,
      ...values,
      intolerancias: mapIdList(values.intolerancias),
      beneficiario: beneficiarios.find(it => it.id.toString() === values.beneficiario.toString()),
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
          ...personaBeneficiariaEntity,
          intolerancias: personaBeneficiariaEntity?.intolerancias?.map(e => e.id.toString()),
          beneficiario: personaBeneficiariaEntity?.beneficiario?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="refoodTrazabilidadApp.personaBeneficiaria.home.createOrEditLabel" data-cy="PersonaBeneficiariaCreateUpdateHeading">
            Crear o editar Persona Beneficiaria
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
                <ValidatedField name="id" required readOnly id="persona-beneficiaria-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Nombre"
                id="persona-beneficiaria-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Primer Apellido"
                id="persona-beneficiaria-primerApellido"
                name="primerApellido"
                data-cy="primerApellido"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Segundo Apellido"
                id="persona-beneficiaria-segundoApellido"
                name="segundoApellido"
                data-cy="segundoApellido"
                type="text"
              />
              <ValidatedField
                label="Fecha Nacimiento"
                id="persona-beneficiaria-fechaNacimiento"
                name="fechaNacimiento"
                data-cy="fechaNacimiento"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Sexo"
                id="persona-beneficiaria-sexo"
                name="sexo"
                data-cy="sexo"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Parentesco"
                id="persona-beneficiaria-parentesco"
                name="parentesco"
                data-cy="parentesco"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Situacion Profesional"
                id="persona-beneficiaria-situacionProfesional"
                name="situacionProfesional"
                data-cy="situacionProfesional"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Intolerancia"
                id="persona-beneficiaria-intolerancia"
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
              <ValidatedField
                id="persona-beneficiaria-beneficiario"
                name="beneficiario"
                data-cy="beneficiario"
                label="Beneficiario"
                type="select"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/persona-beneficiaria" replace color="info">
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

export default PersonaBeneficiariaUpdate;
