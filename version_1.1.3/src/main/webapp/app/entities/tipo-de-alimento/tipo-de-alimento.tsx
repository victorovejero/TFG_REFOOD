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

import { ITipoDeAlimento } from 'app/shared/model/tipo-de-alimento.model';
import { getEntities, reset } from './tipo-de-alimento.reducer';

export const TipoDeAlimento = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const tipoDeAlimentoList = useAppSelector(state => state.tipoDeAlimento.entities);
  const loading = useAppSelector(state => state.tipoDeAlimento.loading);
  const totalItems = useAppSelector(state => state.tipoDeAlimento.totalItems);
  const links = useAppSelector(state => state.tipoDeAlimento.links);
  const entity = useAppSelector(state => state.tipoDeAlimento.entity);
  const updateSuccess = useAppSelector(state => state.tipoDeAlimento.updateSuccess);

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

  const printList = (i) => {
    let arr = []
    let counter = 0;
    for (const int of tipoDeAlimentoList[i].intolerancias){ 
      arr[counter] =  int ? <Link to={`/intolerancia/${int.id}`}>{int.nombre}</Link> : ''
      counter++;
      
      arr[counter] = " - "
      counter++;
    }
    return arr.slice(0,-1);
    
  }

  return (
    <div>
      <h2 id="tipo-de-alimento-heading" data-cy="TipoDeAlimentoHeading">
        Tipo De Alimentos
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          <Link to="/tipo-de-alimento/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Crear nuevo Tipo De Alimento
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={tipoDeAlimentoList ? tipoDeAlimentoList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {tipoDeAlimentoList && tipoDeAlimentoList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('nombreAlimento')}>
                    Nombre Alimento <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Intolerancias
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {tipoDeAlimentoList.map((tipoDeAlimento, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/tipo-de-alimento/${tipoDeAlimento.id}`} color="link" size="sm">
                        {tipoDeAlimento.id}
                      </Button>
                    </td>
                    <td>{tipoDeAlimento.nombreAlimento}</td>
                    <td>{printList(i)}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/tipo-de-alimento/${tipoDeAlimento.id}`}
                          color="info"
                          size="sm"
                          data-cy="entityDetailsButton"
                        >
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/tipo-de-alimento/${tipoDeAlimento.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/tipo-de-alimento/${tipoDeAlimento.id}/delete`}
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
            !loading && <div className="alert alert-warning">Ningún Tipo De Alimentos encontrado</div>
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default TipoDeAlimento;