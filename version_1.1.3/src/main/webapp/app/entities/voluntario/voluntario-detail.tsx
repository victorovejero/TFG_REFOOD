import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './voluntario.reducer';

export const VoluntarioDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const voluntarioEntity = useAppSelector(state => state.voluntario.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="voluntarioDetailsHeading">Voluntario</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{voluntarioEntity.id}</dd>
          <dt>
            <span id="nombre">Nombre</span>
          </dt>
          <dd>{voluntarioEntity.nombre}</dd>
          <dt>
            <span id="primerApellido">Primer Apellido</span>
          </dt>
          <dd>{voluntarioEntity.primerApellido}</dd>
          <dt>
            <span id="segundoApellido">Segundo Apellido</span>
          </dt>
          <dd>{voluntarioEntity.segundoApellido}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{voluntarioEntity.email}</dd>
          <dt>
            <span id="telefonoContacto">Telefono Contacto</span>
          </dt>
          <dd>{voluntarioEntity.telefonoContacto}</dd>
          <dt>
            <span id="dni">Dni</span>
          </dt>
          <dd>{voluntarioEntity.dni}</dd>
          <dt>
            <span id="fechaNacimiento">Fecha Nacimiento</span>
          </dt>
          <dd>
            {voluntarioEntity.fechaNacimiento ? (
              <TextFormat value={voluntarioEntity.fechaNacimiento} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="sexo">Sexo</span>
          </dt>
          <dd>{voluntarioEntity.sexo}</dd>
          <dt>
            <span id="fechaAlta">Fecha Alta</span>
          </dt>
          <dd>
            {voluntarioEntity.fechaAlta ? (
              <TextFormat value={voluntarioEntity.fechaAlta} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fechaBaja">Fecha Baja</span>
          </dt>
          <dd>
            {voluntarioEntity.fechaBaja ? (
              <TextFormat value={voluntarioEntity.fechaBaja} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="tipo">Tipo</span>
          </dt>
          <dd>{voluntarioEntity.tipo}</dd>
          <dt>
            <span id="tipoTurno">Tipo Turno</span>
          </dt>
          <dd>{voluntarioEntity.tipoTurno}</dd>
          <dt>
            <span id="responsableDia">Responsable Dia</span>
          </dt>
          <dd>{voluntarioEntity.responsableDia ? 'true' : 'false'}</dd>
          <dt>
            <span id="origen">Origen</span>
          </dt>
          <dd>{voluntarioEntity.origen}</dd>
          <dt>
            <span id="manipuladorAlimentos">Manipulador Alimentos</span>
          </dt>
          <dd>{voluntarioEntity.manipuladorAlimentos ? 'true' : 'false'}</dd>
          <dt>
            <span id="codigoPostal">Codigo Postal</span>
          </dt>
          <dd>{voluntarioEntity.codigoPostal}</dd>
          <dt>Nucleo</dt>
          <dd>{voluntarioEntity.nucleo ? voluntarioEntity.nucleo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/voluntario" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/voluntario/${voluntarioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VoluntarioDetail;
