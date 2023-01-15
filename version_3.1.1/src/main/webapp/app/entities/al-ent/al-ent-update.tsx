import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITupper } from 'app/shared/model/tupper.model';
import { getEntities as getTuppers } from 'app/entities/tupper/tupper.reducer';
import { IDonante } from 'app/shared/model/donante.model';
import { getEntities as getDonantes } from 'app/entities/donante/donante.reducer';
import { ITipoAl } from 'app/shared/model/tipo-al.model';
import { getEntities as getTipoAls } from 'app/entities/tipo-al/tipo-al.reducer';
import { IAlEnt } from 'app/shared/model/al-ent.model';
import { getEntity, updateEntity, createEntity, reset } from './al-ent.reducer';

export const AlEntUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tuppers = useAppSelector(state => state.tupper.entities);
  const donantes = useAppSelector(state => state.donante.entities);
  const tipoAls = useAppSelector(state => state.tipoAl.entities);
  const alEntEntity = useAppSelector(state => state.alEnt.entity);
  const loading = useAppSelector(state => state.alEnt.loading);
  const updating = useAppSelector(state => state.alEnt.updating);
  const updateSuccess = useAppSelector(state => state.alEnt.updateSuccess);

  const handleClose = () => {
    navigate('/al-ent');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getTuppers({}));
    dispatch(getDonantes({}));
    dispatch(getTipoAls({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.fechaYHoraEntrada = convertDateTimeToServer(values.fechaYHoraEntrada);
    values.fechaYHoraRecogida = convertDateTimeToServer(values.fechaYHoraRecogida);
    values.fechaYHoraPreparacion = convertDateTimeToServer(values.fechaYHoraPreparacion);

    const entity = {
      ...alEntEntity,
      ...values,
      tupper: tuppers.find(it => it.id.toString() === values.tupper.toString()),
      donante: donantes.find(it => it.id.toString() === values.donante.toString()),
      tipoAl: tipoAls.find(it => it.id.toString() === values.tipoAl.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          fechaYHoraEntrada: displayDefaultDateTime(),
          fechaYHoraRecogida: displayDefaultDateTime(),
          fechaYHoraPreparacion: displayDefaultDateTime(),
        }
      : {
          ...alEntEntity,
          fechaYHoraEntrada: convertDateTimeFromServer(alEntEntity.fechaYHoraEntrada),
          fechaYHoraRecogida: convertDateTimeFromServer(alEntEntity.fechaYHoraRecogida),
          fechaYHoraPreparacion: convertDateTimeFromServer(alEntEntity.fechaYHoraPreparacion),
          tupper: alEntEntity?.tupper?.id,
          donante: alEntEntity?.donante?.id,
          tipoAl: alEntEntity?.tipoAl?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="refoodTrazabilidadAppV3App.alEnt.home.createOrEditLabel" data-cy="AlEntCreateUpdateHeading">
            Crear o editar Al Ent
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="al-ent-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Peso"
                id="al-ent-peso"
                name="peso"
                data-cy="peso"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  validate: v => isNumber(v) || 'Este campo debe ser un número.',
                }}
              />
              <ValidatedField
                label="Fruta Y Verdura"
                id="al-ent-frutaYVerdura"
                name="frutaYVerdura"
                data-cy="frutaYVerdura"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Fecha Y Hora Entrada"
                id="al-ent-fechaYHoraEntrada"
                name="fechaYHoraEntrada"
                data-cy="fechaYHoraEntrada"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Fecha Y Hora Recogida"
                id="al-ent-fechaYHoraRecogida"
                name="fechaYHoraRecogida"
                data-cy="fechaYHoraRecogida"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Fecha Y Hora Preparacion"
                id="al-ent-fechaYHoraPreparacion"
                name="fechaYHoraPreparacion"
                data-cy="fechaYHoraPreparacion"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="al-ent-tupper" name="tupper" data-cy="tupper" label="Tupper" type="select">
                <option value="" key="0" />
                {tuppers
                  ? tuppers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="al-ent-donante" name="donante" data-cy="donante" label="Donante" type="select">
                <option value="" key="0" />
                {donantes
                  ? donantes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="al-ent-tipoAl" name="tipoAl" data-cy="tipoAl" label="Tipo Al" type="select">
                <option value="" key="0" />
                {tipoAls
                  ? tipoAls.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/al-ent" replace color="info">
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

export default AlEntUpdate;
