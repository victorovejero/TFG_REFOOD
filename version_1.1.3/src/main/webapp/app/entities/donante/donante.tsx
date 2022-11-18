import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDonante } from 'app/shared/model/donante.model';
import { getEntities } from './donante.reducer';

import './donante.css';

export const Donante = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const donanteList = useAppSelector(state => state.donante.entities);
  const loading = useAppSelector(state => state.donante.loading);
  const totalItems = useAppSelector(state => state.donante.totalItems);

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
      <h2 id="donante-heading" data-cy="DonanteHeading">
        Donantes
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          <Link to="/donante/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Crear nuevo Donante
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {donanteList && donanteList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                {/* <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th> */}
                <th className="hand" onClick={sort('idDonante')}>
                  Id Donante <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('nombre')}>
                  Nombre <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tipo')}>
                  Tipo <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('ruta')}>
                  Ruta <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('direccion')}>
                  Direccion <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('telefono')}>
                  Telefono <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('email')}>
                  Email <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('responsable')}>
                  Responsable <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechaAlta')}>
                  Fecha Alta <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechaBaja')}>
                  Fecha Baja <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('comentarios')}>
                  Comentarios <FontAwesomeIcon icon="sort" />
                </th>
                {/* Quitamos la columna de activo */}
                {/* <th className="hand" onClick={sort('activo')}>
                  Activo <FontAwesomeIcon icon="sort" />
                </th> */}
                <th>
                  Nucleo <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {donanteList.map((donante, i) => (
                <tr className={donante.activo ? "" : "strikeout"}key={`entity-${i}`} data-cy="entityTable">
                  {/* <td>
                    <Button tag={Link} to={`/donante/${donante.id}`} color="link" size="sm">
                      {donante.id}
                    </Button>
                  </td> */}
                  <td>
                    {donante.activo ? <Button tag={Link} to={`/donante/${donante.id}`} color="link" size="sm">
                      {donante.idDonante}
                    </Button> : "INACTIVO"}
                    </td>
                  <td>{donante.nombre}</td>
                  <td>{donante.tipo}</td>
                  <td>{donante.ruta}</td>
                  <td>{donante.direccion}</td>
                  <td>{donante.telefono}</td>
                  <td>{donante.email}</td>
                  <td>{donante.responsable}</td>
                  <td>{donante.fechaAlta ? <TextFormat type="date" value={donante.fechaAlta} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{donante.fechaBaja ? <TextFormat type="date" value={donante.fechaBaja} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{donante.comentarios}</td>
                  {/* <td>{donante.activo ? 'true' : 'false'}</td> */}
                  <td>{donante.nucleo ? <Link to={`/nucleo/${donante.nucleo.id}`}>{donante.nucleo.nombre}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/donante/${donante.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/donante/${donante.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                      </Button>

                      {/* Quitamos el botón de tirar a la basura para no poder borrer */}

                      {/* <Button
                        tag={Link}
                        to={`/donante/${donante.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Eliminar</span>
                      </Button> */}

                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">Ningún Donantes encontrado</div>
        )}
      </div>
      {totalItems ? (
        <div className={donanteList && donanteList.length > 0 ? '' : 'd-none'}>
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

export default Donante;
