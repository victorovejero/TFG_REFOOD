import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './persona-beneficiaria.reducer';

export const PersonaBeneficiariaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const personaBeneficiariaEntity = useAppSelector(state => state.personaBeneficiaria.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="personaBeneficiariaDetailsHeading">Persona Beneficiaria</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{personaBeneficiariaEntity.id}</dd>
          <dt>
            <span id="nombre">Nombre</span>
          </dt>
          <dd>{personaBeneficiariaEntity.nombre}</dd>
          <dt>
            <span id="primerApellido">Primer Apellido</span>
          </dt>
          <dd>{personaBeneficiariaEntity.primerApellido}</dd>
          <dt>
            <span id="segundoApellido">Segundo Apellido</span>
          </dt>
          <dd>{personaBeneficiariaEntity.segundoApellido}</dd>
          <dt>
            <span id="fechaNacimiento">Fecha Nacimiento</span>
          </dt>
          <dd>{personaBeneficiariaEntity.fechaNacimiento}</dd>
          <dt>
            <span id="sexo">Sexo</span>
          </dt>
          <dd>{personaBeneficiariaEntity.sexo}</dd>
          <dt>
            <span id="parentesco">Parentesco</span>
          </dt>
          <dd>{personaBeneficiariaEntity.parentesco}</dd>
          <dt>
            <span id="situacionProfesional">Situacion Profesional</span>
          </dt>
          <dd>{personaBeneficiariaEntity.situacionProfesional}</dd>
          <dt>Intolerancia</dt>
          <dd>
            {personaBeneficiariaEntity.intolerancias
              ? personaBeneficiariaEntity.intolerancias.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {personaBeneficiariaEntity.intolerancias && i === personaBeneficiariaEntity.intolerancias.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Beneficiario</dt>
          <dd>{personaBeneficiariaEntity.beneficiario ? personaBeneficiariaEntity.beneficiario.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/persona-beneficiaria" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/persona-beneficiaria/${personaBeneficiariaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PersonaBeneficiariaDetail;
