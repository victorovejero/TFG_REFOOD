import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './fruta-y-verdura.reducer';

export const FrutaYVerduraDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const frutaYVerduraEntity = useAppSelector(state => state.frutaYVerdura.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="frutaYVerduraDetailsHeading">Fruta Y Verdura</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{frutaYVerduraEntity.id}</dd>
          <dt>
            <span id="nombreAlimento">Nombre Alimento</span>
          </dt>
          <dd>{frutaYVerduraEntity.nombreAlimento}</dd>
        </dl>
        <Button tag={Link} to="/fruta-y-verdura" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/fruta-y-verdura/${frutaYVerduraEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FrutaYVerduraDetail;
