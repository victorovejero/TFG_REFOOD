import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './alimento-de-salida.reducer';

export const AlimentoDeSalidaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const alimentoDeSalidaEntity = useAppSelector(state => state.alimentoDeSalida.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="alimentoDeSalidaDetailsHeading">
          <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.detail.title">AlimentoDeSalida</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{alimentoDeSalidaEntity.id}</dd>
          <dt>
            <span id="peso">
              <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.peso">Peso</Translate>
            </span>
          </dt>
          <dd>{alimentoDeSalidaEntity.peso}</dd>
          <dt>
            <span id="fechaEntrada">
              <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.fechaEntrada">Fecha Entrada</Translate>
            </span>
          </dt>
          <dd>
            {alimentoDeSalidaEntity.fechaEntrada ? (
              <TextFormat value={alimentoDeSalidaEntity.fechaEntrada} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fechaSalida">
              <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.fechaSalida">Fecha Salida</Translate>
            </span>
          </dt>
          <dd>
            {alimentoDeSalidaEntity.fechaSalida ? (
              <TextFormat value={alimentoDeSalidaEntity.fechaSalida} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fechaYHoraLog">
              <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.fechaYHoraLog">Fecha Y Hora Log</Translate>
            </span>
          </dt>
          <dd>
            {alimentoDeSalidaEntity.fechaYHoraLog ? (
              <TextFormat value={alimentoDeSalidaEntity.fechaYHoraLog} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fechaYHoraPreparacion">
              <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.fechaYHoraPreparacion">Fecha Y Hora Preparacion</Translate>
            </span>
          </dt>
          <dd>
            {alimentoDeSalidaEntity.fechaYHoraPreparacion ? (
              <TextFormat value={alimentoDeSalidaEntity.fechaYHoraPreparacion} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fechaYHoraRecogida">
              <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.fechaYHoraRecogida">Fecha Y Hora Recogida</Translate>
            </span>
          </dt>
          <dd>
            {alimentoDeSalidaEntity.fechaYHoraRecogida ? (
              <TextFormat value={alimentoDeSalidaEntity.fechaYHoraRecogida} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.tupper">Tupper</Translate>
          </dt>
          <dd>{alimentoDeSalidaEntity.tupper ? alimentoDeSalidaEntity.tupper.id : ''}</dd>
          <dt>
            <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.beneficiario">Beneficiario</Translate>
          </dt>
          <dd>{alimentoDeSalidaEntity.beneficiario ? alimentoDeSalidaEntity.beneficiario.id : ''}</dd>
          <dt>
            <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.alimentoDeEntrada">Alimento De Entrada</Translate>
          </dt>
          <dd>{alimentoDeSalidaEntity.alimentoDeEntrada ? alimentoDeSalidaEntity.alimentoDeEntrada.id : ''}</dd>
          <dt>
            <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.tipoDeAlimento">Tipo De Alimento</Translate>
          </dt>
          <dd>{alimentoDeSalidaEntity.tipoDeAlimento ? alimentoDeSalidaEntity.tipoDeAlimento.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/alimento-de-salida" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/alimento-de-salida/${alimentoDeSalidaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AlimentoDeSalidaDetail;
