import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './p-benef.reducer';

export const PBenefDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const pBenefEntity = useAppSelector(state => state.pBenef.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pBenefDetailsHeading">P Benef</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{pBenefEntity.id}</dd>
          <dt>
            <span id="nombre">Nombre</span>
          </dt>
          <dd>{pBenefEntity.nombre}</dd>
          <dt>
            <span id="primerApellido">Primer Apellido</span>
          </dt>
          <dd>{pBenefEntity.primerApellido}</dd>
          <dt>
            <span id="segundoApellido">Segundo Apellido</span>
          </dt>
          <dd>{pBenefEntity.segundoApellido}</dd>
          <dt>
            <span id="fechaNacimiento">Fecha Nacimiento</span>
          </dt>
          <dd>{pBenefEntity.fechaNacimiento}</dd>
          <dt>
            <span id="sexo">Sexo</span>
          </dt>
          <dd>{pBenefEntity.sexo}</dd>
          <dt>
            <span id="parentesco">Parentesco</span>
          </dt>
          <dd>{pBenefEntity.parentesco}</dd>
          <dt>
            <span id="situacionProfesional">Situacion Profesional</span>
          </dt>
          <dd>{pBenefEntity.situacionProfesional}</dd>
          <dt>Intol</dt>
          <dd>
            {pBenefEntity.intols
              ? pBenefEntity.intols.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {pBenefEntity.intols && i === pBenefEntity.intols.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Benef</dt>
          <dd>{pBenefEntity.benef ? pBenefEntity.benef.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/p-benef" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/p-benef/${pBenefEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PBenefDetail;
