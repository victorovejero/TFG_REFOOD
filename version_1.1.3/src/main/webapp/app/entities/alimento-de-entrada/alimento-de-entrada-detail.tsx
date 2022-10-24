import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
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
        <h2 data-cy="alimentoDeEntradaDetailsHeading">Alimento De Entrada</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{alimentoDeEntradaEntity.id}</dd>
          <dt>
            <span id="peso">Peso</span>
          </dt>
          <dd>{alimentoDeEntradaEntity.peso}</dd>
          <dt>
            <span id="fechaYHoraEntrada">Fecha Y Hora Entrada</span>
          </dt>
          <dd>
            {alimentoDeEntradaEntity.fechaYHoraEntrada ? (
              <TextFormat value={alimentoDeEntradaEntity.fechaYHoraEntrada} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fechaYHoraRecogida">Fecha Y Hora Recogida</span>
          </dt>
          <dd>
            {alimentoDeEntradaEntity.fechaYHoraRecogida ? (
              <TextFormat value={alimentoDeEntradaEntity.fechaYHoraRecogida} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fechaYHoraPreparacion">Fecha Y Hora Preparacion</span>
          </dt>
          <dd>
            {alimentoDeEntradaEntity.fechaYHoraPreparacion ? (
              <TextFormat value={alimentoDeEntradaEntity.fechaYHoraPreparacion} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Tipo De Alimento</dt>
          <dd>{alimentoDeEntradaEntity.tipoDeAlimento ? alimentoDeEntradaEntity.tipoDeAlimento.nombreAlimento : ''}</dd>
          <dt>Tupper</dt>
          <dd>{alimentoDeEntradaEntity.tupper ? alimentoDeEntradaEntity.tupper.modelo : ''}</dd>
          <dt>Donante</dt>
          <dd>{alimentoDeEntradaEntity.donante ? alimentoDeEntradaEntity.donante.nombre : ''}</dd>
        </dl>
        <Button tag={Link} to="/alimento-de-entrada" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/alimento-de-entrada/${alimentoDeEntradaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AlimentoDeEntradaDetail;
