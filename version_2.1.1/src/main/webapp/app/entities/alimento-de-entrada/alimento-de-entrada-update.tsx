import React, { useState, useEffect, useRef } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, Alert } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector} from 'app/config/store';

import { IFrutaYVerdura } from 'app/shared/model/fruta-y-verdura.model';
import { getEntities as getFrutaYVerduras } from 'app/entities/fruta-y-verdura/fruta-y-verdura.reducer';
import { ITupper } from 'app/shared/model/tupper.model';
import { getAllEntities as getAllTuppers } from 'app/entities/tupper/tupper.reducer';
import { IDonante } from 'app/shared/model/donante.model';
import { getEntities as getDonantes } from 'app/entities/donante/donante.reducer';
import { ITipoDeAlimento } from 'app/shared/model/tipo-de-alimento.model';
import { getAllEntities as getAllTipoDeAlimentos } from 'app/entities/tipo-de-alimento/tipo-de-alimento.reducer';
import { IAlimentoDeEntrada } from 'app/shared/model/alimento-de-entrada.model';
import { getEntity, updateEntity, createEntity, reset } from './alimento-de-entrada.reducer';
import {getToday} from 'app/shared/util/date-utils'
import TipoAlimentoModal from './tipo-alimento-modal';
import './alimento-de-entrada.css';

export const AlimentoDeEntradaUpdate = () => {
  const PESO_MAX = 10;
  const [mostrarHoraPrep,setMostrarHoraPrep] = useState<Boolean>(false);
  const [mostrarHoraRecogida, setMostrarHoraRecogida] = useState<Boolean>(false);
  const [fechaElegida,setFechaElegida] = useState<String>(getToday(true));
  const [fechaRecogida, setFechaRecogida] = useState<String>(getToday(true));
  const [fechaPrep, setFechaPrep] = useState<String>(getToday(true));
  //const frutaYVerdura = useRef<Boolean>(false);
  const [frutaYVerdura,setFrutaYVerdura] = useState<boolean>(false);
  const [frutaYVerduraNotNew,setFrutaYVerduraNotNew] = useState<boolean>(false);
  //const renderCount = useRef<number>(0);
  const pesoMaxNotify = useRef<boolean>(false);
  const [showPesoAlert,setShowPesoAlert] = useState<boolean>(false);
  const [crearAlimento, setCrearAlimento] = useState<boolean>(false);
  const [donante, setDonante] = useState(localStorage.getItem("donante-actual") ?? "");

  useEffect(() => {
    localStorage.setItem("donante-actual", donante);
  },[donante])

  // const [tiposAlimentoUpdate, setTiposAlimentoUpdate] = useState(undefined);
  const dispatch = useAppDispatch();
  

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const frutaYVerduras = useAppSelector(state => state.frutaYVerdura.entities);
  const tuppers = useAppSelector(state => state.tupper.entities);
  const donantes = useAppSelector(state => state.donante.entities);
  const tipoDeAlimentos = useAppSelector(state => state.tipoDeAlimento.entities);
  const alimentoDeEntradaEntity = useAppSelector(state => state.alimentoDeEntrada.entity);
  const loading = useAppSelector(state => state.alimentoDeEntrada.loading);
  const updating = useAppSelector(state => state.alimentoDeEntrada.updating);
  const updateSuccess = useAppSelector(state => state.alimentoDeEntrada.updateSuccess);
  // console.log("Tipo de Alimentos " + tipoDeAlimentos);
  const handleClose = () => {
    location.reload();
    navigate('/alimento-de-entrada/new' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getFrutaYVerduras({}));
    dispatch(getAllTuppers({}));
    dispatch(getDonantes({}));
    dispatch(getAllTipoDeAlimentos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const getEmptyObject = (name:string) => {
    let value;
    if(frutaYVerdura){
      switch(name){
        case "tupper":
          value = (): ITupper => ({});
        break;
        case "tipoDeAlimento":
          value = () : ITipoDeAlimento => ({});
          break;
      }
    }else{
      value = () : IFrutaYVerdura => ({});
    }

  }
  
  const saveEntity = values => {
    values.fechaYHoraEntrada = convertDateTimeToServer(values.fechaYHoraEntrada);
    values.fechaYHoraRecogida = convertDateTimeToServer(values.fechaYHoraRecogida);
    values.fechaYHoraPreparacion = convertDateTimeToServer(values.fechaYHoraPreparacion);

    const entity = {
      ...alimentoDeEntradaEntity,
      ...values,
      frutaYVerduras: frutaYVerdura ? mapIdList(values.frutaYVerduras) : getEmptyObject("frutaYVerdura"),
      tupper: !frutaYVerdura ? tuppers.find(it => it.id.toString() === values.tupper.toString()) : getEmptyObject("tupper"),
      donante: donantes.find(it => it.id.toString() === values.donante.toString()),
      tipoDeAlimento: !frutaYVerdura ? tipoDeAlimentos.find(it => it.id.toString() === values.tipoDeAlimento.toString()) : getEmptyObject("tipoDeAlimento"),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () => 
    isNew
      ? { 
          //MOSTRAR SOLO LA HORA DE HOY EN FECHA ENTRADA, EL RESTO DEJARLOS A NULL PARA QUE NO AUTORELLENEN
          fechaYHoraEntrada: fechaElegida,//displayDefaultDateTime(),
          fechaYHoraRecogida: fechaRecogida,//displayDefaultDateTime(),
          fechaYHoraPreparacion: fechaPrep//displayDefaultDateTime(),
        }
      : {
          ...alimentoDeEntradaEntity,
          fechaYHoraEntrada: convertDateTimeFromServer(alimentoDeEntradaEntity.fechaYHoraEntrada),
          fechaYHoraRecogida: convertDateTimeFromServer(alimentoDeEntradaEntity.fechaYHoraRecogida),
          fechaYHoraPreparacion: convertDateTimeFromServer(alimentoDeEntradaEntity.fechaYHoraPreparacion),
          frutaYVerduras: alimentoDeEntradaEntity?.frutaYVerduras?.map(e => e.id.toString()),
          tupper: alimentoDeEntradaEntity?.tupper?.id,
          donante: alimentoDeEntradaEntity?.donante?.id,
          tipoDeAlimento: alimentoDeEntradaEntity?.tipoDeAlimento?.id,
        };
        
 
    // Cambio de estado, para seleccionar frutas y verduras.
    const changefrutaYVerdura = () => {
      setFrutaYVerdura(frutaYVerdura => !frutaYVerdura);
    }
 

    // Se ejecuta al cambiar el valor del input de peso. (Implementación del Warning de peso mayor que x)
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

    // Se ejecuta al presionar botón para añadir nuevo tipo de alimento (Cambio de estado)
    const showModal = () => {
      setCrearAlimento(!crearAlimento);
    }

    // Se ejecuta al cerrar el modal de crear alimento
    const handleCloseModal = () => {
      setCrearAlimento(false);
      navigate("/alimento-de-entrada/new");
      location.reload();
    }

    //EL FLOW DEL FORMULARIO SOLO PARA ENTRADAS NUEVAS, NO IMPLENENTADO PARA ENTRADAS YA EXISTENTES.
  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="refoodTrazabilidadApp.alimentoDeEntrada.home.createOrEditLabel" data-cy="AlimentoDeEntradaCreateUpdateHeading">
            Crear o editar Alimento De Entrada
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
                <ValidatedField name="id" required readOnly id="alimento-de-entrada-id" label="ID" validate={{ required: true }} />
              ) : null}


<ValidatedField
                className="checkbox"
                label="Fruta Y Verdura"
                id="alimento-de-enctrada-frutaYVerdura"
                name="frutaYVerdura"
                data-cy="frutaYVerdura"
                check
                value={isNew ? frutaYVerdura : null}
                onChange={isNew ? changefrutaYVerdura : null}
                type="checkbox"
              />
              
              <ValidatedField
                label="Seleccione las Frutas y Verduras del Paquete:"
                id="alimento-de-entrada-frutaYVerdura"
                data-cy="frutaYVerdura"
                type="select"
                multiple
                name="frutaYVerduras"
                className={!isNew || frutaYVerdura ? "":"hide"}
                required={isNew ? frutaYVerdura:null}
              >
                <option value="" key="0" />
                {frutaYVerduras
                  ? frutaYVerduras.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombreAlimento}
                      </option>
                    ))
                  : null}
              </ValidatedField> 
              <ValidatedField
                id="alimento-de-entrada-tipoDeAlimento"
                name="tipoDeAlimento"
                data-cy="tipoDeAlimento"
                label="Tipo De Alimento"
                type="select"
                className={frutaYVerdura ? "hide":""}
                required={isNew ? !frutaYVerdura : null}
              >
                <option value="" key="0"/>
                {tipoDeAlimentos
                  ? tipoDeAlimentos.map(otherEntity => (
                    <option value={otherEntity.id} key={otherEntity.id}>
                      {otherEntity.nombreAlimento}
                    </option>
                  ))
                  : null
                  }
              </ValidatedField> 

              <div className="modal-button-div">
                <Button onClick={showModal} color="warning" className={frutaYVerdura ? "hide":""}>
                    &#10010;
                </Button>
              </div>
              
          
              <TipoAlimentoModal showModal={crearAlimento} handleClose={handleCloseModal}/>

              <ValidatedField
                autoComplete="off"
                label="Peso"
                id="alimento-de-entrada-peso"
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
              <ValidatedField
                label="Fecha Y Hora Entrada"
                id="alimento-de-entrada-fechaYHoraEntrada"
                name="fechaYHoraEntrada"
                data-cy="fechaYHoraEntrada"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                value={isNew ? fechaElegida : null}
                onChange={isNew ? ((e) => setFechaElegida(e.target.value)) : null}
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              {isNew ? <Row>
                <Col className={`option-button-col ${mostrarHoraRecogida ? "hide":""}`} md="6">
                  <button type="button" className="option-button" onClick={() => setMostrarHoraRecogida(!mostrarHoraRecogida)}>Insertar hora de Recogida</button> 
                </Col>
                <Col className={`option-button-col ${mostrarHoraPrep ? "hide":""}`} md="6">
                <button type="button" className="option-button" onClick={() => setMostrarHoraPrep(!mostrarHoraPrep)}>Insertar hora de Preparación</button> 
                </Col>
              </Row>:null}
          
              <ValidatedField
                className={`${isNew ? "campo-opcional ":null }` +` ${(mostrarHoraRecogida || !isNew)? "":"hide" }`}
                label={"Fecha Y Hora Recogida (Opcional)"}
                id="alimento-de-entrada-fechaYHoraRecogida"
                name="fechaYHoraRecogida"
                data-cy="fechaYHoraRecogida"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                value={isNew ? fechaRecogida : null}
                onChange={isNew ? ((e) => setFechaRecogida(e.target.value)) : null}
              />
          
              <ValidatedField
                className={`${isNew ? "campo-opcional ":null }` +` ${(mostrarHoraPrep || !isNew)? "":"hide" }`}
                label="Fecha Y Hora Preparacion (Opcional)"
                id="alimento-de-entrada-fechaYHoraPreparacion"
                name="fechaYHoraPreparacion"
                data-cy="fechaYHoraPreparacion"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                value={isNew ? fechaPrep : null}
                onChange={isNew ? ((e) => setFechaPrep(e.target.value)) : null}
              />
              
              
              <ValidatedField id="alimento-de-entrada-tupper" name="tupper" data-cy="tupper" label="Tupper" type="select" className={frutaYVerdura ? "hide":""} required={isNew ? !frutaYVerdura: null}>
                <option value="" key="0" />
                {tuppers
                  ? tuppers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.modelo}
                      </option>
                    ))
                  : null}
              </ValidatedField> 
              <ValidatedField id="alimento-de-entrada-donante" name="donante" data-cy="donante" label="Donante" type="select" required value={donante} onChange={(e) => setDonante(e.target.value)}>
                <option value="" key="0" />
                {donantes
                  ? donantes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.idDonante} - {otherEntity.nombre}
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

export default AlimentoDeEntradaUpdate;
