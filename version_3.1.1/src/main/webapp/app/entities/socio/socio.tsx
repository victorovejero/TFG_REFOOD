import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISocio } from 'app/shared/model/socio.model';
import { getEntities } from './socio.reducer';

export const Socio = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const socioList = useAppSelector(state => state.socio.entities);
  const loading = useAppSelector(state => state.socio.loading);
  const totalItems = useAppSelector(state => state.socio.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  return (
    <div>
      <h2 id="socio-heading" data-cy="SocioHeading">
        Socios
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          <Link to="/socio/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Crear nuevo Socio
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {socioList && socioList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('nombre')}>
                  Nombre <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('primerApellido')}>
                  Primer Apellido <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('segundoApellido')}>
                  Segundo Apellido <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('email')}>
                  Email <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('telefonoContacto')}>
                  Telefono Contacto <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('iBAN')}>
                  I BAN <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dni')}>
                  Dni <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechaNacimiento')}>
                  Fecha Nacimiento <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sexo')}>
                  Sexo <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechaAlta')}>
                  Fecha Alta <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechaBaja')}>
                  Fecha Baja <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('contribucionMensual')}>
                  Contribucion Mensual <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('periodoPago')}>
                  Periodo Pago <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('activo')}>
                  Activo <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('nucleoAsociado')}>
                  Nucleo Asociado <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('comunicacion')}>
                  Comunicacion <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('direccion')}>
                  Direccion <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('codigoPostal')}>
                  Codigo Postal <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('provincia')}>
                  Provincia <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('pais')}>
                  Pais <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {socioList.map((socio, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/socio/${socio.id}`} color="link" size="sm">
                      {socio.id}
                    </Button>
                  </td>
                  <td>{socio.nombre}</td>
                  <td>{socio.primerApellido}</td>
                  <td>{socio.segundoApellido}</td>
                  <td>{socio.email}</td>
                  <td>{socio.telefonoContacto}</td>
                  <td>{socio.iBAN}</td>
                  <td>{socio.dni}</td>
                  <td>
                    {socio.fechaNacimiento ? <TextFormat type="date" value={socio.fechaNacimiento} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{socio.sexo}</td>
                  <td>{socio.fechaAlta ? <TextFormat type="date" value={socio.fechaAlta} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{socio.fechaBaja ? <TextFormat type="date" value={socio.fechaBaja} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{socio.contribucionMensual}</td>
                  <td>{socio.periodoPago}</td>
                  <td>{socio.activo ? 'true' : 'false'}</td>
                  <td>{socio.nucleoAsociado}</td>
                  <td>{socio.comunicacion ? 'true' : 'false'}</td>
                  <td>{socio.direccion}</td>
                  <td>{socio.codigoPostal}</td>
                  <td>{socio.provincia}</td>
                  <td>{socio.pais}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/socio/${socio.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/socio/${socio.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/socio/${socio.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">Ningún Socios encontrado</div>
        )}
      </div>
      {totalItems ? (
        <div className={socioList && socioList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Socio;
