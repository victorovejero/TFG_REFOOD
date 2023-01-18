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

import { IBenef } from 'app/shared/model/benef.model';
import { getEntities, reset } from './benef.reducer';

export const Benef = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const benefList = useAppSelector(state => state.benef.entities);
  const loading = useAppSelector(state => state.benef.loading);
  const totalItems = useAppSelector(state => state.benef.totalItems);
  const links = useAppSelector(state => state.benef.links);
  const entity = useAppSelector(state => state.benef.entity);
  const updateSuccess = useAppSelector(state => state.benef.updateSuccess);

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
      <h2 id="benef-heading" data-cy="BenefHeading">
        Benefs
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          <Link to="/benef/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Crear nuevo Benef
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={benefList ? benefList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {benefList && benefList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('idBeneficiario')}>
                    Id Beneficiario <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('nombreRepresentante')}>
                    Nombre Representante <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('primerApellidoRepresentante')}>
                    Primer Apellido Representante <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('segundoApellidoRepresentante')}>
                    Segundo Apellido Representante <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('numeroPersonas')}>
                    Numero Personas <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('email')}>
                    Email <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('telefono')}>
                    Telefono <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('telefonoSecundario')}>
                    Telefono Secundario <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('direccion')}>
                    Direccion <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('codigoPostal')}>
                    Codigo Postal <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fechaAlta')}>
                    Fecha Alta <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fechaBaja')}>
                    Fecha Baja <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('numeroNinios')}>
                    Numero Ninios <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('idDual')}>
                    Id Dual <FontAwesomeIcon icon="sort" />
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
                {benefList.map((benef, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/benef/${benef.id}`} color="link" size="sm">
                        {benef.id}
                      </Button>
                    </td>
                    <td>{benef.idBeneficiario}</td>
                    <td>{benef.nombreRepresentante}</td>
                    <td>{benef.primerApellidoRepresentante}</td>
                    <td>{benef.segundoApellidoRepresentante}</td>
                    <td>{benef.numeroPersonas}</td>
                    <td>{benef.email}</td>
                    <td>{benef.telefono}</td>
                    <td>{benef.telefonoSecundario}</td>
                    <td>{benef.direccion}</td>
                    <td>{benef.codigoPostal}</td>
                    <td>{benef.fechaAlta ? <TextFormat type="date" value={benef.fechaAlta} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                    <td>{benef.fechaBaja ? <TextFormat type="date" value={benef.fechaBaja} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                    <td>{benef.numeroNinios}</td>
                    <td>{benef.idDual}</td>
                    <td>{benef.activo ? 'true' : 'false'}</td>
                    <td>{benef.nucleo ? <Link to={`/nucleo/${benef.nucleo.id}`}>{benef.nucleo.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/benef/${benef.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                        </Button>
                        <Button tag={Link} to={`/benef/${benef.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                        </Button>
                        <Button tag={Link} to={`/benef/${benef.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Eliminar</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && <div className="alert alert-warning">Ningún Benefs encontrado</div>
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Benef;
