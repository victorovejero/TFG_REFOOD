import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './alimento-de-entrada.reducer';


export const AlimentoDeEntradaDetail = () => {
const [tupperId, setTupperId] = useState();

  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();
  console.log(id);
  useEffect(() => {
    dispatch(getEntity(id));
    
  }, []);

  const alimentoDeEntradaEntity = useAppSelector(state => state.alimentoDeEntrada.entity);
  
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="alimentoDeEntradaDetailsHeading">Alimento De Entrada</h2>
        <dl className="jh-entity-details">
          {/* <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{alimentoDeEntradaEntity.id}</dd> */}
          <dt>
            <span id="peso">Peso</span>
          </dt>
          <dd>{alimentoDeEntradaEntity.peso}</dd>
          <dt>Donante</dt>
          <dd>
            <Link to={`/donante/${id}`}>
              {alimentoDeEntradaEntity.donante ? alimentoDeEntradaEntity.donante.idDonante : ''}
            </Link> 
          </dd>
          <dt>
            <span id="fechaYHoraEntrada">Fecha Y Hora Entrada</span>
          </dt>
          <dd>
            {alimentoDeEntradaEntity.fechaYHoraEntrada ? (
              <TextFormat value={alimentoDeEntradaEntity.fechaYHoraEntrada} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          
          <dt>
            <span id="fechaYHoraRecogida">Fecha Y Hora Recogida</span>
          </dt>
          <dd>
            {alimentoDeEntradaEntity.fechaYHoraRecogida ? (
              <TextFormat value={alimentoDeEntradaEntity.fechaYHoraRecogida} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fechaYHoraPreparacion">Fecha Y Hora Preparacion</span>
          </dt>
          <dd>
            {alimentoDeEntradaEntity.fechaYHoraPreparacion ? (
              <TextFormat value={alimentoDeEntradaEntity.fechaYHoraPreparacion} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
           <dt>
            <span id="frutaYVerdura">Fruta Y Verdura</span>
          </dt>
          <dd>{alimentoDeEntradaEntity.frutaYVerdura ? 'true' : 'false'}</dd>
          <dt>Fruta Y Verdura</dt>
          <dd>
            {alimentoDeEntradaEntity.frutaYVerduras
              ? alimentoDeEntradaEntity.frutaYVerduras.map((val, i) => (
                  <span key={val.id}>
                    {val.nombreAlimento}
                    {alimentoDeEntradaEntity.frutaYVerduras && i === alimentoDeEntradaEntity.frutaYVerduras.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Tupper</dt>
          <dd>
          <Link to={`/tupper/${id}`}>
            {alimentoDeEntradaEntity.tupper ? alimentoDeEntradaEntity.tupper.modelo : ''}
          </Link>
          </dd>
          
          <dt>Tipo De Alimento</dt>
          <dd>
            <Link to={`/tipo-de-alimento/${id}`} onChange={(e) => console.log(e)}>
                {alimentoDeEntradaEntity.tipoDeAlimento ? alimentoDeEntradaEntity.tipoDeAlimento.id : ''}
            </Link>
          </dd>
        </dl>
        <Button tag={Link} to="/alimento-de-entrada" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/alimento-de-entrada/${alimentoDeEntradaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AlimentoDeEntradaDetail;
