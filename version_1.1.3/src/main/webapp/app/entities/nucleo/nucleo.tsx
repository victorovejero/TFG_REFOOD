import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INucleo } from 'app/shared/model/nucleo.model';
import { getEntities, reset } from './nucleo.reducer';

export const Nucleo = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const nucleoList = useAppSelector(state => state.nucleo.entities);
  const loading = useAppSelector(state => state.nucleo.loading);
  const totalItems = useAppSelector(state => state.nucleo.totalItems);
  const links = useAppSelector(state => state.nucleo.links);
  const entity = useAppSelector(state => state.nucleo.entity);
  const updateSuccess = useAppSelector(state => state.nucleo.updateSuccess);

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
      <h2 id="nucleo-heading" data-cy="NucleoHeading">
        Nucleos
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          <Link to="/nucleo/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Crear nuevo Nucleo
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={nucleoList ? nucleoList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {nucleoList && nucleoList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('nombre')}>
                    Nombre <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('direccion')}>
                    Direccion <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('provincia')}>
                    Provincia <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('responsable')}>
                    Responsable <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('telefono')}>
                    Telefono <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('email')}>
                    Email <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('numeroRutas')}>
                    Numero Rutas <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('activo')}>
                    Activo <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {nucleoList.map((nucleo, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/nucleo/${nucleo.id}`} color="link" size="sm">
                        {nucleo.id}
                      </Button>
                    </td>
                    <td>{nucleo.nombre}</td>
                    <td>{nucleo.direccion}</td>
                    <td>{nucleo.provincia}</td>
                    <td>{nucleo.responsable}</td>
                    <td>{nucleo.telefono}</td>
                    <td>{nucleo.email}</td>
                    <td>{nucleo.numeroRutas}</td>
                    <td>{nucleo.activo ? 'true' : 'false'}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/nucleo/${nucleo.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                        </Button>
                        <Button tag={Link} to={`/nucleo/${nucleo.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                        </Button>
                        <Button tag={Link} to={`/nucleo/${nucleo.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Eliminar</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && <div className="alert alert-warning">Ningún Nucleos encontrado</div>
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Nucleo;
