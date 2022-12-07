import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './checkout.reducer';

export const CheckoutDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const checkoutEntity = useAppSelector(state => state.checkout.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="checkoutDetailsHeading">Checkout</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{checkoutEntity.id}</dd>
          <dt>
            <span id="fechaSalida">Fecha Salida</span>
          </dt>
          <dd>
            {checkoutEntity.fechaSalida ? (
              <TextFormat value={checkoutEntity.fechaSalida} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="peso">Peso</span>
          </dt>
          <dd>{checkoutEntity.peso}</dd>
          <dt>Alimento De Salida</dt>
          <dd>
            {checkoutEntity.alimentoDeSalidas
              ? checkoutEntity.alimentoDeSalidas.map((val, i) => (
                  <span key={val.id}>
                    {val.alimentoDeEntrada.frutaYVerdura ? val.alimentoDeEntrada.frutasYVerduras : (val.alimentoDeEntrada.tipoDeAlimento.nombreAlimento + " - " + val.beneficiario.idBeneficiario)}
                    {/* {val.id} */}
                    {checkoutEntity.alimentoDeSalidas && i === checkoutEntity.alimentoDeSalidas.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Beneficiario</dt>
          <dd>{checkoutEntity.beneficiario ? checkoutEntity.beneficiario.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/checkout" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/checkout/${checkoutEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CheckoutDetail;
