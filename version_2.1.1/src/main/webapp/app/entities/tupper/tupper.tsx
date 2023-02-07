import React, { useState, useEffect } from 'react';

import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITupper } from 'app/shared/model/tupper.model';
import { getEntities, reset } from './tupper.reducer';

export const Tupper = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const tupperList = useAppSelector(state => state.tupper.entities);
  const loading = useAppSelector(state => state.tupper.loading);
  const totalItems = useAppSelector(state => state.tupper.totalItems);
  const links = useAppSelector(state => state.tupper.links);
  const entity = useAppSelector(state => state.tupper.entity);
  const updateSuccess = useAppSelector(state => state.tupper.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  // FUNCTIONS FOR PAGINATION

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


  //FUNCTIONS FOR INFINITE SCROLL
  // const resetAll = () => {
  //   dispatch(reset());
  //   setPaginationState({
  //     ...paginationState,
  //     activePage: 1,
  //   });
  //   dispatch(getEntities({}));
  // };

  // useEffect(() => {
  //   resetAll();
  // }, []);

  // useEffect(() => {
  //   if (updateSuccess) {
  //     resetAll();
  //   }
  // }, [updateSuccess]);

  // useEffect(() => {
  //   getAllEntities();
  // }, [paginationState.activePage]);

  // const handleLoadMore = () => {
  //   if ((window as any).pageYOffset > 0) {
  //     setPaginationState({
  //       ...paginationState,
  //       activePage: paginationState.activePage + 1,
  //     });
  //   }
  // };

  // useEffect(() => {
  //   if (sorting) {
  //     getAllEntities();
  //     setSorting(false);
  //   }
  // }, [sorting]);

  // const sort = p => () => {
  //   dispatch(reset());
  //   setPaginationState({
  //     ...paginationState,
  //     activePage: 1,
  //     order: paginationState.order === ASC ? DESC : ASC,
  //     sort: p,
  //   });
  //   setSorting(true);
  // };

  // const handleSyncList = () => {
  //   resetAll();
  // };

  return (
    <div>
      <h2 id="tupper-heading" data-cy="TupperHeading">
        Tuppers
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          <Link to="/tupper/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Crear nuevo Tupper
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        
          {tupperList && tupperList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('peso')}>
                    Peso <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('productor')}>
                    Productor <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('modelo')}>
                    Modelo <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('precio')}>
                    Precio <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('descripcion')}>
                    Descripcion <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {tupperList.map((tupper, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/tupper/${tupper.id}`} color="link" size="sm">
                        {tupper.id}
                      </Button>
                    </td>
                    <td>{tupper.peso}</td>
                    <td>{tupper.productor}</td>
                    <td>{tupper.modelo}</td>
                    <td>{tupper.precio}</td>
                    <td>{tupper.descripcion}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/tupper/${tupper.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                        </Button>
                        <Button tag={Link} to={`/tupper/${tupper.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                        </Button>
                        <Button tag={Link} to={`/tupper/${tupper.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Eliminar</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && <div className="alert alert-warning">Ningún Tuppers encontrado</div>
          )}
      </div>
      {totalItems ? (
        <div className={tupperList && tupperList.length > 0 ? '' : 'd-none'}>
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
        </div>) : ""}
    </div>
  );
};

export default Tupper;
