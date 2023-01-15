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

import { IVoluntario } from 'app/shared/model/voluntario.model';
import { getEntities, reset } from './voluntario.reducer';

export const Voluntario = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const voluntarioList = useAppSelector(state => state.voluntario.entities);
  const loading = useAppSelector(state => state.voluntario.loading);
  const totalItems = useAppSelector(state => state.voluntario.totalItems);
  const links = useAppSelector(state => state.voluntario.links);
  const entity = useAppSelector(state => state.voluntario.entity);
  const updateSuccess = useAppSelector(state => state.voluntario.updateSuccess);

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
      <h2 id="voluntario-heading" data-cy="VoluntarioHeading">
        Voluntarios
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          <Link to="/voluntario/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Crear nuevo Voluntario
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={voluntarioList ? voluntarioList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {voluntarioList && voluntarioList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('idVoluntario')}>
                    Id Voluntario <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('nombre')}>
                    Nombre <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('primerApellido')}>
                    Primer Apellido <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('segundoApellido')}>
                    Segundo Apellido <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('email')}>
                    Email <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('telefonoContacto')}>
                    Telefono Contacto <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('dni')}>
                    Dni <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fechaNacimiento')}>
                    Fecha Nacimiento <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('sexo')}>
                    Sexo <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fechaAlta')}>
                    Fecha Alta <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fechaBaja')}>
                    Fecha Baja <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('categoriaPerfil')}>
                    Categoria Perfil <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('descripcionCategoria')}>
                    Descripcion Categoria <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('diaRefood')}>
                    Dia Refood <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('origen')}>
                    Origen <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('manipuladorAlimentos')}>
                    Manipulador Alimentos <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('direccion')}>
                    Direccion <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('codigoPostal')}>
                    Codigo Postal <FontAwesomeIcon icon="sort" />
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
                {voluntarioList.map((voluntario, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/voluntario/${voluntario.id}`} color="link" size="sm">
                        {voluntario.id}
                      </Button>
                    </td>
                    <td>{voluntario.idVoluntario}</td>
                    <td>{voluntario.nombre}</td>
                    <td>{voluntario.primerApellido}</td>
                    <td>{voluntario.segundoApellido}</td>
                    <td>{voluntario.email}</td>
                    <td>{voluntario.telefonoContacto}</td>
                    <td>{voluntario.dni}</td>
                    <td>
                      {voluntario.fechaNacimiento ? (
                        <TextFormat type="date" value={voluntario.fechaNacimiento} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{voluntario.sexo}</td>
                    <td>
                      {voluntario.fechaAlta ? <TextFormat type="date" value={voluntario.fechaAlta} format={APP_LOCAL_DATE_FORMAT} /> : null}
                    </td>
                    <td>
                      {voluntario.fechaBaja ? <TextFormat type="date" value={voluntario.fechaBaja} format={APP_LOCAL_DATE_FORMAT} /> : null}
                    </td>
                    <td>{voluntario.categoriaPerfil}</td>
                    <td>{voluntario.descripcionCategoria}</td>
                    <td>{voluntario.diaRefood}</td>
                    <td>{voluntario.origen}</td>
                    <td>{voluntario.manipuladorAlimentos ? 'true' : 'false'}</td>
                    <td>{voluntario.direccion}</td>
                    <td>{voluntario.codigoPostal}</td>
                    <td>{voluntario.activo ? 'true' : 'false'}</td>
                    <td>{voluntario.nucleo ? <Link to={`/nucleo/${voluntario.nucleo.id}`}>{voluntario.nucleo.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/voluntario/${voluntario.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                        </Button>
                        <Button tag={Link} to={`/voluntario/${voluntario.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                        </Button>
                        <Button tag={Link} to={`/voluntario/${voluntario.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Eliminar</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && <div className="alert alert-warning">Ningún Voluntarios encontrado</div>
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Voluntario;
