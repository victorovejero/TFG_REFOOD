import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITipoDeAlimento } from 'app/shared/model/tipo-de-alimento.model';
import { getEntities as getTipoDeAlimentos } from 'app/entities/tipo-de-alimento/tipo-de-alimento.reducer';
import { IDonante } from 'app/shared/model/donante.model';
import { getEntities as getDonantes } from 'app/entities/donante/donante.reducer';
import { IAlimentoDeEntrada } from 'app/shared/model/alimento-de-entrada.model';
import { getEntity, updateEntity, createEntity, reset } from './alimento-de-entrada.reducer';

export const AlimentoDeEntradaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tipoDeAlimentos = useAppSelector(state => state.tipoDeAlimento.entities);
  const donantes = useAppSelector(state => state.donante.entities);
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

    dispatch(getTipoDeAlimentos({}));
    dispatch(getDonantes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.fechaYHoraLog = convertDateTimeToServer(values.fechaYHoraLog);
    values.fechaYHoraRecogida = convertDateTimeToServer(values.fechaYHoraRecogida);
    values.fechaYHoraPreparacion = convertDateTimeToServer(values.fechaYHoraPreparacion);

    const entity = {
      ...alimentoDeEntradaEntity,
      ...values,
      alimentoDeEntrada: tipoDeAlimentos.find(it => it.id.toString() === values.alimentoDeEntrada.toString()),
      tipoDeAlimento: tipoDeAlimentos.find(it => it.id.toString() === values.tipoDeAlimento.toString()),
      donante: donantes.find(it => it.id.toString() === values.donante.toString()),
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
          fechaYHoraLog: displayDefaultDateTime(),
          fechaYHoraRecogida: displayDefaultDateTime(),
          fechaYHoraPreparacion: displayDefaultDateTime(),
        }
      : {
          ...alimentoDeEntradaEntity,
          fechaYHoraLog: convertDateTimeFromServer(alimentoDeEntradaEntity.fechaYHoraLog),
          fechaYHoraRecogida: convertDateTimeFromServer(alimentoDeEntradaEntity.fechaYHoraRecogida),
          fechaYHoraPreparacion: convertDateTimeFromServer(alimentoDeEntradaEntity.fechaYHoraPreparacion),
          alimentoDeEntrada: alimentoDeEntradaEntity?.alimentoDeEntrada?.id,
          donante: alimentoDeEntradaEntity?.donante?.id,
          tipoDeAlimento: alimentoDeEntradaEntity?.tipoDeAlimento?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="refoodTrazabilidadApp.alimentoDeEntrada.home.createOrEditLabel" data-cy="AlimentoDeEntradaCreateUpdateHeading">
            <Translate contentKey="refoodTrazabilidadApp.alimentoDeEntrada.home.createOrEditLabel">
              Create or edit a AlimentoDeEntrada
            </Translate>
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
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="alimento-de-entrada-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('refoodTrazabilidadApp.alimentoDeEntrada.peso')}
                id="alimento-de-entrada-peso"
                name="peso"
                data-cy="peso"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.alimentoDeEntrada.fechaEntrada')}
                id="alimento-de-entrada-fechaEntrada"
                name="fechaEntrada"
                data-cy="fechaEntrada"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.alimentoDeEntrada.fechaYHoraLog')}
                id="alimento-de-entrada-fechaYHoraLog"
                name="fechaYHoraLog"
                data-cy="fechaYHoraLog"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.alimentoDeEntrada.fechaYHoraRecogida')}
                id="alimento-de-entrada-fechaYHoraRecogida"
                name="fechaYHoraRecogida"
                data-cy="fechaYHoraRecogida"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.alimentoDeEntrada.fechaYHoraPreparacion')}
                id="alimento-de-entrada-fechaYHoraPreparacion"
                name="fechaYHoraPreparacion"
                data-cy="fechaYHoraPreparacion"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="alimento-de-entrada-alimentoDeEntrada"
                name="alimentoDeEntrada"
                data-cy="alimentoDeEntrada"
                label={translate('refoodTrazabilidadApp.alimentoDeEntrada.alimentoDeEntrada')}
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
              <ValidatedField
                id="alimento-de-entrada-donante"
                name="donante"
                data-cy="donante"
                label={translate('refoodTrazabilidadApp.alimentoDeEntrada.donante')}
                type="select"
              >
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
                label={translate('refoodTrazabilidadApp.alimentoDeEntrada.tipoDeAlimento')}
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
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AlimentoDeEntradaUpdate;
