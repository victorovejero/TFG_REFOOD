import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IIntol } from 'app/shared/model/intol.model';
import { getEntities as getIntols } from 'app/entities/intol/intol.reducer';
import { IBenef } from 'app/shared/model/benef.model';
import { getEntities as getBenefs } from 'app/entities/benef/benef.reducer';
import { IPBenef } from 'app/shared/model/p-benef.model';
import { getEntity, updateEntity, createEntity, reset } from './p-benef.reducer';

export const PBenefUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const intols = useAppSelector(state => state.intol.entities);
  const benefs = useAppSelector(state => state.benef.entities);
  const pBenefEntity = useAppSelector(state => state.pBenef.entity);
  const loading = useAppSelector(state => state.pBenef.loading);
  const updating = useAppSelector(state => state.pBenef.updating);
  const updateSuccess = useAppSelector(state => state.pBenef.updateSuccess);

  const handleClose = () => {
    navigate('/p-benef' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getIntols({}));
    dispatch(getBenefs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...pBenefEntity,
      ...values,
      intols: mapIdList(values.intols),
      benef: benefs.find(it => it.id.toString() === values.benef.toString()),
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
          ...pBenefEntity,
          intols: pBenefEntity?.intols?.map(e => e.id.toString()),
          benef: pBenefEntity?.benef?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="reefoodTrazabilidadAppV3App.pBenef.home.createOrEditLabel" data-cy="PBenefCreateUpdateHeading">
            Crear o editar P Benef
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="p-benef-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Nombre"
                id="p-benef-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Primer Apellido"
                id="p-benef-primerApellido"
                name="primerApellido"
                data-cy="primerApellido"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Segundo Apellido"
                id="p-benef-segundoApellido"
                name="segundoApellido"
                data-cy="segundoApellido"
                type="text"
              />
              <ValidatedField
                label="Fecha Nacimiento"
                id="p-benef-fechaNacimiento"
                name="fechaNacimiento"
                data-cy="fechaNacimiento"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Sexo"
                id="p-benef-sexo"
                name="sexo"
                data-cy="sexo"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Parentesco"
                id="p-benef-parentesco"
                name="parentesco"
                data-cy="parentesco"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Situacion Profesional"
                id="p-benef-situacionProfesional"
                name="situacionProfesional"
                data-cy="situacionProfesional"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField label="Intol" id="p-benef-intol" data-cy="intol" type="select" multiple name="intols">
                <option value="" key="0" />
                {intols
                  ? intols.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="p-benef-benef" name="benef" data-cy="benef" label="Benef" type="select">
                <option value="" key="0" />
                {benefs
                  ? benefs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/p-benef" replace color="info">
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

export default PBenefUpdate;
