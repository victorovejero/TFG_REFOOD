import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './donante.reducer';

export const DonanteDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const donanteEntity = useAppSelector(state => state.donante.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="donanteDetailsHeading">Donante</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{donanteEntity.id}</dd>
          <dt>
            <span id="idDonante">Id Donante</span>
          </dt>
          <dd>{donanteEntity.idDonante}</dd>
          <dt>
            <span id="nombre">Nombre</span>
          </dt>
          <dd>{donanteEntity.nombre}</dd>
          <dt>
            <span id="tipo">Tipo</span>
          </dt>
          <dd>{donanteEntity.tipo}</dd>
          <dt>
            <span id="ruta">Ruta</span>
          </dt>
          <dd>{donanteEntity.ruta}</dd>
          <dt>
            <span id="direccion">Direccion</span>
          </dt>
          <dd>{donanteEntity.direccion}</dd>
          <dt>
            <span id="telefono">Telefono</span>
          </dt>
          <dd>{donanteEntity.telefono}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{donanteEntity.email}</dd>
          <dt>
            <span id="responsable">Responsable</span>
          </dt>
          <dd>{donanteEntity.responsable}</dd>
          <dt>
            <span id="fechaAlta">Fecha Alta</span>
          </dt>
          <dd>
            {donanteEntity.fechaAlta ? <TextFormat value={donanteEntity.fechaAlta} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="fechaBaja">Fecha Baja</span>
          </dt>
          <dd>
            {donanteEntity.fechaBaja ? <TextFormat value={donanteEntity.fechaBaja} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="comentarios">Comentarios</span>
          </dt>
          <dd>{donanteEntity.comentarios}</dd>
          <dt>
            <span id="activo">Activo</span>
          </dt>
          <dd>{donanteEntity.activo ? 'true' : 'false'}</dd>
          <dt>Nucleo</dt>
          <dd>{donanteEntity.nucleo ? donanteEntity.nucleo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/donante" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/donante/${donanteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DonanteDetail;
