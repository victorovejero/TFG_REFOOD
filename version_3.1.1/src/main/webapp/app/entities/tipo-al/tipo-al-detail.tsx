import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tipo-al.reducer';

export const TipoAlDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tipoAlEntity = useAppSelector(state => state.tipoAl.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tipoAlDetailsHeading">Tipo Al</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{tipoAlEntity.id}</dd>
          <dt>
            <span id="nombreAlimento">Nombre Alimento</span>
          </dt>
          <dd>{tipoAlEntity.nombreAlimento}</dd>
          <dt>
            <span id="frutaYVerdura">Fruta Y Verdura</span>
          </dt>
          <dd>{tipoAlEntity.frutaYVerdura ? 'true' : 'false'}</dd>
          <dt>
            <span id="descripcion">Descripcion</span>
          </dt>
          <dd>{tipoAlEntity.descripcion}</dd>
          <dt>Intol</dt>
          <dd>
            {tipoAlEntity.intols
              ? tipoAlEntity.intols.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {tipoAlEntity.intols && i === tipoAlEntity.intols.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/tipo-al" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tipo-al/${tipoAlEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TipoAlDetail;
