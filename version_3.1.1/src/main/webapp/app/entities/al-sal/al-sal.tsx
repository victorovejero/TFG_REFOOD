import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT, ORDEN_LISTA } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAlSal } from 'app/shared/model/al-sal.model';
import { getEntities } from './al-sal.reducer';

export const AlSal = () => {
  const [searchState, setSearchState] = useState<string>("");

  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const alSalList = useAppSelector(state => state.alSal.entities);
  const loading = useAppSelector(state => state.alSal.loading);
  const totalItems = useAppSelector(state => state.alSal.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${ORDEN_LISTA}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${ORDEN_LISTA}`;
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

  const filterSearch = (value) => {
    setSearchState(value);
  }
  return (
    <div>
      <h2 id="al-sal-heading" data-cy="AlSalHeading">
        Alimentos de Salida
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          <Link to="/al-sal/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Crear nuevo Alimento de Salida
          </Link>
        </div>
      </h2>
      <div className="search-salida">
        <label className="search-salida" htmlFor="search">
          Buscar Alimento por Beneficiario: &nbsp;
        </label>
        <input id="search" type="text" placeholder="e.g. B-22 o 22" value={searchState} onChange={(e) => filterSearch(e.target.value)}/>
      </div>
      <div className="table-responsive">
        {alSalList && alSalList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                {/* <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th> */}
                <th>
                  Fecha de Salida 
                </th>
                <th>
                  Tupper 
                </th>
                <th>
                  Beneficiario 
                </th>
                <th>
                  Alimento de Entrada 
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {alSalList.filter(x => x.benef.idBeneficiario.includes(searchState)).map((alSal, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  {/* <td>
                    <Button tag={Link} to={`/al-sal/${alSal.id}`} color="link" size="sm">
                      {alSal.id}
                    </Button>
                  </td> */}
                  <td>{alSal.fechaSalida ? <TextFormat type="date" value={alSal.fechaSalida} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{alSal.tupper ? <Link to={`/tupper/${alSal.tupper.id}`}>{alSal.tupper.modelo}</Link> : ''}</td>
                  <td>{alSal.benef ? <Link to={`/benef/${alSal.benef.id}`}>{alSal.benef.idBeneficiario}</Link> : ''}</td>
                  <td>{alSal.alEnt ? <Link to={`/al-ent/${alSal.alEnt.id}`}>{alSal.alEnt.tipoAl.nombreAlimento}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/al-sal/${alSal.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/al-sal/${alSal.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/al-sal/${alSal.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">Ningún Al Sals encontrado</div>
        )}
      </div>
      {totalItems ? (
        <div className={alSalList && alSalList.length > 0 ? '' : 'd-none'}>
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

export default AlSal;
