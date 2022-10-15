import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './alimento-de-entrada.reducer';

export const AlimentoDeEntradaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const alimentoDeEntradaEntity = useAppSelector(state => state.alimentoDeEntrada.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="alimentoDeEntradaDetailsHeading">
          <Translate contentKey="refoodTrazabilidadApp.alimentoDeEntrada.detail.title">AlimentoDeEntrada</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{alimentoDeEntradaEntity.id}</dd>
          <dt>
            <span id="peso">
              <Translate contentKey="refoodTrazabilidadApp.alimentoDeEntrada.peso">Peso</Translate>
            </span>
          </dt>
          <dd>{alimentoDeEntradaEntity.peso}</dd>
          <dt>
            <span id="fechaEntrada">
              <Translate contentKey="refoodTrazabilidadApp.alimentoDeEntrada.fechaEntrada">Fecha Entrada</Translate>
            </span>
          </dt>
          <dd>
            {alimentoDeEntradaEntity.fechaEntrada ? (
              <TextFormat value={alimentoDeEntradaEntity.fechaEntrada} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fechaYHoraLog">
              <Translate contentKey="refoodTrazabilidadApp.alimentoDeEntrada.fechaYHoraLog">Fecha Y Hora Log</Translate>
            </span>
          </dt>
          <dd>
            {alimentoDeEntradaEntity.fechaYHoraLog ? (
              <TextFormat value={alimentoDeEntradaEntity.fechaYHoraLog} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fechaYHoraRecogida">
              <Translate contentKey="refoodTrazabilidadApp.alimentoDeEntrada.fechaYHoraRecogida">Fecha Y Hora Recogida</Translate>
            </span>
          </dt>
          <dd>
            {alimentoDeEntradaEntity.fechaYHoraRecogida ? (
              <TextFormat value={alimentoDeEntradaEntity.fechaYHoraRecogida} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fechaYHoraPreparacion">
              <Translate contentKey="refoodTrazabilidadApp.alimentoDeEntrada.fechaYHoraPreparacion">Fecha Y Hora Preparacion</Translate>
            </span>
          </dt>
          <dd>
            {alimentoDeEntradaEntity.fechaYHoraPreparacion ? (
              <TextFormat value={alimentoDeEntradaEntity.fechaYHoraPreparacion} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="refoodTrazabilidadApp.alimentoDeEntrada.alimentoDeEntrada">Alimento De Entrada</Translate>
          </dt>
          <dd>{alimentoDeEntradaEntity.alimentoDeEntrada ? alimentoDeEntradaEntity.alimentoDeEntrada.id : ''}</dd>
          <dt>
            <Translate contentKey="refoodTrazabilidadApp.alimentoDeEntrada.donante">Donante</Translate>
          </dt>
          <dd>{alimentoDeEntradaEntity.donante ? alimentoDeEntradaEntity.donante.id : ''}</dd>
          <dt>
            <Translate contentKey="refoodTrazabilidadApp.alimentoDeEntrada.tipoDeAlimento">Tipo De Alimento</Translate>
          </dt>
          <dd>{alimentoDeEntradaEntity.tipoDeAlimento ? alimentoDeEntradaEntity.tipoDeAlimento.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/alimento-de-entrada" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/alimento-de-entrada/${alimentoDeEntradaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AlimentoDeEntradaDetail;
