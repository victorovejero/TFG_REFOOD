import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
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
        <h2 data-cy="alimentoDeSalidaDetailsHeading">Alimento De Salida</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{alimentoDeSalidaEntity.id}</dd>
          <dt>
            <span id="fechaSalida">Fecha Salida</span>
          </dt>
          <dd>
            {alimentoDeSalidaEntity.fechaSalida ? (
              <TextFormat value={alimentoDeSalidaEntity.fechaSalida} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Tupper</dt>
          <dd>{alimentoDeSalidaEntity.tupper ? alimentoDeSalidaEntity.tupper.id : ''}</dd>
          <dt>Beneficiario</dt>
          <dd>{alimentoDeSalidaEntity.beneficiario ? alimentoDeSalidaEntity.beneficiario.id : ''}</dd>
          <dt>Alimento De Entrada</dt>
          <dd>{alimentoDeSalidaEntity.alimentoDeEntrada ? alimentoDeSalidaEntity.alimentoDeEntrada.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/alimento-de-salida" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/alimento-de-salida/${alimentoDeSalidaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AlimentoDeSalidaDetail;
