import React, {FC, ReactElement} from 'react'
import {Modal, ModalBody, ModalHeader} from 'reactstrap'
import TipoDeAlimentoUpdate from '../tipo-de-alimento/tipo-de-alimento-update'
interface IModalProps {
  showModal:boolean,
  handleClose: () => void;
};

const TipoAlimentoModal = (props:IModalProps) => {

  const {showModal,handleClose} = props;

  return (
    <Modal isOpen={showModal} toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        Crear un Alimento Nuevo.
      </ModalHeader>
      <ModalBody>
        <TipoDeAlimentoUpdate showTitle={false} submitNavigate={"/alimento-de-entrada/new"}/>
      </ModalBody>
    </Modal>
  )
}

export default TipoAlimentoModal;

