import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tupper.reducer';

export const TupperDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tupperEntity = useAppSelector(state => state.tupper.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tupperDetailsHeading">Tupper</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{tupperEntity.id}</dd>
          <dt>
            <span id="peso">Peso</span>
          </dt>
          <dd>{tupperEntity.peso}</dd>
          <dt>
            <span id="productor">Productor</span>
          </dt>
          <dd>{tupperEntity.productor}</dd>
          <dt>
            <span id="modelo">Modelo</span>
          </dt>
          <dd>{tupperEntity.modelo}</dd>
          <dt>
            <span id="precio">Precio</span>
          </dt>
          <dd>{tupperEntity.precio}</dd>
          <dt>
            <span id="descripcion">Descripcion</span>
          </dt>
          <dd>{tupperEntity.descripcion}</dd>
        </dl>
        <Button tag={Link} to="/tupper" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tupper/${tupperEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TupperDetail;
