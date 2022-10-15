import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
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
        <h2 data-cy="donanteDetailsHeading">
          <Translate contentKey="refoodTrazabilidadApp.donante.detail.title">Donante</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{donanteEntity.id}</dd>
          <dt>
            <span id="nombreDonante">
              <Translate contentKey="refoodTrazabilidadApp.donante.nombreDonante">Nombre Donante</Translate>
            </span>
          </dt>
          <dd>{donanteEntity.nombreDonante}</dd>
          <dt>
            <span id="tipoDonante">
              <Translate contentKey="refoodTrazabilidadApp.donante.tipoDonante">Tipo Donante</Translate>
            </span>
          </dt>
          <dd>{donanteEntity.tipoDonante}</dd>
          <dt>
            <span id="ruta">
              <Translate contentKey="refoodTrazabilidadApp.donante.ruta">Ruta</Translate>
            </span>
          </dt>
          <dd>{donanteEntity.ruta}</dd>
          <dt>
            <span id="direccionDonante">
              <Translate contentKey="refoodTrazabilidadApp.donante.direccionDonante">Direccion Donante</Translate>
            </span>
          </dt>
          <dd>{donanteEntity.direccionDonante}</dd>
          <dt>
            <span id="telefonoDonante">
              <Translate contentKey="refoodTrazabilidadApp.donante.telefonoDonante">Telefono Donante</Translate>
            </span>
          </dt>
          <dd>{donanteEntity.telefonoDonante}</dd>
          <dt>
            <span id="emailDonante">
              <Translate contentKey="refoodTrazabilidadApp.donante.emailDonante">Email Donante</Translate>
            </span>
          </dt>
          <dd>{donanteEntity.emailDonante}</dd>
          <dt>
            <span id="responsableDonante">
              <Translate contentKey="refoodTrazabilidadApp.donante.responsableDonante">Responsable Donante</Translate>
            </span>
          </dt>
          <dd>{donanteEntity.responsableDonante}</dd>
          <dt>
            <span id="fechaAlta">
              <Translate contentKey="refoodTrazabilidadApp.donante.fechaAlta">Fecha Alta</Translate>
            </span>
          </dt>
          <dd>
            {donanteEntity.fechaAlta ? <TextFormat value={donanteEntity.fechaAlta} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="fechaBaja">
              <Translate contentKey="refoodTrazabilidadApp.donante.fechaBaja">Fecha Baja</Translate>
            </span>
          </dt>
          <dd>
            {donanteEntity.fechaBaja ? <TextFormat value={donanteEntity.fechaBaja} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="comentarios">
              <Translate contentKey="refoodTrazabilidadApp.donante.comentarios">Comentarios</Translate>
            </span>
          </dt>
          <dd>{donanteEntity.comentarios}</dd>
          <dt>
            <Translate contentKey="refoodTrazabilidadApp.donante.nucleo">Nucleo</Translate>
          </dt>
          <dd>{donanteEntity.nucleo ? donanteEntity.nucleo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/donante" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/donante/${donanteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DonanteDetail;
