import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITupper } from 'app/shared/model/tupper.model';
import { getEntities as getTuppers } from 'app/entities/tupper/tupper.reducer';
import { IBeneficiario } from 'app/shared/model/beneficiario.model';
import { getEntities as getBeneficiarios } from 'app/entities/beneficiario/beneficiario.reducer';
import { IAlimentoDeEntrada } from 'app/shared/model/alimento-de-entrada.model';
import { getEntities as getAlimentoDeEntradas } from 'app/entities/alimento-de-entrada/alimento-de-entrada.reducer';
import { ITipoDeAlimento } from 'app/shared/model/tipo-de-alimento.model';
import { getEntities as getTipoDeAlimentos } from 'app/entities/tipo-de-alimento/tipo-de-alimento.reducer';
import { IAlimentoDeSalida } from 'app/shared/model/alimento-de-salida.model';
import { getEntity, updateEntity, createEntity, reset } from './alimento-de-salida.reducer';

export const AlimentoDeSalidaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tuppers = useAppSelector(state => state.tupper.entities);
  const beneficiarios = useAppSelector(state => state.beneficiario.entities);
  const alimentoDeEntradas = useAppSelector(state => state.alimentoDeEntrada.entities);
  const tipoDeAlimentos = useAppSelector(state => state.tipoDeAlimento.entities);
  const alimentoDeSalidaEntity = useAppSelector(state => state.alimentoDeSalida.entity);
  const loading = useAppSelector(state => state.alimentoDeSalida.loading);
  const updating = useAppSelector(state => state.alimentoDeSalida.updating);
  const updateSuccess = useAppSelector(state => state.alimentoDeSalida.updateSuccess);

  const handleClose = () => {
    navigate('/alimento-de-salida' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTuppers({}));
    dispatch(getBeneficiarios({}));
    dispatch(getAlimentoDeEntradas({}));
    dispatch(getTipoDeAlimentos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.fechaYHoraLog = convertDateTimeToServer(values.fechaYHoraLog);
    values.fechaYHoraPreparacion = convertDateTimeToServer(values.fechaYHoraPreparacion);
    values.fechaYHoraRecogida = convertDateTimeToServer(values.fechaYHoraRecogida);

    const entity = {
      ...alimentoDeSalidaEntity,
      ...values,
      tupper: tuppers.find(it => it.id.toString() === values.tupper.toString()),
      beneficiario: beneficiarios.find(it => it.id.toString() === values.beneficiario.toString()),
      alimentoDeEntrada: alimentoDeEntradas.find(it => it.id.toString() === values.alimentoDeEntrada.toString()),
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
          fechaYHoraLog: displayDefaultDateTime(),
          fechaYHoraPreparacion: displayDefaultDateTime(),
          fechaYHoraRecogida: displayDefaultDateTime(),
        }
      : {
          ...alimentoDeSalidaEntity,
          fechaYHoraLog: convertDateTimeFromServer(alimentoDeSalidaEntity.fechaYHoraLog),
          fechaYHoraPreparacion: convertDateTimeFromServer(alimentoDeSalidaEntity.fechaYHoraPreparacion),
          fechaYHoraRecogida: convertDateTimeFromServer(alimentoDeSalidaEntity.fechaYHoraRecogida),
          tupper: alimentoDeSalidaEntity?.tupper?.id,
          beneficiario: alimentoDeSalidaEntity?.beneficiario?.id,
          alimentoDeEntrada: alimentoDeSalidaEntity?.alimentoDeEntrada?.id,
          tipoDeAlimento: alimentoDeSalidaEntity?.tipoDeAlimento?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="refoodTrazabilidadApp.alimentoDeSalida.home.createOrEditLabel" data-cy="AlimentoDeSalidaCreateUpdateHeading">
            <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.home.createOrEditLabel">
              Create or edit a AlimentoDeSalida
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
                  id="alimento-de-salida-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('refoodTrazabilidadApp.alimentoDeSalida.peso')}
                id="alimento-de-salida-peso"
                name="peso"
                data-cy="peso"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.alimentoDeSalida.fechaEntrada')}
                id="alimento-de-salida-fechaEntrada"
                name="fechaEntrada"
                data-cy="fechaEntrada"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.alimentoDeSalida.fechaSalida')}
                id="alimento-de-salida-fechaSalida"
                name="fechaSalida"
                data-cy="fechaSalida"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.alimentoDeSalida.fechaYHoraLog')}
                id="alimento-de-salida-fechaYHoraLog"
                name="fechaYHoraLog"
                data-cy="fechaYHoraLog"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.alimentoDeSalida.fechaYHoraPreparacion')}
                id="alimento-de-salida-fechaYHoraPreparacion"
                name="fechaYHoraPreparacion"
                data-cy="fechaYHoraPreparacion"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('refoodTrazabilidadApp.alimentoDeSalida.fechaYHoraRecogida')}
                id="alimento-de-salida-fechaYHoraRecogida"
                name="fechaYHoraRecogida"
                data-cy="fechaYHoraRecogida"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="alimento-de-salida-tupper"
                name="tupper"
                data-cy="tupper"
                label={translate('refoodTrazabilidadApp.alimentoDeSalida.tupper')}
                type="select"
              >
                <option value="" key="0" />
                {tuppers
                  ? tuppers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="alimento-de-salida-beneficiario"
                name="beneficiario"
                data-cy="beneficiario"
                label={translate('refoodTrazabilidadApp.alimentoDeSalida.beneficiario')}
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
              <ValidatedField
                id="alimento-de-salida-alimentoDeEntrada"
                name="alimentoDeEntrada"
                data-cy="alimentoDeEntrada"
                label={translate('refoodTrazabilidadApp.alimentoDeSalida.alimentoDeEntrada')}
                type="select"
              >
                <option value="" key="0" />
                {alimentoDeEntradas
                  ? alimentoDeEntradas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="alimento-de-salida-tipoDeAlimento"
                name="tipoDeAlimento"
                data-cy="tipoDeAlimento"
                label={translate('refoodTrazabilidadApp.alimentoDeSalida.tipoDeAlimento')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/alimento-de-salida" replace color="info">
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

export default AlimentoDeSalidaUpdate;
