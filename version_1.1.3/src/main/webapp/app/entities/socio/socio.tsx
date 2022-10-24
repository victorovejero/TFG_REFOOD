import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISocio } from 'app/shared/model/socio.model';
import { getEntities, reset } from './socio.reducer';

export const Socio = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const socioList = useAppSelector(state => state.socio.entities);
  const loading = useAppSelector(state => state.socio.loading);
  const totalItems = useAppSelector(state => state.socio.totalItems);
  const links = useAppSelector(state => state.socio.links);
  const entity = useAppSelector(state => state.socio.entity);
  const updateSuccess = useAppSelector(state => state.socio.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
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
        <InfiniteScroll
          dataLength={socioList ? socioList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
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
                  <th>
                    Nucleo <FontAwesomeIcon icon="sort" />
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
                    <td>{socio.dni}</td>
                    <td>
                      {socio.fechaNacimiento ? (
                        <TextFormat type="date" value={socio.fechaNacimiento} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{socio.sexo}</td>
                    <td>{socio.fechaAlta ? <TextFormat type="date" value={socio.fechaAlta} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                    <td>{socio.fechaBaja ? <TextFormat type="date" value={socio.fechaBaja} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                    <td>{socio.contribucionMensual}</td>
                    <td>{socio.periodoPago}</td>
                    <td>{socio.nucleo ? <Link to={`/nucleo/${socio.nucleo.id}`}>{socio.nucleo.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/socio/${socio.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                        </Button>
                        <Button tag={Link} to={`/socio/${socio.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                        </Button>
                        <Button tag={Link} to={`/socio/${socio.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Socio;
