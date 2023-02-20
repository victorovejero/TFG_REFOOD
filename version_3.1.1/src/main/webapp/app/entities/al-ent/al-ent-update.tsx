import React, { useState, useEffect, useRef } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, Alert } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector} from 'app/config/store';



import { ITupper } from 'app/shared/model/tupper.model';
import { getAllEntities as getAllTuppers } from 'app/entities/tupper/tupper.reducer';
import { IDonante } from 'app/shared/model/donante.model';
import { getAllEntities as getDonantes } from 'app/entities/donante/donante.reducer';
import { ITipoAl } from 'app/shared/model/tipo-al.model';
import { getAllEntities as getAllTipoAls } from 'app/entities/tipo-al/tipo-al.reducer';
import { IAlEnt } from 'app/shared/model/al-ent.model';
import { getEntity, updateEntity, createEntity, reset } from './al-ent.reducer';
import {getToday} from 'app/shared/util/date-utils'
import TipoAlModal from './tipo-al-modal';
import './al-ent.css';


export const AlEntUpdate = () => {
  const PESO_MAX = 10;
  const [mostrarHoraPrep,setMostrarHoraPrep] = useState<Boolean>(false);
  const [mostrarHoraRecogida, setMostrarHoraRecogida] = useState<Boolean>(false);
  const [fechaElegida,setFechaElegida] = useState<String>(getToday(true));
  const [fechaRecogida, setFechaRecogida] = useState<String>(getToday(true));
  const [fechaPrep, setFechaPrep] = useState<String>(getToday(true));
  //const frutaYVerdura = useRef<Boolean>(false);
  // const [frutaYVerdura,setFrutaYVerdura] = useState<boolean>(false);
  // const [frutaYVerduraNotNew,setFrutaYVerduraNotNew] = useState<boolean>(false);
  //const renderCount = useRef<number>(0);
  const pesoMaxNotify = useRef<boolean>(false);
  const [showPesoAlert,setShowPesoAlert] = useState<boolean>(false);
  const [crearAlimento, setCrearAlimento] = useState<boolean>(false);
  const [donante, setDonante] = useState(localStorage.getItem("donante-actual") ?? "");

  //Manejo Fruta y Verdura
  const [tipoAl, setTipoAl] = useState("");
  const [frutaYVerdura, setFrutaYVerdura] = useState(false);

  useEffect(() => {
    localStorage.setItem("donante-actual", donante);
  },[donante])

  // const [tiposAlimentoUpdate, setTiposAlimentoUpdate] = useState(undefined);
  const dispatch = useAppDispatch();
  

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  // const frutaYVerduras = useAppSelector(state => state.frutaYVerdura.entities);
  const tuppers = useAppSelector(state => state.tupper.entities);
  const donantes = useAppSelector(state => state.donante.entities);
  const tipoAls = useAppSelector(state => state.tipoAl.entities);
  const alEntEntity = useAppSelector(state => state.alEnt.entity);
  const loading = useAppSelector(state => state.alEnt.loading);
  const updating = useAppSelector(state => state.alEnt.updating);
  const updateSuccess = useAppSelector(state => state.alEnt.updateSuccess);
  // console.log("Tipo de Alimentos " + tipoAls);
  const handleClose = () => {
    location.reload();
    navigate('/al-ent/new' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }


    dispatch(getAllTuppers({}));
    dispatch(getDonantes({}));
    dispatch(getAllTipoAls({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // const getEmptyObject = (name:string) => {
  //   let value;
  //   if(frutaYVerdura){
  //     switch(name){
  //       case "tupper":
  //         value = (): ITupper => ({});
  //       break;
  //       case "tipoAl":
  //         value = () : ITipoAl => ({});
  //         break;
  //     }
  //   }else{
  //     value = () : IFrutaYVerdura => ({});
  //   }

  // }
  
  const saveEntity = values => {
    values.fechaYHoraEntrada = convertDateTimeToServer(values.fechaYHoraEntrada);
    values.fechaYHoraRecogida = convertDateTimeToServer(values.fechaYHoraRecogida);
    values.fechaYHoraPreparacion = convertDateTimeToServer(values.fechaYHoraPreparacion);

    const entity = {
      ...alEntEntity,
      ...values,

      tupper: tuppers.find(it => it.id.toString() === values.tupper.toString()),
      donante: donantes.find(it => it.id.toString() === values.donante.toString()),
      tipoAl: tipoAls.find(it => it.id.toString() === values.tipoAl.toString()),
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
          ...alEntEntity,
          fechaYHoraEntrada: convertDateTimeFromServer(alEntEntity.fechaYHoraEntrada),
          fechaYHoraRecogida: convertDateTimeFromServer(alEntEntity.fechaYHoraRecogida),
          fechaYHoraPreparacion: convertDateTimeFromServer(alEntEntity.fechaYHoraPreparacion),

          tupper: alEntEntity?.tupper?.id,
          donante: alEntEntity?.donante?.id,
          tipoAl: alEntEntity?.tipoAl?.id,
        };
        
 
    // Cambio de estado, para seleccionar frutas y verduras.
    // const changefrutaYVerdura = () => {
    //   setFrutaYVerdura(frutaYVerdura => !frutaYVerdura);
    // }
 
console.log(tipoAl);
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
      navigate("/al-ent/new");
      location.reload();
    }

    //EL FLOW DEL FORMULARIO SOLO PARA ENTRADAS NUEVAS, NO IMPLENENTADO PARA ENTRADAS YA EXISTENTES.
  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="refoodTrazabilidadApp.alEnt.home.createOrEditLabel" data-cy="AlEntCreateUpdateHeading">
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
                <ValidatedField name="id" required readOnly id="al-ent-id" label="ID" validate={{ required: true }} />
              ) : null}


              <label htmlFor="al-ent-fYV">Fruta o Verdura
              <ValidatedField
              id="al-ent-fYV"
              name="fYV"
              data-cy="fYV"
              className="checkbox"
              type="checkbox"
              value={frutaYVerdura}
              onChange={(e) => setFrutaYVerdura(e.target.checked)}>
              </ValidatedField>
              </label>
              <ValidatedField
                id="al-ent-tipoAl"
                name="tipoAl"
                data-cy="tipoAl"
                label="Tipo De Alimento"
                type="select"
                value={frutaYVerdura ? "0":tipoAl}
                onChange={(e) => setTipoAl(e.target.value)}
                required={frutaYVerdura ? false:true}
              >
                <option value="fruta Y Verdura" key="0"></option>
                {tipoAls
                  ? tipoAls.map(otherEntity => (
                    <option value={otherEntity.id} key={otherEntity.id}>
                      {otherEntity.nombreAlimento}
                    </option>
                  ))
                  : null
                  }
              </ValidatedField> 

              <div className="modal-button-div">
                <Button onClick={showModal} color="warning" >
                    &#10010;
                </Button>
              </div>
              
          
              <TipoAlModal showModal={crearAlimento} handleClose={handleCloseModal}/>

              <ValidatedField
                autoComplete="off"
                label="Peso"
                id="al-ent-peso"
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
                id="al-ent-fechaYHoraEntrada"
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
                id="al-ent-fechaYHoraRecogida"
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
                id="al-ent-fechaYHoraPreparacion"
                name="fechaYHoraPreparacion"
                data-cy="fechaYHoraPreparacion"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                value={isNew ? fechaPrep : null}
                onChange={isNew ? ((e) => setFechaPrep(e.target.value)) : null}
              />
              
              
              <ValidatedField id="al-ent-tupper" name="tupper" data-cy="tupper" label="Tupper" type="select" required={true}>
                <option value="" key="0" />
                {tuppers
                  ? tuppers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.modelo}
                      </option>
                    ))
                  : null}
              </ValidatedField> 
              <ValidatedField id="al-ent-donante" name="donante" data-cy="donante" label="Donante" type="select" required value={donante} onChange={(e) => setDonante(e.target.value)}>
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

export default AlEntUpdate;
