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

import { IAlEnt } from 'app/shared/model/al-ent.model';
import { getEntities, reset } from './al-ent.reducer';

export const AlEnt = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const alEntList = useAppSelector(state => state.alEnt.entities);
  const loading = useAppSelector(state => state.alEnt.loading);
  const totalItems = useAppSelector(state => state.alEnt.totalItems);
  const links = useAppSelector(state => state.alEnt.links);
  const entity = useAppSelector(state => state.alEnt.entity);
  const updateSuccess = useAppSelector(state => state.alEnt.updateSuccess);

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
      <h2 id="al-ent-heading" data-cy="AlEntHeading">
        Al Ents
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          <Link to="/al-ent/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Crear nuevo Al Ent
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={alEntList ? alEntList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {alEntList && alEntList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('peso')}>
                    Peso <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('frutaYVerdura')}>
                    Fruta Y Verdura <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fechaYHoraEntrada')}>
                    Fecha Y Hora Entrada <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fechaYHoraRecogida')}>
                    Fecha Y Hora Recogida <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fechaYHoraPreparacion')}>
                    Fecha Y Hora Preparacion <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Tupper <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Donante <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Tipo Al <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {alEntList.map((alEnt, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/al-ent/${alEnt.id}`} color="link" size="sm">
                        {alEnt.id}
                      </Button>
                    </td>
                    <td>{alEnt.peso}</td>
                    <td>{alEnt.frutaYVerdura ? 'true' : 'false'}</td>
                    <td>
                      {alEnt.fechaYHoraEntrada ? <TextFormat type="date" value={alEnt.fechaYHoraEntrada} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>
                      {alEnt.fechaYHoraRecogida ? (
                        <TextFormat type="date" value={alEnt.fechaYHoraRecogida} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {alEnt.fechaYHoraPreparacion ? (
                        <TextFormat type="date" value={alEnt.fechaYHoraPreparacion} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{alEnt.tupper ? <Link to={`/tupper/${alEnt.tupper.id}`}>{alEnt.tupper.id}</Link> : ''}</td>
                    <td>{alEnt.donante ? <Link to={`/donante/${alEnt.donante.id}`}>{alEnt.donante.id}</Link> : ''}</td>
                    <td>{alEnt.tipoAl ? <Link to={`/tipo-al/${alEnt.tipoAl.id}`}>{alEnt.tipoAl.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/al-ent/${alEnt.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                        </Button>
                        <Button tag={Link} to={`/al-ent/${alEnt.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                        </Button>
                        <Button tag={Link} to={`/al-ent/${alEnt.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Eliminar</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && <div className="alert alert-warning">Ningún Al Ents encontrado</div>
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default AlEnt;
