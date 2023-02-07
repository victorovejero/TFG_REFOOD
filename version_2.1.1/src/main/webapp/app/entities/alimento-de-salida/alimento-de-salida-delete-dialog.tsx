import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './alimento-de-salida.reducer';

export const AlimentoDeSalidaDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const alimentoDeSalidaEntity = useAppSelector(state => state.alimentoDeSalida.entity);
  const updateSuccess = useAppSelector(state => state.alimentoDeSalida.updateSuccess);

  const handleClose = () => {
    navigate('/alimento-de-salida' + location.search);
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(alimentoDeSalidaEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="alimentoDeSalidaDeleteDialogHeading">
        Confirmar operación de borrado
      </ModalHeader>
      <ModalBody id="refoodTrazabilidadApp.alimentoDeSalida.delete.question">
        ¿Seguro que quiere eliminar Alimento De Salida {alimentoDeSalidaEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancelar
        </Button>
        <Button id="jhi-confirm-delete-alimentoDeSalida" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp; Eliminar
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default AlimentoDeSalidaDeleteDialog;
