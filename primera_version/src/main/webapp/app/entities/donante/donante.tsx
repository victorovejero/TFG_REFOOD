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
        <Translate contentKey="refoodTrazabilidadApp.donante.home.title">Donantes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="refoodTrazabilidadApp.donante.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/donante/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="refoodTrazabilidadApp.donante.home.createLabel">Create new Donante</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {donanteList && donanteList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="refoodTrazabilidadApp.donante.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('nombreDonante')}>
                  <Translate contentKey="refoodTrazabilidadApp.donante.nombreDonante">Nombre Donante</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tipoDonante')}>
                  <Translate contentKey="refoodTrazabilidadApp.donante.tipoDonante">Tipo Donante</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('ruta')}>
                  <Translate contentKey="refoodTrazabilidadApp.donante.ruta">Ruta</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('direccionDonante')}>
                  <Translate contentKey="refoodTrazabilidadApp.donante.direccionDonante">Direccion Donante</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('telefonoDonante')}>
                  <Translate contentKey="refoodTrazabilidadApp.donante.telefonoDonante">Telefono Donante</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('emailDonante')}>
                  <Translate contentKey="refoodTrazabilidadApp.donante.emailDonante">Email Donante</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('responsableDonante')}>
                  <Translate contentKey="refoodTrazabilidadApp.donante.responsableDonante">Responsable Donante</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechaAlta')}>
                  <Translate contentKey="refoodTrazabilidadApp.donante.fechaAlta">Fecha Alta</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechaBaja')}>
                  <Translate contentKey="refoodTrazabilidadApp.donante.fechaBaja">Fecha Baja</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('comentarios')}>
                  <Translate contentKey="refoodTrazabilidadApp.donante.comentarios">Comentarios</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="refoodTrazabilidadApp.donante.nucleo">Nucleo</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {donanteList.map((donante, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/donante/${donante.id}`} color="link" size="sm">
                      {donante.id}
                    </Button>
                  </td>
                  <td>{donante.nombreDonante}</td>
                  <td>{donante.tipoDonante}</td>
                  <td>{donante.ruta}</td>
                  <td>{donante.direccionDonante}</td>
                  <td>{donante.telefonoDonante}</td>
                  <td>{donante.emailDonante}</td>
                  <td>{donante.responsableDonante}</td>
                  <td>{donante.fechaAlta ? <TextFormat type="date" value={donante.fechaAlta} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{donante.fechaBaja ? <TextFormat type="date" value={donante.fechaBaja} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{donante.comentarios}</td>
                  <td>{donante.nucleo ? <Link to={`/nucleo/${donante.nucleo.id}`}>{donante.nucleo.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/donante/${donante.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/donante/${donante.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/donante/${donante.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="refoodTrazabilidadApp.donante.home.notFound">No Donantes found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={donanteList && donanteList.length > 0 ? '' : 'd-none'}>
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

export default Donante;
