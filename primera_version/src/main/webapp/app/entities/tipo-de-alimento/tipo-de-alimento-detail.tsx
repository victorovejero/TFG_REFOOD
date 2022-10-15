import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tipo-de-alimento.reducer';

export const TipoDeAlimentoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tipoDeAlimentoEntity = useAppSelector(state => state.tipoDeAlimento.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tipoDeAlimentoDetailsHeading">
          <Translate contentKey="refoodTrazabilidadApp.tipoDeAlimento.detail.title">TipoDeAlimento</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tipoDeAlimentoEntity.id}</dd>
          <dt>
            <span id="nombreAlimento">
              <Translate contentKey="refoodTrazabilidadApp.tipoDeAlimento.nombreAlimento">Nombre Alimento</Translate>
            </span>
          </dt>
          <dd>{tipoDeAlimentoEntity.nombreAlimento}</dd>
        </dl>
        <Button tag={Link} to="/tipo-de-alimento" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tipo-de-alimento/${tipoDeAlimentoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TipoDeAlimentoDetail;
