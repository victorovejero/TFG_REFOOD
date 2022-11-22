import React, { useState, useEffect, useRef } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVoluntario } from 'app/shared/model/voluntario.model';
import { getEntities as getVoluntarios } from 'app/entities/voluntario/voluntario.reducer';
import { INucleo } from 'app/shared/model/nucleo.model';
import { getEntities as getNucleos } from 'app/entities/nucleo/nucleo.reducer';
import { IRegistro } from 'app/shared/model/registro.model';
import { getEntity, updateEntity, createEntity, reset } from './registro.reducer';

export const RegistroUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const voluntarios = useAppSelector(state => state.voluntario.entities);
  const nucleos = useAppSelector(state => state.nucleo.entities).filter(x => x.activo == true);
  const registroEntity = useAppSelector(state => state.registro.entity);
  const loading = useAppSelector(state => state.registro.loading);
  const updating = useAppSelector(state => state.registro.updating);
  const updateSuccess = useAppSelector(state => state.registro.updateSuccess);

  const handleClose = () => {
    navigate('/registro' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getVoluntarios({}));
    dispatch(getNucleos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    console.log(values);
    const entity = {
      ...registroEntity,
      ...values,
      voluntarios: mapIdList(values.voluntarios),
      nucleo: nucleos.find(it => it.id.toString() === values.nucleo.toString()),
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
          ...registroEntity,
          voluntarios: registroEntity?.voluntarios?.map(e => e.id.toString()),
          nucleo: registroEntity?.nucleo?.id,
        };
  
        // SOLO PODER ESCOGER RUTAS DEL NUCLEO PARA EVITAR ERRORES
  // const idNucleo = useRef<Number>();
  // //const [nombreNucleo, setnombreNucleo] = useState<string>();
  // const [numRutas, setNumRutas] = useState<number>(3);
  
  // const numRutasToArray = (numeroRutas) => {
  //   return Array.from(Array(numeroRutas + 1).keys()).slice(1);
  // }

 
  // const nucleoRutasSelector = (idNucleo_) => {
  //   console.log("Value of Select: " + idNucleo_);
  //   //if(!(idNucleo == "")){
  //    // setnombreNucleo(nombreNucleo)
  //     idNucleo.current = idNucleo_;
  //     console.log("Ref Value is " + idNucleo.current)
  //     let numeroRutas = [];
  //     nucleos.map((x,i) => {
  //       numeroRutas[i] = x.numeroRutas;
  //     })
  //     let nucleo = nucleos.map(x => x.id == idNucleo.current)
  //     const indice = nucleo.flatMap((bool, index) => bool ? index : [])
  //     console.log("INDICE: " + indice);
  //     let numRutas_ = numeroRutas[indice];
  //     console.log(nucleo);
  //     console.log(numeroRutas);
  //     console.log("State numRutas: " + numRutas_);
  //     console.log("State nombreNucleo: " + idNucleo.current);
  //     setNumRutas(numRutas_);
      
  //   //}else{
  //     //idNucleo.current = 1;
  //     // setnombreNucleo("");
  //     //setNumRutas(3);
  //   //}  
  // }

  
  //FIN DE SELECCION DE RUTAS DEL NUCLEO
  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="refoodTrazabilidadApp.registro.home.createOrEditLabel" data-cy="RegistroCreateUpdateHeading">
            Crear o editar Registro
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {/* NO QUEREMOS MOSTRAR EL ID AUTOGENERADO */}
              {/* {!isNew ? <ValidatedField name="id" required readOnly id="registro-id" label="ID" validate={{ required: true }} /> : null} */}
              <ValidatedField
                label="Dia Actividad"
                id="registro-diaActividad"
                name="diaActividad"
                data-cy="diaActividad"
                type="date"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Ruta"
                id="registro-ruta"
                name="ruta"
                data-cy="ruta"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}>
                  {/* {numRutasToArray(numRutas).map(i => (
                    <option key={i} value={i}>
                      Ruta {i}
                    </option>
                  ))
                  } */}

                  </ValidatedField>
                  
              
              <ValidatedField label="Voluntario" id="registro-voluntario" data-cy="voluntario" type="select" multiple name="voluntarios">
                <option value="" key="0" />
                {voluntarios
                  ? voluntarios.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombre} {otherEntity.primerApellido} {otherEntity.segundoApellido}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="registro-nucleo" name="nucleo" data-cy="nucleo" label="Nucleo" type="select"  validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  //onChange={(e) => nucleoRutasSelector(e.target.value)}
                  //value={idNucleo.current}
                }}>
                  
                <option value="" key="0"/>
                {nucleos
                  ? nucleos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombre}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/registro" replace color="info">
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

export default RegistroUpdate;
