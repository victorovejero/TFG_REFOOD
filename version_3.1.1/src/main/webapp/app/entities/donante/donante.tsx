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

import { IDonante } from 'app/shared/model/donante.model';
import { getEntities, reset } from './donante.reducer';

export const Donante = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const donanteList = useAppSelector(state => state.donante.entities);
  const loading = useAppSelector(state => state.donante.loading);
  const totalItems = useAppSelector(state => state.donante.totalItems);
  const links = useAppSelector(state => state.donante.links);
  const entity = useAppSelector(state => state.donante.entity);
  const updateSuccess = useAppSelector(state => state.donante.updateSuccess);

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
      <h2 id="donante-heading" data-cy="DonanteHeading">
        Donantes
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          <Link to="/donante/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Crear nuevo Donante
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={donanteList ? donanteList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {donanteList && donanteList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('idDonante')}>
                    Id Donante <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('nombre')}>
                    Nombre <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('categoria')}>
                    Categoria <FontAwesomeIcon icon="sort" />
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
                  <th className="hand" onClick={sort('telefono')}>
                    Telefono <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('email')}>
                    Email <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('responsable')}>
                    Responsable <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fechaAlta')}>
                    Fecha Alta <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fechaBaja')}>
                    Fecha Baja <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('comentarios')}>
                    Comentarios <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('activo')}>
                    Activo <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Nucleo <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {donanteList.map((donante, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/donante/${donante.id}`} color="link" size="sm">
                        {donante.id}
                      </Button>
                    </td>
                    <td>{donante.idDonante}</td>
                    <td>{donante.nombre}</td>
                    <td>{donante.categoria}</td>
                    <td>{donante.direccion}</td>
                    <td>{donante.codigoPostal}</td>
                    <td>{donante.provincia}</td>
                    <td>{donante.telefono}</td>
                    <td>{donante.email}</td>
                    <td>{donante.responsable}</td>
                    <td>
                      {donante.fechaAlta ? <TextFormat type="date" value={donante.fechaAlta} format={APP_LOCAL_DATE_FORMAT} /> : null}
                    </td>
                    <td>
                      {donante.fechaBaja ? <TextFormat type="date" value={donante.fechaBaja} format={APP_LOCAL_DATE_FORMAT} /> : null}
                    </td>
                    <td>{donante.comentarios}</td>
                    <td>{donante.activo ? 'true' : 'false'}</td>
                    <td>{donante.nucleo ? <Link to={`/nucleo/${donante.nucleo.id}`}>{donante.nucleo.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/donante/${donante.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                        </Button>
                        <Button tag={Link} to={`/donante/${donante.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                        </Button>
                        <Button tag={Link} to={`/donante/${donante.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Eliminar</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && <div className="alert alert-warning">Ningún Donantes encontrado</div>
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Donante;
