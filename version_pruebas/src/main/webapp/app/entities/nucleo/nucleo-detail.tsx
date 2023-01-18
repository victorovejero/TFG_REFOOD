import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './nucleo.reducer';

export const NucleoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const nucleoEntity = useAppSelector(state => state.nucleo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="nucleoDetailsHeading">Nucleo</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{nucleoEntity.id}</dd>
          <dt>
            <span id="idNucleo">Id Nucleo</span>
          </dt>
          <dd>{nucleoEntity.idNucleo}</dd>
          <dt>
            <span id="nombre">Nombre</span>
          </dt>
          <dd>{nucleoEntity.nombre}</dd>
          <dt>
            <span id="direccion">Direccion</span>
          </dt>
          <dd>{nucleoEntity.direccion}</dd>
          <dt>
            <span id="codigoPostal">Codigo Postal</span>
          </dt>
          <dd>{nucleoEntity.codigoPostal}</dd>
          <dt>
            <span id="provincia">Provincia</span>
          </dt>
          <dd>{nucleoEntity.provincia}</dd>
          <dt>
            <span id="responsable">Responsable</span>
          </dt>
          <dd>{nucleoEntity.responsable}</dd>
          <dt>
            <span id="telefono">Telefono</span>
          </dt>
          <dd>{nucleoEntity.telefono}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{nucleoEntity.email}</dd>
          <dt>
            <span id="activo">Activo</span>
          </dt>
          <dd>{nucleoEntity.activo ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/nucleo" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/nucleo/${nucleoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default NucleoDetail;
