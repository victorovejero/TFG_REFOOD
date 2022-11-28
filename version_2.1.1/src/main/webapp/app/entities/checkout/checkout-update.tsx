import React, { useState, useEffect, useRef } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAlimentoDeSalida } from 'app/shared/model/alimento-de-salida.model';
import { getEntities as getAlimentoDeSalidas } from 'app/entities/alimento-de-salida/alimento-de-salida.reducer';
import { IBeneficiario } from 'app/shared/model/beneficiario.model';
import { getEntities as getBeneficiarios } from 'app/entities/beneficiario/beneficiario.reducer';
import { ICheckout } from 'app/shared/model/checkout.model';
import { getEntity, updateEntity, createEntity, reset } from './checkout.reducer';
import {getToday} from 'app/shared/util/date-utils'

export const CheckoutUpdate = () => {
  const PESO_MAX = 15;
  const [defaultToday,setDefaultToday] = useState<String>(getToday(false));
  const [beneficiario, setBeneficiario] = useState<number>(0);
  const peso = useRef<Number>();
  const pesoMaxNotify = useRef<Boolean>(false);
  
  
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const alimentoDeSalidas = useAppSelector(state => state.alimentoDeSalida.entities);
  const beneficiarios = useAppSelector(state => state.beneficiario.entities);
  const checkoutEntity = useAppSelector(state => state.checkout.entity);
  const loading = useAppSelector(state => state.checkout.loading);
  const updating = useAppSelector(state => state.checkout.updating);
  const updateSuccess = useAppSelector(state => state.checkout.updateSuccess);

  console.log(alimentoDeSalidas);
  const handleClose = () => {
    navigate('/checkout' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAlimentoDeSalidas({}));
    dispatch(getBeneficiarios({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...checkoutEntity,
      ...values,
      alimentoDeSalidas: mapIdList(values.alimentoDeSalidas),
      beneficiario: beneficiarios.find(it => it.id.toString() === values.beneficiario.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {fechaSalida:defaultToday}
      : {
          ...checkoutEntity,
          alimentoDeSalidas: checkoutEntity?.alimentoDeSalidas?.map(e => e.id.toString()),
          beneficiario: checkoutEntity?.beneficiario?.id,
        };

  const pesoAlert = (event) => {
    peso.current = event.target.value;
    if(peso.current >= PESO_MAX && pesoMaxNotify.current === false){
      confirm("¿Está seguro de que el peso es mayor a "+ PESO_MAX + "Kg? \n\n Si acepta, no le volveremos a notificar.") ? pesoMaxNotify.current = true : null
    }
  }
  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="refoodTrazabilidadApp.checkout.home.createOrEditLabel" data-cy="CheckoutCreateUpdateHeading">
            Crear o editar Checkout {JSON.stringify(beneficiario)}
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="checkout-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Fecha Salida"
                id="checkout-fechaSalida"
                name="fechaSalida"
                data-cy="fechaSalida"
                type="date"
                value={isNew ? defaultToday : null}
                onChange={isNew ? (e) => setDefaultToday(e.target.value) : null}
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Peso"
                id="checkout-peso"
                name="peso"
                data-cy="peso"
                type="text"
                maxlength="6"
                value={peso.current}
                onChange={pesoAlert}
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  validate: v => isNumber(v) || 'Este campo debe ser un número.',
                }}
              />
              <ValidatedField id="checkout-beneficiario" name="beneficiario" data-cy="beneficiario" label="Beneficiario" type="select" value={beneficiario} onChange={(e) => setBeneficiario(Number(e.target.value))}>
                <option value="ninguno" key="0" />
                {beneficiarios
                  ? beneficiarios.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.idBeneficiario}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Alimento De Salida"
                id="checkout-alimentoDeSalida"
                data-cy="alimentoDeSalida"
                type="select"
                multiple
                name="alimentoDeSalidas"
              >
                <option value="ninguno" key="0" />
                {alimentoDeSalidas
                  ? alimentoDeSalidas.map(otherEntity => (
                    otherEntity.beneficiario.id == beneficiario && otherEntity.fechaSalida == getToday(false)? 
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option> : ""
                    ))
                  : null}
              </ValidatedField>
              
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Volver</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Guardar
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CheckoutUpdate;
