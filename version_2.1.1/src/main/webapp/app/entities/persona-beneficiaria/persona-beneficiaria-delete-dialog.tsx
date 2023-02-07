import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './persona-beneficiaria.reducer';

export const PersonaBeneficiariaDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const personaBeneficiariaEntity = useAppSelector(state => state.personaBeneficiaria.entity);
  const updateSuccess = useAppSelector(state => state.personaBeneficiaria.updateSuccess);

  const handleClose = () => {
    navigate('/persona-beneficiaria');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(personaBeneficiariaEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="personaBeneficiariaDeleteDialogHeading">
        Confirmar operación de borrado
      </ModalHeader>
      <ModalBody id="refoodTrazabilidadApp.personaBeneficiaria.delete.question">
        ¿Seguro que quiere eliminar Persona Beneficiaria {personaBeneficiariaEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancelar
        </Button>
        <Button id="jhi-confirm-delete-personaBeneficiaria" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp; Eliminar
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default PersonaBeneficiariaDeleteDialog;
