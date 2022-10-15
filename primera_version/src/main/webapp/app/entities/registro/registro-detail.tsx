import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
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
        <h2 data-cy="registroDetailsHeading">
          <Translate contentKey="refoodTrazabilidadApp.registro.detail.title">Registro</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{registroEntity.id}</dd>
          <dt>
            <span id="diaActividad">
              <Translate contentKey="refoodTrazabilidadApp.registro.diaActividad">Dia Actividad</Translate>
            </span>
          </dt>
          <dd>
            {registroEntity.diaActividad ? (
              <TextFormat value={registroEntity.diaActividad} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="ruta">
              <Translate contentKey="refoodTrazabilidadApp.registro.ruta">Ruta</Translate>
            </span>
          </dt>
          <dd>{registroEntity.ruta}</dd>
          <dt>
            <Translate contentKey="refoodTrazabilidadApp.registro.voluntario">Voluntario</Translate>
          </dt>
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
          <dt>
            <Translate contentKey="refoodTrazabilidadApp.registro.nucleo">Nucleo</Translate>
          </dt>
          <dd>{registroEntity.nucleo ? registroEntity.nucleo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/registro" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/registro/${registroEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RegistroDetail;
