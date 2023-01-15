import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAlEnt } from 'app/shared/model/al-ent.model';
import { getEntities } from './al-ent.reducer';

export const AlEnt = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const alEntList = useAppSelector(state => state.alEnt.entities);
  const loading = useAppSelector(state => state.alEnt.loading);
  const totalItems = useAppSelector(state => state.alEnt.totalItems);

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
                    {alEnt.fechaYHoraRecogida ? <TextFormat type="date" value={alEnt.fechaYHoraRecogida} format={APP_DATE_FORMAT} /> : null}
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
                      <Button
                        tag={Link}
                        to={`/al-ent/${alEnt.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/al-ent/${alEnt.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">Ningún Al Ents encontrado</div>
        )}
      </div>
      {totalItems ? (
        <div className={alEntList && alEntList.length > 0 ? '' : 'd-none'}>
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

export default AlEnt;
