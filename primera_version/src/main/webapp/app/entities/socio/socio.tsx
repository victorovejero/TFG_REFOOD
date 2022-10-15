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

import { ISocio } from 'app/shared/model/socio.model';
import { getEntities, reset } from './socio.reducer';

export const Socio = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const socioList = useAppSelector(state => state.socio.entities);
  const loading = useAppSelector(state => state.socio.loading);
  const totalItems = useAppSelector(state => state.socio.totalItems);
  const links = useAppSelector(state => state.socio.links);
  const entity = useAppSelector(state => state.socio.entity);
  const updateSuccess = useAppSelector(state => state.socio.updateSuccess);

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
      <h2 id="socio-heading" data-cy="SocioHeading">
        <Translate contentKey="refoodTrazabilidadApp.socio.home.title">Socios</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="refoodTrazabilidadApp.socio.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/socio/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="refoodTrazabilidadApp.socio.home.createLabel">Create new Socio</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={socioList ? socioList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {socioList && socioList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="refoodTrazabilidadApp.socio.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('nombreSocio')}>
                    <Translate contentKey="refoodTrazabilidadApp.socio.nombreSocio">Nombre Socio</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('primerApellidoSocio')}>
                    <Translate contentKey="refoodTrazabilidadApp.socio.primerApellidoSocio">Primer Apellido Socio</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('segundoApellidoSocio')}>
                    <Translate contentKey="refoodTrazabilidadApp.socio.segundoApellidoSocio">Segundo Apellido Socio</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('emailSocio')}>
                    <Translate contentKey="refoodTrazabilidadApp.socio.emailSocio">Email Socio</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('telefonoContactoSocio')}>
                    <Translate contentKey="refoodTrazabilidadApp.socio.telefonoContactoSocio">Telefono Contacto Socio</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('dniSocio')}>
                    <Translate contentKey="refoodTrazabilidadApp.socio.dniSocio">Dni Socio</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fechaNacimientoSocio')}>
                    <Translate contentKey="refoodTrazabilidadApp.socio.fechaNacimientoSocio">Fecha Nacimiento Socio</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('sexoSocio')}>
                    <Translate contentKey="refoodTrazabilidadApp.socio.sexoSocio">Sexo Socio</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fechaAltaSocio')}>
                    <Translate contentKey="refoodTrazabilidadApp.socio.fechaAltaSocio">Fecha Alta Socio</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fechaBajaSocio')}>
                    <Translate contentKey="refoodTrazabilidadApp.socio.fechaBajaSocio">Fecha Baja Socio</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('contribucionMensual')}>
                    <Translate contentKey="refoodTrazabilidadApp.socio.contribucionMensual">Contribucion Mensual</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('periodoPago')}>
                    <Translate contentKey="refoodTrazabilidadApp.socio.periodoPago">Periodo Pago</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="refoodTrazabilidadApp.socio.nucleo">Nucleo</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {socioList.map((socio, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/socio/${socio.id}`} color="link" size="sm">
                        {socio.id}
                      </Button>
                    </td>
                    <td>{socio.nombreSocio}</td>
                    <td>{socio.primerApellidoSocio}</td>
                    <td>{socio.segundoApellidoSocio}</td>
                    <td>{socio.emailSocio}</td>
                    <td>{socio.telefonoContactoSocio}</td>
                    <td>{socio.dniSocio}</td>
                    <td>
                      {socio.fechaNacimientoSocio ? (
                        <TextFormat type="date" value={socio.fechaNacimientoSocio} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{socio.sexoSocio}</td>
                    <td>
                      {socio.fechaAltaSocio ? <TextFormat type="date" value={socio.fechaAltaSocio} format={APP_LOCAL_DATE_FORMAT} /> : null}
                    </td>
                    <td>
                      {socio.fechaBajaSocio ? <TextFormat type="date" value={socio.fechaBajaSocio} format={APP_LOCAL_DATE_FORMAT} /> : null}
                    </td>
                    <td>{socio.contribucionMensual}</td>
                    <td>{socio.periodoPago}</td>
                    <td>{socio.nucleo ? <Link to={`/nucleo/${socio.nucleo.id}`}>{socio.nucleo.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/socio/${socio.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/socio/${socio.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/socio/${socio.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
                <Translate contentKey="refoodTrazabilidadApp.socio.home.notFound">No Socios found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Socio;
