import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
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
        <h2 data-cy="beneficiarioDetailsHeading">Beneficiario</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{beneficiarioEntity.id}</dd>
          <dt>
            <span id="idBeneficiario">Id Beneficiario</span>
          </dt>
          <dd>{beneficiarioEntity.idBeneficiario}</dd>
          <dt>
            <span id="nombreRepresentante">Nombre Representante</span>
          </dt>
          <dd>{beneficiarioEntity.nombreRepresentante}</dd>
          <dt>
            <span id="primerApellidoRepresentante">Primer Apellido Representante</span>
          </dt>
          <dd>{beneficiarioEntity.primerApellidoRepresentante}</dd>
          <dt>
            <span id="segundoApellidoRepresentante">Segundo Apellido Representante</span>
          </dt>
          <dd>{beneficiarioEntity.segundoApellidoRepresentante}</dd>
          <dt>
            <span id="numeroPersonas">Numero Personas</span>
          </dt>
          <dd>{beneficiarioEntity.numeroPersonas}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{beneficiarioEntity.email}</dd>
          <dt>
            <span id="telefono">Telefono</span>
          </dt>
          <dd>{beneficiarioEntity.telefono}</dd>
          <dt>
            <span id="telefonoSecundario">Telefono Secundario</span>
          </dt>
          <dd>{beneficiarioEntity.telefonoSecundario}</dd>
          <dt>
            <span id="direccion">Direccion</span>
          </dt>
          <dd>{beneficiarioEntity.direccion}</dd>
          <dt>
            <span id="codigoPostal">Codigo Postal</span>
          </dt>
          <dd>{beneficiarioEntity.codigoPostal}</dd>
          <dt>
            <span id="fechaAlta">Fecha Alta</span>
          </dt>
          <dd>
            {beneficiarioEntity.fechaAlta ? (
              <TextFormat value={beneficiarioEntity.fechaAlta} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fechaBaja">Fecha Baja</span>
          </dt>
          <dd>
            {beneficiarioEntity.fechaBaja ? (
              <TextFormat value={beneficiarioEntity.fechaBaja} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="numeroNinios">Numero Ninios</span>
          </dt>
          <dd>{beneficiarioEntity.numeroNinios}</dd>
          <dt>
            <span id="idDual">Id Dual</span>
          </dt>
          <dd>{beneficiarioEntity.idDual}</dd>
          <dt>
            <span id="activo">Activo</span>
          </dt>
          <dd>{beneficiarioEntity.activo ? 'true' : 'false'}</dd>
          <dt>Intolerancia</dt>
          <dd>
            {beneficiarioEntity.intolerancias
              ? beneficiarioEntity.intolerancias.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {beneficiarioEntity.intolerancias && i === beneficiarioEntity.intolerancias.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Nucleo</dt>
          <dd>{beneficiarioEntity.nucleo ? beneficiarioEntity.nucleo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/beneficiario" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/beneficiario/${beneficiarioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BeneficiarioDetail;
