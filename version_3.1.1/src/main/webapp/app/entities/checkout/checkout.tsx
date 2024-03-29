import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT, ORDEN_LISTA } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICheckout } from 'app/shared/model/checkout.model';
import { getEntities } from './checkout.reducer';

export const Checkout = () => {

  const [searchState, setSearchState] = useState<string>("");

  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const checkoutList = useAppSelector(state => state.checkout.entities);
  const loading = useAppSelector(state => state.checkout.loading);
  const totalItems = useAppSelector(state => state.checkout.totalItems);

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

  const printList = (i) => {
    let arr = []
    let counter = 0;
    for (const int of checkoutList[i].alSals){ 
      arr[counter] =  int ? (int.alEnt.tipoAl ? <Link key={counter} to={`/al-sal/${int.id}`}>{int.alEnt.tipoAl.nombreAlimento}</Link> : 'fruta y verdura') : ''
      counter++;
      
      arr[counter] = " - "
      counter++;
    }
    return arr.slice(0,-1);
    
  }

  const filterSearch = (value) => {
    setSearchState(value);
  }

  return (
    <div>
      <h2 id="checkout-heading" data-cy="CheckoutHeading">
        Checkouts
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          <Link to="/checkout/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Crear nuevo Checkout
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
        {checkoutList && checkoutList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                {/* <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th> */}
                <th >
                  Fecha Salida 
                </th>
                <th >
                  Peso 
                </th>
                <th>
                  Benef
                </th>
                <th>Alimentos de Salida</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {checkoutList.filter(x => x.benef.idBeneficiario.includes(searchState)).map((checkout, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  {/* <td>
                    <Button tag={Link} to={`/checkout/${checkout.id}`} color="link" size="sm">
                      {checkout.id}
                    </Button>
                  </td> */}
                  <td>
                    {checkout.fechaSalida ? <TextFormat type="date" value={checkout.fechaSalida} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{checkout.peso}</td>
                  <td>{checkout.benef ? <Link to={`/benef/${checkout.benef.id}`}>{checkout.benef.idBeneficiario}</Link> : ''}</td>
                  <td>{printList(i)}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/checkout/${checkout.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/checkout/${checkout.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/checkout/${checkout.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">Ningún Checkouts encontrado</div>
        )}
      </div>
      {totalItems ? (
        <div className={checkoutList && checkoutList.length > 0 ? '' : 'd-none'}>
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

export default Checkout;
