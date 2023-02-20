import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './benef.reducer';

export const BenefDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const benefEntity = useAppSelector(state => state.benef.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="benefDetailsHeading">Benef</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{benefEntity.id}</dd>
          <dt>
            <span id="idBeneficiario">Id Beneficiario</span>
          </dt>
          <dd>{benefEntity.idBeneficiario}</dd>
          <dt>
            <span id="nombreRepresentante">Nombre Representante</span>
          </dt>
          <dd>{benefEntity.nombreRepresentante}</dd>
          <dt>
            <span id="primerApellidoRepresentante">Primer Apellido Representante</span>
          </dt>
          <dd>{benefEntity.primerApellidoRepresentante}</dd>
          <dt>
            <span id="segundoApellidoRepresentante">Segundo Apellido Representante</span>
          </dt>
          <dd>{benefEntity.segundoApellidoRepresentante}</dd>
          <dt>
            <span id="numeroPersonas">Numero Personas</span>
          </dt>
          <dd>{benefEntity.numeroPersonas}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{benefEntity.email}</dd>
          <dt>
            <span id="telefono">Telefono</span>
          </dt>
          <dd>{benefEntity.telefono}</dd>
          <dt>
            <span id="telefonoSecundario">Telefono Secundario</span>
          </dt>
          <dd>{benefEntity.telefonoSecundario}</dd>
          <dt>
            <span id="direccion">Direccion</span>
          </dt>
          <dd>{benefEntity.direccion}</dd>
          <dt>
            <span id="codigoPostal">Codigo Postal</span>
          </dt>
          <dd>{benefEntity.codigoPostal}</dd>
          <dt>
            <span id="fechaAlta">Fecha Alta</span>
          </dt>
          <dd>{benefEntity.fechaAlta ? <TextFormat value={benefEntity.fechaAlta} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="fechaBaja">Fecha Baja</span>
          </dt>
          <dd>{benefEntity.fechaBaja ? <TextFormat value={benefEntity.fechaBaja} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="numeroNinios">Numero Ninios</span>
          </dt>
          <dd>{benefEntity.numeroNinios}</dd>
          <dt>
            <span id="idDual">Id Dual</span>
          </dt>
          <dd>{benefEntity.idDual}</dd>
          <dt>
            <span id="activo">Activo</span>
          </dt>
          <dd>{benefEntity.activo ? 'true' : 'false'}</dd>
          <dt>Intol</dt>
          <dd>
            {benefEntity.intols
              ? benefEntity.intols.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {benefEntity.intols && i === benefEntity.intols.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Nucleo</dt>
          <dd>{benefEntity.nucleo ? benefEntity.nucleo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/benef" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/benef/${benefEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BenefDetail;
