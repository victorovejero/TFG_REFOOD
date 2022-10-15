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
        <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.home.title">Alimento De Salidas</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/alimento-de-salida/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.home.createLabel">Create new Alimento De Salida</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {alimentoDeSalidaList && alimentoDeSalidaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('peso')}>
                  <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.peso">Peso</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechaEntrada')}>
                  <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.fechaEntrada">Fecha Entrada</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechaSalida')}>
                  <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.fechaSalida">Fecha Salida</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechaYHoraLog')}>
                  <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.fechaYHoraLog">Fecha Y Hora Log</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechaYHoraPreparacion')}>
                  <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.fechaYHoraPreparacion">Fecha Y Hora Preparacion</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechaYHoraRecogida')}>
                  <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.fechaYHoraRecogida">Fecha Y Hora Recogida</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.tupper">Tupper</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.beneficiario">Beneficiario</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.alimentoDeEntrada">Alimento De Entrada</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.tipoDeAlimento">Tipo De Alimento</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
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
                  <td>{alimentoDeSalida.peso}</td>
                  <td>
                    {alimentoDeSalida.fechaEntrada ? (
                      <TextFormat type="date" value={alimentoDeSalida.fechaEntrada} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {alimentoDeSalida.fechaSalida ? (
                      <TextFormat type="date" value={alimentoDeSalida.fechaSalida} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {alimentoDeSalida.fechaYHoraLog ? (
                      <TextFormat type="date" value={alimentoDeSalida.fechaYHoraLog} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {alimentoDeSalida.fechaYHoraPreparacion ? (
                      <TextFormat type="date" value={alimentoDeSalida.fechaYHoraPreparacion} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {alimentoDeSalida.fechaYHoraRecogida ? (
                      <TextFormat type="date" value={alimentoDeSalida.fechaYHoraRecogida} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {alimentoDeSalida.tupper ? <Link to={`/tupper/${alimentoDeSalida.tupper.id}`}>{alimentoDeSalida.tupper.id}</Link> : ''}
                  </td>
                  <td>
                    {alimentoDeSalida.beneficiario ? (
                      <Link to={`/beneficiario/${alimentoDeSalida.beneficiario.id}`}>{alimentoDeSalida.beneficiario.id}</Link>
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
                      <Link to={`/tipo-de-alimento/${alimentoDeSalida.tipoDeAlimento.id}`}>{alimentoDeSalida.tipoDeAlimento.id}</Link>
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
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/alimento-de-salida/${alimentoDeSalida.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/alimento-de-salida/${alimentoDeSalida.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="refoodTrazabilidadApp.alimentoDeSalida.home.notFound">No Alimento De Salidas found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={alimentoDeSalidaList && alimentoDeSalidaList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
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
