import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
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
        <h2 data-cy="tupperDetailsHeading">
          <Translate contentKey="refoodTrazabilidadApp.tupper.detail.title">Tupper</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tupperEntity.id}</dd>
          <dt>
            <span id="peso">
              <Translate contentKey="refoodTrazabilidadApp.tupper.peso">Peso</Translate>
            </span>
          </dt>
          <dd>{tupperEntity.peso}</dd>
          <dt>
            <span id="productor">
              <Translate contentKey="refoodTrazabilidadApp.tupper.productor">Productor</Translate>
            </span>
          </dt>
          <dd>{tupperEntity.productor}</dd>
          <dt>
            <span id="modelo">
              <Translate contentKey="refoodTrazabilidadApp.tupper.modelo">Modelo</Translate>
            </span>
          </dt>
          <dd>{tupperEntity.modelo}</dd>
          <dt>
            <span id="precio">
              <Translate contentKey="refoodTrazabilidadApp.tupper.precio">Precio</Translate>
            </span>
          </dt>
          <dd>{tupperEntity.precio}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="refoodTrazabilidadApp.tupper.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{tupperEntity.descripcion}</dd>
        </dl>
        <Button tag={Link} to="/tupper" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tupper/${tupperEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TupperDetail;
