import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
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
        <h2 data-cy="socioDetailsHeading">Socio</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{socioEntity.id}</dd>
          <dt>
            <span id="nombre">Nombre</span>
          </dt>
          <dd>{socioEntity.nombre}</dd>
          <dt>
            <span id="primerApellido">Primer Apellido</span>
          </dt>
          <dd>{socioEntity.primerApellido}</dd>
          <dt>
            <span id="segundoApellido">Segundo Apellido</span>
          </dt>
          <dd>{socioEntity.segundoApellido}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{socioEntity.email}</dd>
          <dt>
            <span id="telefonoContacto">Telefono Contacto</span>
          </dt>
          <dd>{socioEntity.telefonoContacto}</dd>
          <dt>
            <span id="dni">Dni</span>
          </dt>
          <dd>{socioEntity.dni}</dd>
          <dt>
            <span id="fechaNacimiento">Fecha Nacimiento</span>
          </dt>
          <dd>
            {socioEntity.fechaNacimiento ? (
              <TextFormat value={socioEntity.fechaNacimiento} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="sexo">Sexo</span>
          </dt>
          <dd>{socioEntity.sexo}</dd>
          <dt>
            <span id="fechaAlta">Fecha Alta</span>
          </dt>
          <dd>{socioEntity.fechaAlta ? <TextFormat value={socioEntity.fechaAlta} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="fechaBaja">Fecha Baja</span>
          </dt>
          <dd>{socioEntity.fechaBaja ? <TextFormat value={socioEntity.fechaBaja} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="contribucionMensual">Contribucion Mensual</span>
          </dt>
          <dd>{socioEntity.contribucionMensual}</dd>
          <dt>
            <span id="periodoPago">Periodo Pago</span>
          </dt>
          <dd>{socioEntity.periodoPago}</dd>
          <dt>
            <span id="activo">Activo</span>
          </dt>
          <dd>{socioEntity.activo ? 'true' : 'false'}</dd>
          <dt>
            <span id="nucleoAsociado">Nucleo Asociado</span>
          </dt>
          <dd>{socioEntity.nucleoAsociado}</dd>
          <dt>
            <span id="comunicacion">Comunicacion</span>
          </dt>
          <dd>{socioEntity.comunicacion ? 'true' : 'false'}</dd>
          <dt>
            <span id="direccion">Direccion</span>
          </dt>
          <dd>{socioEntity.direccion}</dd>
          <dt>
            <span id="codigoPostal">Codigo Postal</span>
          </dt>
          <dd>{socioEntity.codigoPostal}</dd>
          <dt>
            <span id="provincia">Provincia</span>
          </dt>
          <dd>{socioEntity.provincia}</dd>
          <dt>
            <span id="pais">Pais</span>
          </dt>
          <dd>{socioEntity.pais}</dd>
        </dl>
        <Button tag={Link} to="/socio" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/socio/${socioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SocioDetail;
