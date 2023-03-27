import React, { useState, useEffect, useRef } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText,Alert } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAlSal } from 'app/shared/model/al-sal.model';
import { getAllEntities as getAlSals } from 'app/entities/al-sal/al-sal.reducer';
import { IBenef } from 'app/shared/model/benef.model';
import { getAllEntities as getBenefs } from 'app/entities/benef/benef.reducer';
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

  const alSals = useAppSelector(state => state.alSal.entities);
  const benefs = useAppSelector(state => state.benef.entities);
  const checkoutEntity = useAppSelector(state => state.checkout.entity);
  const loading = useAppSelector(state => state.checkout.loading);
  const updating = useAppSelector(state => state.checkout.updating);
  const updateSuccess = useAppSelector(state => state.checkout.updateSuccess);
  const [beneficiario, setBeneficiario] = useState<number>( isNew? 0 : checkoutEntity?.beneficiario?.id);
  console.log("BENEFICIARIO STATE" + beneficiario);
  console.log(benefs)
  const handleClose = () => {
    navigate('/checkout' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAlSals({}));
    dispatch(getBenefs({}));
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
      alSals: mapIdList(values.alSals),
      benef: benefs.find(it => it.id.toString() === values.benef.toString()),
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
          alSals: checkoutEntity?.alSals?.map(e => e.id.toString()),
          benef: beneficiario
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
          <h2 id="reefoodTrazabilidadAppV3App.checkout.home.createOrEditLabel" data-cy="CheckoutCreateUpdateHeading">
            { isNew ? "Crear Checkout":"Editar Checkout"}
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
              <ValidatedField id="checkout-benef" name="benef" data-cy="benef" label="Beneficiario" type="select" value={beneficiario} onChange={(e) => setBeneficiario(Number(e.target.value))} required>
                <option value="" key="0" />
                {benefs
                  ? benefs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.idBeneficiario} - {otherEntity.nombreRepresentante}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                autoComplete="off"
                label="Peso (kg)"
                id="checkout-peso"
                name="peso"
                data-cy="peso"
                type="text"
                onChange={pesoAlert}
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  validate: v => isNumber(v) || 'Este campo debe ser un número.',
                }}
              />
              <Alert color="danger"  isOpen={showPesoAlert}>
                ¿Está seguro de que el peso es mayor a {PESO_MAX}Kg?
              </Alert>
              
              <ValidatedField label="Alimentos de Salida" id="checkout-alSal" data-cy="alSal" type="select" multiple name="alSals">
                <option value="" key="0" />
                {alSals
                  ? alSals.map(otherEntity => (
                    otherEntity.benef.id == beneficiario && otherEntity.fechaSalida == getToday(false) && otherEntity.benef? 
                      <option value={otherEntity.id} key={otherEntity.id} selected={true}>
                        {otherEntity.tupper.modelo}
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
