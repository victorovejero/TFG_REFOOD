import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITupper } from 'app/shared/model/tupper.model';
import { getEntities as getTuppers } from 'app/entities/tupper/tupper.reducer';
import { IBeneficiario } from 'app/shared/model/beneficiario.model';
import { getEntities as getBeneficiarios } from 'app/entities/beneficiario/beneficiario.reducer';
import { IAlimentoDeEntrada } from 'app/shared/model/alimento-de-entrada.model';
import { getEntities as getAlimentoDeEntradas } from 'app/entities/alimento-de-entrada/alimento-de-entrada.reducer';
import { IAlimentoDeSalida } from 'app/shared/model/alimento-de-salida.model';
import { getEntity, updateEntity, createEntity, reset } from './alimento-de-salida.reducer';

export const AlimentoDeSalidaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;
  const date = new Date();
  const today = date.getDate();
  const month = date.getMonth() + 1;
  const tuppers = useAppSelector(state => state.tupper.entities);
  const beneficiarios = useAppSelector(state => state.beneficiario.entities);

  const alimentoDeEntradas = useAppSelector(state => state.alimentoDeEntrada.entities);
  //HABRÁ QUE HACER EL FILTRO EN EL BACKEND... NO SE POR QUE NO ME FUNCIONA AHORA CUANDO ME FUNCIONÓ EN SU DIA.

  // let reduced = alimentoDeEntradas.reduced((filtered,x) => {
  //   if((today-x.fechaYHoraEntrada.substring(8,10) <= 2 && month == x.fechaYHoraEntrada.substring(5,7)) || (x.fechaYHoraEntrada.substring(8,10) - today >= 29 && (month - 1) == x.fechaYHoraEntrada.substring(5,7))){
  //     let alimento = {hola:1}
  //     filtered.push(alimento);
  //   }
  //   return filtered;
  // },[])
    
  //   .filter(x => {(today-x.fechaYHoraEntrada.substring(8,10) <= 2 && month == x.fechaYHoraEntrada.substring(5,7)) || (x.fechaYHoraEntrada.substring(8,10) - today >= 29 && (month - 1) == x.fechaYHoraEntrada.substring(5,7))})
  // );
  // .filter((x) => {
  //   (today-x.fechaYHoraEntrada.substring(8,10) <= 2 && month == x.fechaYHoraEntrada.substring(5,7)) || (x.fechaYHoraEntrada.substring(8,10) - today >= 29 && (month - 1) == x.fechaYHoraEntrada.substring(5,7))   
  // });
  const alimentoDeSalidaEntity = useAppSelector(state => state.alimentoDeSalida.entity);
  const loading = useAppSelector(state => state.alimentoDeSalida.loading);
  const updating = useAppSelector(state => state.alimentoDeSalida.updating);
  const updateSuccess = useAppSelector(state => state.alimentoDeSalida.updateSuccess);


  const handleClose = () => {
    navigate('/alimento-de-salida' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTuppers({}));
    dispatch(getBeneficiarios({}));
    dispatch(getAlimentoDeEntradas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...alimentoDeSalidaEntity,
      ...values,
      tupper: tuppers.find(it => it.id.toString() === values.tupper.toString()),
      beneficiario: beneficiarios.find(it => it.id.toString() === values.beneficiario.toString()),
      alimentoDeEntrada: alimentoDeEntradas.find(it => it.id.toString() === values.alimentoDeEntrada.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...alimentoDeSalidaEntity,
          tupper: alimentoDeSalidaEntity?.tupper?.id,
          beneficiario: alimentoDeSalidaEntity?.beneficiario?.id,
          alimentoDeEntrada: alimentoDeSalidaEntity?.alimentoDeEntrada?.id,
        };

  const getToday = () => {
    const date = new Date();
    
    const day = date.getDate().toString();
    const month = (date.getMonth() + 1).toString();
    const year = date.getFullYear().toString();
    return year + "-" + month + "-" + day;
  }
  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="refoodTrazabilidadApp.alimentoDeSalida.home.createOrEditLabel" data-cy="AlimentoDeSalidaCreateUpdateHeading">
            Crear o editar Alimento De Salida
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="alimento-de-salida-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Peso"
                id="alimento-de-salida-peso"
                name="peso"
                data-cy="peso"
                maxlength="4"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  validate: v => isNumber(v) || 'Este campo debe ser un número.',
                }}
              />
              <ValidatedField
                label="Fecha Salida"
                id="alimento-de-salida-fechaSalida"
                name="fechaSalida"
                data-cy="fechaSalida"
                type="date"
                value={isNew ? getToday():null}
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField id="alimento-de-salida-tupper" name="tupper" data-cy="tupper" label="Tupper" type="select" validate={{required: { value: true, message: 'Este campo es obligatorio.' }}}>
                <option value="" key="0" />
                {tuppers
                  ? tuppers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.modelo}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="alimento-de-salida-beneficiario"
                name="beneficiario"
                data-cy="beneficiario"
                label="Beneficiario"
                type="select"
                validate={{required: { value: true, message: 'Este campo es obligatorio.' }}}
              >
                <option value="" key="0" />
                {beneficiarios
                  ? beneficiarios.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.idBeneficiario}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="alimento-de-salida-alimentoDeEntrada"
                name="alimentoDeEntrada"
                data-cy="alimentoDeEntrada"
                label="Alimento De Entrada"
                type="select"
                validate={{required: { value: true, message: 'Este campo es obligatorio.' }}}
              >
                <option value="" key="0" />
                {alimentoDeEntradas
                  ? alimentoDeEntradas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.donante.idDonante} - {otherEntity.tipoDeAlimento.nombreAlimento} {otherEntity.fechaYHoraEntrada}
                      </option>
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

export default AlimentoDeSalidaUpdate;
