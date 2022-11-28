import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBeneficiario } from 'app/shared/model/beneficiario.model';
import { getEntities } from './beneficiario.reducer';

export const Beneficiario = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const beneficiarioList = useAppSelector(state => state.beneficiario.entities);
  const loading = useAppSelector(state => state.beneficiario.loading);
  const totalItems = useAppSelector(state => state.beneficiario.totalItems);

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
      <h2 id="beneficiario-heading" data-cy="BeneficiarioHeading">
        Beneficiarios
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          <Link to="/beneficiario/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Crear nuevo Beneficiario
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {beneficiarioList && beneficiarioList.length > 0 ? (
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
              {beneficiarioList.map((beneficiario, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/beneficiario/${beneficiario.id}`} color="link" size="sm">
                      {beneficiario.id}
                    </Button>
                  </td>
                  <td>{beneficiario.idBeneficiario}</td>
                  <td>{beneficiario.nombreRepresentante}</td>
                  <td>{beneficiario.primerApellidoRepresentante}</td>
                  <td>{beneficiario.segundoApellidoRepresentante}</td>
                  <td>{beneficiario.numeroPersonas}</td>
                  <td>{beneficiario.email}</td>
                  <td>{beneficiario.telefono}</td>
                  <td>{beneficiario.telefonoSecundario}</td>
                  <td>{beneficiario.direccion}</td>
                  <td>{beneficiario.codigoPostal}</td>
                  <td>
                    {beneficiario.fechaAlta ? (
                      <TextFormat type="date" value={beneficiario.fechaAlta} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {beneficiario.fechaBaja ? (
                      <TextFormat type="date" value={beneficiario.fechaBaja} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{beneficiario.numeroNinios}</td>
                  <td>{beneficiario.idDual}</td>
                  <td>{beneficiario.activo ? 'true' : 'false'}</td>
                  <td>{beneficiario.nucleo ? <Link to={`/nucleo/${beneficiario.nucleo.id}`}>{beneficiario.nucleo.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/beneficiario/${beneficiario.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/beneficiario/${beneficiario.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/beneficiario/${beneficiario.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">Ningún Beneficiarios encontrado</div>
        )}
      </div>
      {totalItems ? (
        <div className={beneficiarioList && beneficiarioList.length > 0 ? '' : 'd-none'}>
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

export default Beneficiario;
