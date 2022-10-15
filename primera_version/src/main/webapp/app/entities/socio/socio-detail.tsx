import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './socio.reducer';

export const SocioDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const socioEntity = useAppSelector(state => state.socio.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="socioDetailsHeading">
          <Translate contentKey="refoodTrazabilidadApp.socio.detail.title">Socio</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{socioEntity.id}</dd>
          <dt>
            <span id="nombreSocio">
              <Translate contentKey="refoodTrazabilidadApp.socio.nombreSocio">Nombre Socio</Translate>
            </span>
          </dt>
          <dd>{socioEntity.nombreSocio}</dd>
          <dt>
            <span id="primerApellidoSocio">
              <Translate contentKey="refoodTrazabilidadApp.socio.primerApellidoSocio">Primer Apellido Socio</Translate>
            </span>
          </dt>
          <dd>{socioEntity.primerApellidoSocio}</dd>
          <dt>
            <span id="segundoApellidoSocio">
              <Translate contentKey="refoodTrazabilidadApp.socio.segundoApellidoSocio">Segundo Apellido Socio</Translate>
            </span>
          </dt>
          <dd>{socioEntity.segundoApellidoSocio}</dd>
          <dt>
            <span id="emailSocio">
              <Translate contentKey="refoodTrazabilidadApp.socio.emailSocio">Email Socio</Translate>
            </span>
          </dt>
          <dd>{socioEntity.emailSocio}</dd>
          <dt>
            <span id="telefonoContactoSocio">
              <Translate contentKey="refoodTrazabilidadApp.socio.telefonoContactoSocio">Telefono Contacto Socio</Translate>
            </span>
          </dt>
          <dd>{socioEntity.telefonoContactoSocio}</dd>
          <dt>
            <span id="dniSocio">
              <Translate contentKey="refoodTrazabilidadApp.socio.dniSocio">Dni Socio</Translate>
            </span>
          </dt>
          <dd>{socioEntity.dniSocio}</dd>
          <dt>
            <span id="fechaNacimientoSocio">
              <Translate contentKey="refoodTrazabilidadApp.socio.fechaNacimientoSocio">Fecha Nacimiento Socio</Translate>
            </span>
          </dt>
          <dd>
            {socioEntity.fechaNacimientoSocio ? (
              <TextFormat value={socioEntity.fechaNacimientoSocio} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="sexoSocio">
              <Translate contentKey="refoodTrazabilidadApp.socio.sexoSocio">Sexo Socio</Translate>
            </span>
          </dt>
          <dd>{socioEntity.sexoSocio}</dd>
          <dt>
            <span id="fechaAltaSocio">
              <Translate contentKey="refoodTrazabilidadApp.socio.fechaAltaSocio">Fecha Alta Socio</Translate>
            </span>
          </dt>
          <dd>
            {socioEntity.fechaAltaSocio ? (
              <TextFormat value={socioEntity.fechaAltaSocio} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fechaBajaSocio">
              <Translate contentKey="refoodTrazabilidadApp.socio.fechaBajaSocio">Fecha Baja Socio</Translate>
            </span>
          </dt>
          <dd>
            {socioEntity.fechaBajaSocio ? (
              <TextFormat value={socioEntity.fechaBajaSocio} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="contribucionMensual">
              <Translate contentKey="refoodTrazabilidadApp.socio.contribucionMensual">Contribucion Mensual</Translate>
            </span>
          </dt>
          <dd>{socioEntity.contribucionMensual}</dd>
          <dt>
            <span id="periodoPago">
              <Translate contentKey="refoodTrazabilidadApp.socio.periodoPago">Periodo Pago</Translate>
            </span>
          </dt>
          <dd>{socioEntity.periodoPago}</dd>
          <dt>
            <Translate contentKey="refoodTrazabilidadApp.socio.nucleo">Nucleo</Translate>
          </dt>
          <dd>{socioEntity.nucleo ? socioEntity.nucleo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/socio" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/socio/${socioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SocioDetail;
