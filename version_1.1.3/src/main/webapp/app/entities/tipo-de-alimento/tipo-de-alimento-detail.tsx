import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
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
        <h2 data-cy="tipoDeAlimentoDetailsHeading">Tipo De Alimento</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{tipoDeAlimentoEntity.id}</dd>
          <dt>
            <span id="nombreAlimento">Nombre Alimento</span>
          </dt>
          <dd>{tipoDeAlimentoEntity.nombreAlimento}</dd>
          <dt>Intolerancia</dt>
          <dd>
            {tipoDeAlimentoEntity.intolerancias
              ? tipoDeAlimentoEntity.intolerancias.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {tipoDeAlimentoEntity.intolerancias && i === tipoDeAlimentoEntity.intolerancias.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/tipo-de-alimento" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tipo-de-alimento/${tipoDeAlimentoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TipoDeAlimentoDetail;
