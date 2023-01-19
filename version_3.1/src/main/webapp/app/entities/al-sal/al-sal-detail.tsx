import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './al-sal.reducer';

export const AlSalDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const alSalEntity = useAppSelector(state => state.alSal.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="alSalDetailsHeading">Alimento de Salida</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{alSalEntity.id}</dd>
          <dt>
            <span id="fechaSalida">Fecha Salida</span>
          </dt>
          <dd>
            {alSalEntity.fechaSalida ? <TextFormat value={alSalEntity.fechaSalida} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>Tupper</dt>
          <dd>{alSalEntity.tupper ? alSalEntity.tupper.modelo : ''}</dd>
          <dt>Beneficiario</dt>
          <dd>{alSalEntity.benef ? alSalEntity.benef.idBeneficiario : ''}</dd>
          <dt>Alimento de Entrada</dt>
          <dd>{alSalEntity.alEnt ? alSalEntity.alEnt.tipoAl.nombreAlimento : ''}</dd>
        </dl>
        <Button tag={Link} to="/al-sal" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/al-sal/${alSalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AlSalDetail;
