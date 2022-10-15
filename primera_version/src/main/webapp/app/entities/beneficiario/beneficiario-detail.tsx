import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './beneficiario.reducer';

export const BeneficiarioDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const beneficiarioEntity = useAppSelector(state => state.beneficiario.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="beneficiarioDetailsHeading">
          <Translate contentKey="refoodTrazabilidadApp.beneficiario.detail.title">Beneficiario</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{beneficiarioEntity.id}</dd>
          <dt>
            <span id="nombreBeneficiario">
              <Translate contentKey="refoodTrazabilidadApp.beneficiario.nombreBeneficiario">Nombre Beneficiario</Translate>
            </span>
          </dt>
          <dd>{beneficiarioEntity.nombreBeneficiario}</dd>
          <dt>
            <span id="numPersonas">
              <Translate contentKey="refoodTrazabilidadApp.beneficiario.numPersonas">Num Personas</Translate>
            </span>
          </dt>
          <dd>{beneficiarioEntity.numPersonas}</dd>
          <dt>
            <span id="numNinios">
              <Translate contentKey="refoodTrazabilidadApp.beneficiario.numNinios">Num Ninios</Translate>
            </span>
          </dt>
          <dd>{beneficiarioEntity.numNinios}</dd>
          <dt>
            <span id="idDual">
              <Translate contentKey="refoodTrazabilidadApp.beneficiario.idDual">Id Dual</Translate>
            </span>
          </dt>
          <dd>{beneficiarioEntity.idDual}</dd>
          <dt>
            <Translate contentKey="refoodTrazabilidadApp.beneficiario.nucleo">Nucleo</Translate>
          </dt>
          <dd>{beneficiarioEntity.nucleo ? beneficiarioEntity.nucleo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/beneficiario" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/beneficiario/${beneficiarioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BeneficiarioDetail;
