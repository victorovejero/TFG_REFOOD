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
import { INucleo } from 'app/shared/model/nucleo.model';
import { getEntities as getNucleos } from 'app/entities/nucleo/nucleo.reducer';
import { IBenef } from 'app/shared/model/benef.model';
import { getEntity, updateEntity, createEntity, reset } from './benef.reducer';

export const BenefUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const intols = useAppSelector(state => state.intol.entities);
  const nucleos = useAppSelector(state => state.nucleo.entities);
  const benefEntity = useAppSelector(state => state.benef.entity);
  const loading = useAppSelector(state => state.benef.loading);
  const updating = useAppSelector(state => state.benef.updating);
  const updateSuccess = useAppSelector(state => state.benef.updateSuccess);

  const handleClose = () => {
    navigate('/benef');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getIntols({}));
    dispatch(getNucleos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...benefEntity,
      ...values,
      intols: mapIdList(values.intols),
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
          ...benefEntity,
          intols: benefEntity?.intols?.map(e => e.id.toString()),
          nucleo: benefEntity?.nucleo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="refoodTrazabilidadAppV3App.benef.home.createOrEditLabel" data-cy="BenefCreateUpdateHeading">
            Crear o editar Benef
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="benef-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Id Beneficiario"
                id="benef-idBeneficiario"
                name="idBeneficiario"
                data-cy="idBeneficiario"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Nombre Representante"
                id="benef-nombreRepresentante"
                name="nombreRepresentante"
                data-cy="nombreRepresentante"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Primer Apellido Representante"
                id="benef-primerApellidoRepresentante"
                name="primerApellidoRepresentante"
                data-cy="primerApellidoRepresentante"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Segundo Apellido Representante"
                id="benef-segundoApellidoRepresentante"
                name="segundoApellidoRepresentante"
                data-cy="segundoApellidoRepresentante"
                type="text"
              />
              <ValidatedField
                label="Numero Personas"
                id="benef-numeroPersonas"
                name="numeroPersonas"
                data-cy="numeroPersonas"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  validate: v => isNumber(v) || 'Este campo debe ser un número.',
                }}
              />
              <ValidatedField
                label="Email"
                id="benef-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Telefono"
                id="benef-telefono"
                name="telefono"
                data-cy="telefono"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Telefono Secundario"
                id="benef-telefonoSecundario"
                name="telefonoSecundario"
                data-cy="telefonoSecundario"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Direccion"
                id="benef-direccion"
                name="direccion"
                data-cy="direccion"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Codigo Postal"
                id="benef-codigoPostal"
                name="codigoPostal"
                data-cy="codigoPostal"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Fecha Alta"
                id="benef-fechaAlta"
                name="fechaAlta"
                data-cy="fechaAlta"
                type="date"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField label="Fecha Baja" id="benef-fechaBaja" name="fechaBaja" data-cy="fechaBaja" type="date" />
              <ValidatedField
                label="Numero Ninios"
                id="benef-numeroNinios"
                name="numeroNinios"
                data-cy="numeroNinios"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  validate: v => isNumber(v) || 'Este campo debe ser un número.',
                }}
              />
              <ValidatedField label="Id Dual" id="benef-idDual" name="idDual" data-cy="idDual" type="text" />
              <ValidatedField label="Activo" id="benef-activo" name="activo" data-cy="activo" check type="checkbox" />
              <ValidatedField label="Intol" id="benef-intol" data-cy="intol" type="select" multiple name="intols">
                <option value="" key="0" />
                {intols
                  ? intols.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="benef-nucleo" name="nucleo" data-cy="nucleo" label="Nucleo" type="select">
                <option value="" key="0" />
                {nucleos
                  ? nucleos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/benef" replace color="info">
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

export default BenefUpdate;
