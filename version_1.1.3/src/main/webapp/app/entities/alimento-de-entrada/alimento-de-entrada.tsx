import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAlimentoDeEntrada } from 'app/shared/model/alimento-de-entrada.model';
import { getEntities } from './alimento-de-entrada.reducer';

export const AlimentoDeEntrada = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const alimentoDeEntradaList = useAppSelector(state => state.alimentoDeEntrada.entities);
  const loading = useAppSelector(state => state.alimentoDeEntrada.loading);
  const totalItems = useAppSelector(state => state.alimentoDeEntrada.totalItems);

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
      <h2 id="alimento-de-entrada-heading" data-cy="AlimentoDeEntradaHeading">
        Alimento De Entradas
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          <Link
            to="/alimento-de-entrada/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Crear nuevo Alimento De Entrada
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {alimentoDeEntradaList && alimentoDeEntradaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('peso')}>
                  Peso <FontAwesomeIcon icon="sort" />
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
                  Tipo De Alimento <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {alimentoDeEntradaList.map((alimentoDeEntrada, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/alimento-de-entrada/${alimentoDeEntrada.id}`} color="link" size="sm">
                      {alimentoDeEntrada.id}
                    </Button>
                  </td>
                  <td>{alimentoDeEntrada.peso}</td>
                  <td>
                    {alimentoDeEntrada.fechaYHoraEntrada ? (
                      <TextFormat type="date" value={alimentoDeEntrada.fechaYHoraEntrada} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {alimentoDeEntrada.fechaYHoraRecogida ? (
                      <TextFormat type="date" value={alimentoDeEntrada.fechaYHoraRecogida} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {alimentoDeEntrada.fechaYHoraPreparacion ? (
                      <TextFormat type="date" value={alimentoDeEntrada.fechaYHoraPreparacion} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {alimentoDeEntrada.tupper ? (
                      <Link to={`/tupper/${alimentoDeEntrada.tupper.id}`}>{alimentoDeEntrada.tupper.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {alimentoDeEntrada.donante ? (
                      <Link to={`/donante/${alimentoDeEntrada.donante.id}`}>{alimentoDeEntrada.donante.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {alimentoDeEntrada.tipoDeAlimento ? (
                      <Link to={`/tipo-de-alimento/${alimentoDeEntrada.tipoDeAlimento.id}`}>{alimentoDeEntrada.tipoDeAlimento.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/alimento-de-entrada/${alimentoDeEntrada.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/alimento-de-entrada/${alimentoDeEntrada.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/alimento-de-entrada/${alimentoDeEntrada.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">Ningún Alimento De Entradas encontrado</div>
        )}
      </div>
      {totalItems ? (
        <div className={alimentoDeEntradaList && alimentoDeEntradaList.length > 0 ? '' : 'd-none'}>
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

export default AlimentoDeEntrada;
