import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPersonaBeneficiaria } from 'app/shared/model/persona-beneficiaria.model';
import { getEntities } from './persona-beneficiaria.reducer';

export const PersonaBeneficiaria = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const personaBeneficiariaList = useAppSelector(state => state.personaBeneficiaria.entities);
  const loading = useAppSelector(state => state.personaBeneficiaria.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="persona-beneficiaria-heading" data-cy="PersonaBeneficiariaHeading">
        Persona Beneficiarias
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          <Link
            to="/persona-beneficiaria/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Crear nuevo Persona Beneficiaria
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {personaBeneficiariaList && personaBeneficiariaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Primer Apellido</th>
                <th>Segundo Apellido</th>
                <th>Fecha Nacimiento</th>
                <th>Sexo</th>
                <th>Parentesco</th>
                <th>Situacion Profesional</th>
                <th>Intolerancia</th>
                <th>Beneficiario</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {personaBeneficiariaList.map((personaBeneficiaria, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/persona-beneficiaria/${personaBeneficiaria.id}`} color="link" size="sm">
                      {personaBeneficiaria.id}
                    </Button>
                  </td>
                  <td>{personaBeneficiaria.nombre}</td>
                  <td>{personaBeneficiaria.primerApellido}</td>
                  <td>{personaBeneficiaria.segundoApellido}</td>
                  <td>{personaBeneficiaria.fechaNacimiento}</td>
                  <td>{personaBeneficiaria.sexo}</td>
                  <td>{personaBeneficiaria.parentesco}</td>
                  <td>{personaBeneficiaria.situacionProfesional}</td>
                  <td>
                    {personaBeneficiaria.intolerancias
                      ? personaBeneficiaria.intolerancias.map((val, j) => (
                          <span key={j}>
                            <Link to={`/intolerancia/${val.id}`}>{val.id}</Link>
                            {j === personaBeneficiaria.intolerancias.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {personaBeneficiaria.beneficiario ? (
                      <Link to={`/beneficiario/${personaBeneficiaria.beneficiario.id}`}>{personaBeneficiaria.beneficiario.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/persona-beneficiaria/${personaBeneficiaria.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/persona-beneficiaria/${personaBeneficiaria.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/persona-beneficiaria/${personaBeneficiaria.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Eliminar</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">Ningún Persona Beneficiarias encontrado</div>
        )}
      </div>
    </div>
  );
};

export default PersonaBeneficiaria;
