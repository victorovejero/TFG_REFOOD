import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITupper } from 'app/shared/model/tupper.model';
import { getAllEntities as getTuppers } from 'app/entities/tupper/tupper.reducer';
import { IBenef } from 'app/shared/model/benef.model';
import { getAllEntities as getBenefs } from 'app/entities/benef/benef.reducer';
import { IAlEnt } from 'app/shared/model/al-ent.model';
import { getAllEntities as getAlEnts } from 'app/entities/al-ent/al-ent.reducer';
import { ICheckout } from 'app/shared/model/checkout.model';
import { getEntities as getCheckouts } from 'app/entities/checkout/checkout.reducer';
import { IAlSal } from 'app/shared/model/al-sal.model';
import { getEntity, updateEntity, createEntity, reset } from './al-sal.reducer';
import {getToday} from 'app/shared/util/date-utils'
export const AlSalUpdate = () => {
  const [rangoEntradas, setRangoEntradas] = useState<number>(0);
  const [defaultToday,setDefaultToday] = useState<String>(getToday(false));
  // const [beneficiario, setBeneficiario] = useLocalStorageState('beneficiario',{defaultValue:""});
  const [beneficiario, setBeneficiario] = useState(localStorage.getItem("beneficiario-actual") ?? "");

  //Para persistir el estado de beneficiario en memoria.
  useEffect(() => {
    localStorage.setItem("beneficiario-actual",beneficiario);
  },[beneficiario])

  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;


  const today = new Date();
  const day = today.getDate();
  const month = today.getMonth() + 1;
  const year = today.getFullYear();

  
  const tuppers = useAppSelector(state => state.tupper.entities);
  const benefs = useAppSelector(state => state.benef.entities);
  const alEnts = useAppSelector(state => state.alEnt.entities)
  .filter(x => (( day - x.fechaYHoraEntrada.substring(8,10) <= rangoEntradas && month == x.fechaYHoraEntrada.substring(5,7)) || (x.fechaYHoraEntrada.substring(8,10) - day >= 31 - rangoEntradas && month - x.fechaYHoraEntrada.substring(5,7) == 1)) && year == x.fechaYHoraEntrada.substring(0,4));
  const checkouts = useAppSelector(state => state.checkout.entities);
  const alSalEntity = useAppSelector(state => state.alSal.entity);
  const loading = useAppSelector(state => state.alSal.loading);
  const updating = useAppSelector(state => state.alSal.updating);
  const updateSuccess = useAppSelector(state => state.alSal.updateSuccess);

  const handleClose = () => {
    location.reload();
    navigate('/al-sal/new' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTuppers({}));
    dispatch(getBenefs({}));
    dispatch(getAlEnts({}));
    dispatch(getCheckouts({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...alSalEntity,
      ...values,
      tupper: tuppers.find(it => it.id.toString() === values.tupper.toString()),
      benef: benefs.find(it => it.id.toString() === values.benef.toString()),
      alEnt: alEnts.find(it => it.id.toString() === values.alEnt.toString()),
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
          ...alSalEntity,
          tupper: alSalEntity?.tupper?.id,
          benef: alSalEntity?.benef?.id,
          alEnt: alSalEntity?.alEnt?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="reefoodTrazabilidadAppV3App.alSal.home.createOrEditLabel" data-cy="AlSalCreateUpdateHeading">
            Crear o editar Alimento de Salida
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="al-sal-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Fecha Salida"
                id="al-sal-fechaSalida"
                name="fechaSalida"
                data-cy="fechaSalida"
                value={ isNew ? defaultToday : null}
                onChange={ isNew ? (e) => setDefaultToday(e.target.value) : null}
                type="date"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField id="al-sal-tupper" name="tupper" data-cy="tupper" label="Tupper" type="select">
                <option value="" key="0" />
                {tuppers
                  ? tuppers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.modelo}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="al-sal-benef" name="benef" data-cy="benef" label="Beneficiario" type="select" required>
                <option value="" key="0" />
                {benefs
                  ? benefs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.idBeneficiario} - {otherEntity.nombreRepresentante}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="al-sal-alEnt" name="alEnt" data-cy="alEnt" label="Alimento de Entrada" type="select">
                <option value="" key="0" />
                {alEnts
                  ? alEnts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.tipoAl.nombreAlimento} - {otherEntity.donante.nombre}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <div className="slider-div">
                <input  className="slider" type="range" min="0" max="3" value={rangoEntradas} onChange={(e) => setRangoEntradas(Number(e.target.value))} />
                <p>{rangoEntradas} días</p>
              </div>
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

export default AlSalUpdate;
