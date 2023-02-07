import React, { useState, useEffect, useRef } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText,Alert } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAlimentoDeSalida } from 'app/shared/model/alimento-de-salida.model';
import { getAllEntities as getAlimentoDeSalidas } from 'app/entities/alimento-de-salida/alimento-de-salida.reducer';
import { IBeneficiario } from 'app/shared/model/beneficiario.model';
import { getAllEntities as getBeneficiarios } from 'app/entities/beneficiario/beneficiario.reducer';
import { ICheckout } from 'app/shared/model/checkout.model';
import { getEntity, updateEntity, createEntity, reset } from './checkout.reducer';
import {getToday} from 'app/shared/util/date-utils'

export const CheckoutUpdate = () => {
  const PESO_MAX = 15;
  const [defaultToday,setDefaultToday] = useState<String>(getToday(false));
  const pesoMaxNotify = useRef<boolean>(false);
  const [showPesoAlert,setShowPesoAlert] = useState<boolean>(false);
  
  
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
  console.log(alimentoDeSalidas)
  const [beneficiario, setBeneficiario] = useState<number>( isNew? 0 : checkoutEntity?.beneficiario?.id);

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
          beneficiario: beneficiario
        };

        const pesoAlert = (event) => {
          switch(event.target.value >= 10 && event.target.value !==""){
            case true:
              if(pesoMaxNotify.current === false){
                setShowPesoAlert(!showPesoAlert);
                pesoMaxNotify.current = true;
              }
              break;
            case false:
              if(pesoMaxNotify.current === true){
                setShowPesoAlert(!showPesoAlert);
                pesoMaxNotify.current = false;
              }
              break;
            }
        }
  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="refoodTrazabilidadApp.checkout.home.createOrEditLabel" data-cy="CheckoutCreateUpdateHeading">
            Crear o editar Checkout
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
                autoComplete="off"
                label="Peso"
                id="checkout-peso"
                name="peso"
                data-cy="peso"
                type="text"
                maxlength="6"
                onChange={pesoAlert}
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  validate: v => isNumber(v) || 'Este campo debe ser un número.',
                }}
              />
              <Alert color="danger"  isOpen={showPesoAlert}>
                ¿Está seguro de que el peso es mayor a {PESO_MAX}Kg?
              </Alert>
              <ValidatedField id="checkout-beneficiario" name="beneficiario" data-cy="beneficiario" label="Beneficiario" type="select" value={beneficiario} onChange={(e) => setBeneficiario(Number(e.target.value))} required>
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
                required
              >
                <option value="" key="0" >No se ha presentado</option>
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
