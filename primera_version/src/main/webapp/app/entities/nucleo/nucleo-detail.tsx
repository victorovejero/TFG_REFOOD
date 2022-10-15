import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
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
        <h2 data-cy="nucleoDetailsHeading">
          <Translate contentKey="refoodTrazabilidadApp.nucleo.detail.title">Nucleo</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{nucleoEntity.id}</dd>
          <dt>
            <span id="nombreNucleo">
              <Translate contentKey="refoodTrazabilidadApp.nucleo.nombreNucleo">Nombre Nucleo</Translate>
            </span>
          </dt>
          <dd>{nucleoEntity.nombreNucleo}</dd>
          <dt>
            <span id="direccionNucleo">
              <Translate contentKey="refoodTrazabilidadApp.nucleo.direccionNucleo">Direccion Nucleo</Translate>
            </span>
          </dt>
          <dd>{nucleoEntity.direccionNucleo}</dd>
          <dt>
            <span id="provinciaNucleo">
              <Translate contentKey="refoodTrazabilidadApp.nucleo.provinciaNucleo">Provincia Nucleo</Translate>
            </span>
          </dt>
          <dd>{nucleoEntity.provinciaNucleo}</dd>
          <dt>
            <span id="responsableNucleo">
              <Translate contentKey="refoodTrazabilidadApp.nucleo.responsableNucleo">Responsable Nucleo</Translate>
            </span>
          </dt>
          <dd>{nucleoEntity.responsableNucleo}</dd>
          <dt>
            <span id="telefonoNucleo">
              <Translate contentKey="refoodTrazabilidadApp.nucleo.telefonoNucleo">Telefono Nucleo</Translate>
            </span>
          </dt>
          <dd>{nucleoEntity.telefonoNucleo}</dd>
          <dt>
            <span id="emailNucleo">
              <Translate contentKey="refoodTrazabilidadApp.nucleo.emailNucleo">Email Nucleo</Translate>
            </span>
          </dt>
          <dd>{nucleoEntity.emailNucleo}</dd>
          <dt>
            <span id="numeroRutas">
              <Translate contentKey="refoodTrazabilidadApp.nucleo.numeroRutas">Numero Rutas</Translate>
            </span>
          </dt>
          <dd>{nucleoEntity.numeroRutas}</dd>
        </dl>
        <Button tag={Link} to="/nucleo" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/nucleo/${nucleoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NucleoDetail;
