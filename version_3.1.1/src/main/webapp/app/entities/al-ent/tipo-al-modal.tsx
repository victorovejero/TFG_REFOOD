import React, {FC, ReactElement} from 'react'
import {Modal, ModalBody, ModalHeader} from 'reactstrap'
import TipoAlUpdate from '../tipo-al/tipo-al-update'
interface IModalProps {
  showModal:boolean,
  handleClose: () => void;
};

const TipoAlModal = (props:IModalProps) => {

  const {showModal,handleClose} = props;

  return (
    <Modal isOpen={showModal} toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        Crear un Alimento Nuevo.
      </ModalHeader>
      <ModalBody>
        <TipoAlUpdate showTitle={false} submitNavigate={"/al-ent/new"}/>
      </ModalBody>
    </Modal>
  )
}

export default TipoAlModal;

