import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFrutaYVerdura } from 'app/shared/model/fruta-y-verdura.model';
import { getEntities as getFrutaYVerduras } from 'app/entities/fruta-y-verdura/fruta-y-verdura.reducer';
import { ITupper } from 'app/shared/model/tupper.model';
import { getEntities as getTuppers } from 'app/entities/tupper/tupper.reducer';
import { IDonante } from 'app/shared/model/donante.model';
import { getEntities as getDonantes } from 'app/entities/donante/donante.reducer';
import { ITipoDeAlimento } from 'app/shared/model/tipo-de-alimento.model';
import { getEntities as getTipoDeAlimentos } from 'app/entities/tipo-de-alimento/tipo-de-alimento.reducer';
import { IAlimentoDeEntrada } from 'app/shared/model/alimento-de-entrada.model';
import { getEntity, updateEntity, createEntity, reset } from './alimento-de-entrada.reducer';

export const AlimentoDeEntradaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const frutaYVerduras = useAppSelector(state => state.frutaYVerdura.entities);
  const tuppers = useAppSelector(state => state.tupper.entities);
  const donantes = useAppSelector(state => state.donante.entities);
  const tipoDeAlimentos = useAppSelector(state => state.tipoDeAlimento.entities);
  const alimentoDeEntradaEntity = useAppSelector(state => state.alimentoDeEntrada.entity);
  const loading = useAppSelector(state => state.alimentoDeEntrada.loading);
  const updating = useAppSelector(state => state.alimentoDeEntrada.updating);
  const updateSuccess = useAppSelector(state => state.alimentoDeEntrada.updateSuccess);

  const handleClose = () => {
    navigate('/alimento-de-entrada' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getFrutaYVerduras({}));
    dispatch(getTuppers({}));
    dispatch(getDonantes({}));
    dispatch(getTipoDeAlimentos({}));
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
      ...alimentoDeEntradaEntity,
      ...values,
      frutaYVerduras: mapIdList(values.frutaYVerduras),
      tupper: tuppers.find(it => it.id.toString() === values.tupper.toString()),
      donante: donantes.find(it => it.id.toString() === values.donante.toString()),
      tipoDeAlimento: tipoDeAlimentos.find(it => it.id.toString() === values.tipoDeAlimento.toString()),
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
          ...alimentoDeEntradaEntity,
          fechaYHoraEntrada: convertDateTimeFromServer(alimentoDeEntradaEntity.fechaYHoraEntrada),
          fechaYHoraRecogida: convertDateTimeFromServer(alimentoDeEntradaEntity.fechaYHoraRecogida),
          fechaYHoraPreparacion: convertDateTimeFromServer(alimentoDeEntradaEntity.fechaYHoraPreparacion),
          frutaYVerduras: alimentoDeEntradaEntity?.frutaYVerduras?.map(e => e.id.toString()),
          tupper: alimentoDeEntradaEntity?.tupper?.id,
          donante: alimentoDeEntradaEntity?.donante?.id,
          tipoDeAlimento: alimentoDeEntradaEntity?.tipoDeAlimento?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="refoodTrazabilidadApp.alimentoDeEntrada.home.createOrEditLabel" data-cy="AlimentoDeEntradaCreateUpdateHeading">
            Crear o editar Alimento De Entrada
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
                <ValidatedField name="id" required readOnly id="alimento-de-entrada-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Peso"
                id="alimento-de-entrada-peso"
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
                id="alimento-de-entrada-frutaYVerdura"
                name="frutaYVerdura"
                data-cy="frutaYVerdura"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Fecha Y Hora Entrada"
                id="alimento-de-entrada-fechaYHoraEntrada"
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
                id="alimento-de-entrada-fechaYHoraRecogida"
                name="fechaYHoraRecogida"
                data-cy="fechaYHoraRecogida"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Fecha Y Hora Preparacion"
                id="alimento-de-entrada-fechaYHoraPreparacion"
                name="fechaYHoraPreparacion"
                data-cy="fechaYHoraPreparacion"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Fruta Y Verdura"
                id="alimento-de-entrada-frutaYVerdura"
                data-cy="frutaYVerdura"
                type="select"
                multiple
                name="frutaYVerduras"
              >
                <option value="" key="0" />
                {frutaYVerduras
                  ? frutaYVerduras.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="alimento-de-entrada-tupper" name="tupper" data-cy="tupper" label="Tupper" type="select">
                <option value="" key="0" />
                {tuppers
                  ? tuppers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="alimento-de-entrada-donante" name="donante" data-cy="donante" label="Donante" type="select">
                <option value="" key="0" />
                {donantes
                  ? donantes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="alimento-de-entrada-tipoDeAlimento"
                name="tipoDeAlimento"
                data-cy="tipoDeAlimento"
                label="Tipo De Alimento"
                type="select"
              >
                <option value="" key="0" />
                {tipoDeAlimentos
                  ? tipoDeAlimentos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/alimento-de-entrada" replace color="info">
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

export default AlimentoDeEntradaUpdate;
