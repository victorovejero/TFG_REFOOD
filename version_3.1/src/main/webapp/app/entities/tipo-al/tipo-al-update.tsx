import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IIntol } from 'app/shared/model/intol.model';
import { getEntities as getIntols } from 'app/entities/intol/intol.reducer';
import { ITipoAl } from 'app/shared/model/tipo-al.model';
import { getEntity, updateEntity, createEntity, reset } from './tipo-al.reducer';

interface IShowTitle {
  showTitle?:boolean;
  submitNavigate?:string;
}
export const TipoAlUpdate = ({showTitle = true, submitNavigate="/tipo-al"}:IShowTitle) => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const intolerancias = useAppSelector(state => state.intol.entities);
  const tipoAlEntity = useAppSelector(state => state.tipoAl.entity);
  const loading = useAppSelector(state => state.tipoAl.loading);
  const updating = useAppSelector(state => state.tipoAl.updating);
  const updateSuccess = useAppSelector(state => state.tipoAl.updateSuccess);

  const handleClose = () => {
    navigate(submitNavigate);
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getIntols({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      // ...tipoDeAlimentoEntity,
      ...values,
      intolerancias: mapIdList(values.intolerancias),
    };

    if (isNew) {
      console.log(entity);
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...tipoAlEntity,
          intolerancias: tipoAlEntity?.intolerancias?.map(e => e.id.toString()),
        };

  return (
    <div>
      {showTitle ? <Row className="justify-content-center">
        <Col md="8">
          <h2 id="refoodTrazabilidadApp.tipoDeAlimento.home.createOrEditLabel" data-cy="TipoDeAlimentoCreateUpdateHeading">
            Crear o editar Tipo De Alimento
          </h2>
        </Col>
      </Row> : null}
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="tipo-al-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Nombre Alimento"
                id="tipo-al-nombreAlimento"
                name="nombreAlimento"
                data-cy="nombreAlimento"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Fruta Y Verdura"
                id="tipo-al-frutaYVerdura"
                name="frutaYVerdura"
                data-cy="frutaYVerdura"
                check
                type="checkbox"
              />
              <ValidatedField label="Descripcion" id="tipo-al-descripcion" name="descripcion" data-cy="descripcion" type="text" />
              <ValidatedField
                label="Intolerancias"
                id="tipo-al-intolerancia"
                data-cy="intolerancia"
                type="select"
                multiple
                name="intolerancias"
              >
                <option value="" key="0" />
                {intolerancias
                  ? intolerancias.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tipo-al" replace color="info">
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

export default TipoAlUpdate;
