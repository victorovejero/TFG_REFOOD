import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './al-ent.reducer';

export const AlEntDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const alEntEntity = useAppSelector(state => state.alEnt.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="alEntDetailsHeading">Al Ent</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{alEntEntity.id}</dd>
          <dt>
            <span id="peso">Peso</span>
          </dt>
          <dd>{alEntEntity.peso}</dd>
          <dt>
            <span id="frutaYVerdura">Fruta Y Verdura</span>
          </dt>
          <dd>{alEntEntity.frutaYVerdura ? 'true' : 'false'}</dd>
          <dt>
            <span id="fechaYHoraEntrada">Fecha Y Hora Entrada</span>
          </dt>
          <dd>
            {alEntEntity.fechaYHoraEntrada ? (
              <TextFormat value={alEntEntity.fechaYHoraEntrada} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fechaYHoraRecogida">Fecha Y Hora Recogida</span>
          </dt>
          <dd>
            {alEntEntity.fechaYHoraRecogida ? (
              <TextFormat value={alEntEntity.fechaYHoraRecogida} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fechaYHoraPreparacion">Fecha Y Hora Preparacion</span>
          </dt>
          <dd>
            {alEntEntity.fechaYHoraPreparacion ? (
              <TextFormat value={alEntEntity.fechaYHoraPreparacion} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Tupper</dt>
          <dd>{alEntEntity.tupper ? alEntEntity.tupper.id : ''}</dd>
          <dt>Donante</dt>
          <dd>{alEntEntity.donante ? alEntEntity.donante.id : ''}</dd>
          <dt>Tipo Al</dt>
          <dd>{alEntEntity.tipoAl ? alEntEntity.tipoAl.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/al-ent" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/al-ent/${alEntEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AlEntDetail;
