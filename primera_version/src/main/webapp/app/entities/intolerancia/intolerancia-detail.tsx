import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './intolerancia.reducer';

export const IntoleranciaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const intoleranciaEntity = useAppSelector(state => state.intolerancia.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="intoleranciaDetailsHeading">
          <Translate contentKey="refoodTrazabilidadApp.intolerancia.detail.title">Intolerancia</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{intoleranciaEntity.id}</dd>
          <dt>
            <span id="nombreIntolerancia">
              <Translate contentKey="refoodTrazabilidadApp.intolerancia.nombreIntolerancia">Nombre Intolerancia</Translate>
            </span>
          </dt>
          <dd>{intoleranciaEntity.nombreIntolerancia}</dd>
          <dt>
            <Translate contentKey="refoodTrazabilidadApp.intolerancia.tipoDeAlimento">Tipo De Alimento</Translate>
          </dt>
          <dd>
            {intoleranciaEntity.tipoDeAlimentos
              ? intoleranciaEntity.tipoDeAlimentos.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {intoleranciaEntity.tipoDeAlimentos && i === intoleranciaEntity.tipoDeAlimentos.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="refoodTrazabilidadApp.intolerancia.beneficiario">Beneficiario</Translate>
          </dt>
          <dd>
            {intoleranciaEntity.beneficiarios
              ? intoleranciaEntity.beneficiarios.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {intoleranciaEntity.beneficiarios && i === intoleranciaEntity.beneficiarios.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/intolerancia" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/intolerancia/${intoleranciaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default IntoleranciaDetail;
