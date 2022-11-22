import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';

import { Row, Col, Alert, Button} from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <>
      
      {account.login ? 
      <>
        <Row>
        <h1 id="home-title">¡Bienvenido, Gracias por Colaborar!</h1>
        </Row>
        <Row>
          <Col md="4" className="home-col">
            <Link to="/alimento-de-entrada/new" className="btn btn-primary home-button">Registrar Alimento de Entrada</Link>
          </Col>
          <Col md="4" className="home-col" >
            <img src="../../../content/images/Logo_Refood.jpg" className="logo signed"></img>
            {/* <span className="hipster rounded" /> */}
          </Col>
          <Col md="4" className="home-col">
            <Link to="/alimento-de-salida/new" className="btn btn-primary home-button">Registrar Alimento de Salida</Link>
            </Col>
        </Row>
        {/* <Row>
        <Col md="6" className="registro-col">
            <Link to="/registro/new" className="btn btn-primary home-button">Registro Diario de Rutas</Link>
          </Col>
          <Col md="4" className="registro-text">
            <p>El registro diario de rutas sirve para saber que voluntarios han hecho cada ruta, únicamente hay que rellenarlo una vez por ruta, al día.</p>
          </Col>
          <Col md="2">
          </Col>
          
        </Row> */}
      </> : 
      <>
      <Row>
          <h1 id="home-title">¡Bienvenido a Refood!</h1>
        </Row>
        <Row>
          <Col md="4" className="home-col logo unsigned">
            <span className="hipster unsigned rounded" />
          </Col>
          <Col md="8" className="home-col text-col unsigned">
            <div className="home-text">
              <h2 className="">Nuestro Objetivo:</h2>
              <p>Refood trabaja cada día para ayudar a familias en necesidad a traer comida a la mesa, a su vez, conseguiendo aportar nuestro granito de arena a conseguir una ciudad más sostenible!</p>
              <br></br>
              <h3>¿Para que sirve esta herramienta?</h3>
              <p>Por la seguridad de nuestros beneficiarios, es muy importante saber de dónde viene, y como se ha tratado la comida que entra y sale del local. Para esto, hemos desarrollado la aplicación de trazabilidad, con el objetivo de que el proceso de anotar toda la información sea mucho mas eficaz!</p>
              
              

              
            </div>
            
          </Col>
        </Row> 
        <Row className="footer unsigned">
        <p className="lead"><span className="footer-title">Síguenos:</span></p>
          <Col md="3">
            <a href="http://refoodesp.org/" target="_blank" rel="noopener noreferrer">
              Página web de Refood
            </a>
          </Col>
          <Col md="1">
            &#x2022;
          </Col>
          <Col md="4">
            <a href="https://www.instagram.com/refoodtetuan/" target="_blank" rel="noopener noreferrer">
              Refood en Instragram
            </a>
          </Col>
          <Col md="1">
            &#x2022;
          </Col>
          <Col md="3">
            <a href="https://twitter.com/refoodmad" target="_blank" rel="noopener noreferrer">
              Refood en Twitter
            </a>
          </Col>
        </Row>
      </>}
      
    
          {/* <p className="lead">Esta es su página de inicio</p>
          {account?.login ? (
            <div>
              <Alert color="success">Está conectado como &quot;{account.login}&quot;.</Alert>
            </div>
          ) : (
            <div>
              <Alert color="warning">
                Si desea
                <span>&nbsp;</span>
                <Link to="/login" className="alert-link">
                  iniciar sesión
                </Link>
                , puede intentar con las cuentas predeterminadas:
                <br />- Administrador (usuario=&quot;admin&quot; y contraseña=&quot;admin&quot;) <br />- Usuario (usuario=&quot;user&quot; y
                contraseña=&quot;user&quot;).
              </Alert>

              <Alert color="warning">
                ¿Aún no tienes una cuenta?&nbsp;
                <Link to="/account/register" className="alert-link">
                  Crea una cuenta
                </Link>
              </Alert>
            </div>
                )} */}
                      
          
            {/* Cambio para poder añadir alimentos de salida y entrada desde el home y que solo sea accesible cuando se ha hecho login. */}
            
            
              
    </>
  );
};

export default Home;
