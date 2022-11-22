import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './registro.reducer';

export const RegistroDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const registroEntity = useAppSelector(state => state.registro.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="registroDetailsHeading">Registro</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{registroEntity.id}</dd>
          <dt>
            <span id="diaActividad">Dia Actividad</span>
          </dt>
          <dd>
            {registroEntity.diaActividad ? (
              <TextFormat value={registroEntity.diaActividad} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="ruta">Ruta</span>
          </dt>
          <dd>{registroEntity.ruta}</dd>
          <dt>Voluntario</dt>
          <dd>
            {registroEntity.voluntarios
              ? registroEntity.voluntarios.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {registroEntity.voluntarios && i === registroEntity.voluntarios.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Nucleo</dt>
          <dd>{registroEntity.nucleo ? registroEntity.nucleo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/registro" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/registro/${registroEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RegistroDetail;
