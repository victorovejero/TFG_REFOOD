import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
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
        <h2 data-cy="voluntarioDetailsHeading">
          <Translate contentKey="refoodTrazabilidadApp.voluntario.detail.title">Voluntario</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{voluntarioEntity.id}</dd>
          <dt>
            <span id="nombreVoluntario">
              <Translate contentKey="refoodTrazabilidadApp.voluntario.nombreVoluntario">Nombre Voluntario</Translate>
            </span>
          </dt>
          <dd>{voluntarioEntity.nombreVoluntario}</dd>
          <dt>
            <span id="primerApellido">
              <Translate contentKey="refoodTrazabilidadApp.voluntario.primerApellido">Primer Apellido</Translate>
            </span>
          </dt>
          <dd>{voluntarioEntity.primerApellido}</dd>
          <dt>
            <span id="segundoApellido">
              <Translate contentKey="refoodTrazabilidadApp.voluntario.segundoApellido">Segundo Apellido</Translate>
            </span>
          </dt>
          <dd>{voluntarioEntity.segundoApellido}</dd>
          <dt>
            <span id="emailVoluntario">
              <Translate contentKey="refoodTrazabilidadApp.voluntario.emailVoluntario">Email Voluntario</Translate>
            </span>
          </dt>
          <dd>{voluntarioEntity.emailVoluntario}</dd>
          <dt>
            <span id="telefonoContactoVoluntario">
              <Translate contentKey="refoodTrazabilidadApp.voluntario.telefonoContactoVoluntario">Telefono Contacto Voluntario</Translate>
            </span>
          </dt>
          <dd>{voluntarioEntity.telefonoContactoVoluntario}</dd>
          <dt>
            <span id="dniVoluntario">
              <Translate contentKey="refoodTrazabilidadApp.voluntario.dniVoluntario">Dni Voluntario</Translate>
            </span>
          </dt>
          <dd>{voluntarioEntity.dniVoluntario}</dd>
          <dt>
            <span id="fechaNacimientoVoluntario">
              <Translate contentKey="refoodTrazabilidadApp.voluntario.fechaNacimientoVoluntario">Fecha Nacimiento Voluntario</Translate>
            </span>
          </dt>
          <dd>
            {voluntarioEntity.fechaNacimientoVoluntario ? (
              <TextFormat value={voluntarioEntity.fechaNacimientoVoluntario} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="sexoVoluntario">
              <Translate contentKey="refoodTrazabilidadApp.voluntario.sexoVoluntario">Sexo Voluntario</Translate>
            </span>
          </dt>
          <dd>{voluntarioEntity.sexoVoluntario}</dd>
          <dt>
            <span id="fechaAlta">
              <Translate contentKey="refoodTrazabilidadApp.voluntario.fechaAlta">Fecha Alta</Translate>
            </span>
          </dt>
          <dd>
            {voluntarioEntity.fechaAlta ? (
              <TextFormat value={voluntarioEntity.fechaAlta} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fechaBaja">
              <Translate contentKey="refoodTrazabilidadApp.voluntario.fechaBaja">Fecha Baja</Translate>
            </span>
          </dt>
          <dd>
            {voluntarioEntity.fechaBaja ? (
              <TextFormat value={voluntarioEntity.fechaBaja} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="tipoVoluntario">
              <Translate contentKey="refoodTrazabilidadApp.voluntario.tipoVoluntario">Tipo Voluntario</Translate>
            </span>
          </dt>
          <dd>{voluntarioEntity.tipoVoluntario}</dd>
          <dt>
            <span id="tipoTurno">
              <Translate contentKey="refoodTrazabilidadApp.voluntario.tipoTurno">Tipo Turno</Translate>
            </span>
          </dt>
          <dd>{voluntarioEntity.tipoTurno}</dd>
          <dt>
            <span id="responsableDia">
              <Translate contentKey="refoodTrazabilidadApp.voluntario.responsableDia">Responsable Dia</Translate>
            </span>
          </dt>
          <dd>{voluntarioEntity.responsableDia ? 'true' : 'false'}</dd>
          <dt>
            <span id="origenVoluntario">
              <Translate contentKey="refoodTrazabilidadApp.voluntario.origenVoluntario">Origen Voluntario</Translate>
            </span>
          </dt>
          <dd>{voluntarioEntity.origenVoluntario}</dd>
          <dt>
            <span id="manipuladorAlimentos">
              <Translate contentKey="refoodTrazabilidadApp.voluntario.manipuladorAlimentos">Manipulador Alimentos</Translate>
            </span>
          </dt>
          <dd>{voluntarioEntity.manipuladorAlimentos ? 'true' : 'false'}</dd>
          <dt>
            <span id="codigoPostal">
              <Translate contentKey="refoodTrazabilidadApp.voluntario.codigoPostal">Codigo Postal</Translate>
            </span>
          </dt>
          <dd>{voluntarioEntity.codigoPostal}</dd>
          <dt>
            <Translate contentKey="refoodTrazabilidadApp.voluntario.nucleo">Nucleo</Translate>
          </dt>
          <dd>{voluntarioEntity.nucleo ? voluntarioEntity.nucleo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/voluntario" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/voluntario/${voluntarioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VoluntarioDetail;
