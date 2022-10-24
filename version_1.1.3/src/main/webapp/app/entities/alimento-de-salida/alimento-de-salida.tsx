import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAlimentoDeSalida } from 'app/shared/model/alimento-de-salida.model';
import { getEntities } from './alimento-de-salida.reducer';

export const AlimentoDeSalida = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const alimentoDeSalidaList = useAppSelector(state => state.alimentoDeSalida.entities);
  const loading = useAppSelector(state => state.alimentoDeSalida.loading);
  const totalItems = useAppSelector(state => state.alimentoDeSalida.totalItems);

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
      <h2 id="alimento-de-salida-heading" data-cy="AlimentoDeSalidaHeading">
        Alimento De Salidas
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          <Link
            to="/alimento-de-salida/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Crear nuevo Alimento De Salida
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {alimentoDeSalidaList && alimentoDeSalidaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('peso')}>
                  Peso <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechaSalida')}>
                  Fecha Salida <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Tupper <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Beneficiario <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Alimento De Entrada <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Tipo De Alimento <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {alimentoDeSalidaList.map((alimentoDeSalida, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/alimento-de-salida/${alimentoDeSalida.id}`} color="link" size="sm">
                      {alimentoDeSalida.id}
                    </Button>
                  </td>
                  <td>{alimentoDeSalida.peso} kg</td>
                  <td>
                    {alimentoDeSalida.fechaSalida ? (
                      <TextFormat type="date" value={alimentoDeSalida.fechaSalida} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {alimentoDeSalida.tupper ? <Link to={`/tupper/${alimentoDeSalida.tupper.id}`}>{alimentoDeSalida.tupper.modelo}</Link> : ''}
                  </td>
                  <td>
                    {alimentoDeSalida.beneficiario ? (
                      <Link to={`/beneficiario/${alimentoDeSalida.beneficiario.id}`}>{alimentoDeSalida.beneficiario.nombre}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {alimentoDeSalida.alimentoDeEntrada ? (
                      <Link to={`/alimento-de-entrada/${alimentoDeSalida.alimentoDeEntrada.id}`}>
                        {alimentoDeSalida.alimentoDeEntrada.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {alimentoDeSalida.tipoDeAlimento ? (
                      <Link to={`/tipo-de-alimento/${alimentoDeSalida.tipoDeAlimento.id}`}>{alimentoDeSalida.tipoDeAlimento.nombreAlimento}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/alimento-de-salida/${alimentoDeSalida.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/alimento-de-salida/${alimentoDeSalida.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/alimento-de-salida/${alimentoDeSalida.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">Ningún Alimento De Salidas encontrado</div>
        )}
      </div>
      {totalItems ? (
        <div className={alimentoDeSalidaList && alimentoDeSalidaList.length > 0 ? '' : 'd-none'}>
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

export default AlimentoDeSalida;
