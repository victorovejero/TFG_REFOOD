import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Voluntario e2e test', () => {
  const voluntarioPageUrl = '/voluntario';
  const voluntarioPageUrlPattern = new RegExp('/voluntario(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const voluntarioSample = {
    idVoluntario: 'Informática',
    nombre: 'cultivate Orígenes',
    primerApellido: 'Cantabria',
    email: 'Teodoro_Soliz@gmail.com',
    telefonoContacto: 'Marroquinería',
    fechaNacimiento: '2023-01-14',
    sexo: 'state',
    fechaAlta: '2023-01-15',
    categoriaPerfil: 'compress Creativo',
    diaRefood: 'Cine Guapo',
    manipuladorAlimentos: true,
    direccion: 'cross-platform Ramal Acero',
    codigoPostal: 'Vietnam',
    activo: false,
  };

  let voluntario;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/voluntarios+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/voluntarios').as('postEntityRequest');
    cy.intercept('DELETE', '/api/voluntarios/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (voluntario) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/voluntarios/${voluntario.id}`,
      }).then(() => {
        voluntario = undefined;
      });
    }
  });

  it('Voluntarios menu should load Voluntarios page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('voluntario');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Voluntario').should('exist');
    cy.url().should('match', voluntarioPageUrlPattern);
  });

  describe('Voluntario page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(voluntarioPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Voluntario page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/voluntario/new$'));
        cy.getEntityCreateUpdateHeading('Voluntario');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', voluntarioPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/voluntarios',
          body: voluntarioSample,
        }).then(({ body }) => {
          voluntario = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/voluntarios+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/voluntarios?page=0&size=20>; rel="last",<http://localhost/api/voluntarios?page=0&size=20>; rel="first"',
              },
              body: [voluntario],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(voluntarioPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Voluntario page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('voluntario');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', voluntarioPageUrlPattern);
      });

      it('edit button click should load edit Voluntario page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Voluntario');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', voluntarioPageUrlPattern);
      });

      it('edit button click should load edit Voluntario page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Voluntario');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', voluntarioPageUrlPattern);
      });

      it('last delete button click should delete instance of Voluntario', () => {
        cy.intercept('GET', '/api/voluntarios/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('voluntario').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', voluntarioPageUrlPattern);

        voluntario = undefined;
      });
    });
  });

  describe('new Voluntario page', () => {
    beforeEach(() => {
      cy.visit(`${voluntarioPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Voluntario');
    });

    it('should create an instance of Voluntario', () => {
      cy.get(`[data-cy="idVoluntario"]`).type('Amarillo').should('have.value', 'Amarillo');

      cy.get(`[data-cy="nombre"]`).type('Avon override').should('have.value', 'Avon override');

      cy.get(`[data-cy="primerApellido"]`).type('Solar Joyería').should('have.value', 'Solar Joyería');

      cy.get(`[data-cy="segundoApellido"]`).type('Hogar mission-critical Librería').should('have.value', 'Hogar mission-critical Librería');

      cy.get(`[data-cy="email"]`).type('Hugo_Parra@hotmail.com').should('have.value', 'Hugo_Parra@hotmail.com');

      cy.get(`[data-cy="telefonoContacto"]`).type('architectures Rústico').should('have.value', 'architectures Rústico');

      cy.get(`[data-cy="dni"]`).type('Pizza hard').should('have.value', 'Pizza hard');

      cy.get(`[data-cy="fechaNacimiento"]`).type('2023-01-14').blur().should('have.value', '2023-01-14');

      cy.get(`[data-cy="sexo"]`).type('connect').should('have.value', 'connect');

      cy.get(`[data-cy="fechaAlta"]`).type('2023-01-15').blur().should('have.value', '2023-01-15');

      cy.get(`[data-cy="fechaBaja"]`).type('2023-01-14').blur().should('have.value', '2023-01-14');

      cy.get(`[data-cy="categoriaPerfil"]`).type('iterate Queso').should('have.value', 'iterate Queso');

      cy.get(`[data-cy="descripcionCategoria"]`).type('networks virtual Rioja').should('have.value', 'networks virtual Rioja');

      cy.get(`[data-cy="diaRefood"]`).type('motivadora Silla').should('have.value', 'motivadora Silla');

      cy.get(`[data-cy="origen"]`).type('neural Travesía').should('have.value', 'neural Travesía');

      cy.get(`[data-cy="manipuladorAlimentos"]`).should('not.be.checked');
      cy.get(`[data-cy="manipuladorAlimentos"]`).click().should('be.checked');

      cy.get(`[data-cy="direccion"]`).type('Azul Lugar Aplicaciones').should('have.value', 'Azul Lugar Aplicaciones');

      cy.get(`[data-cy="codigoPostal"]`).type('Cambridgeshire').should('have.value', 'Cambridgeshire');

      cy.get(`[data-cy="activo"]`).should('not.be.checked');
      cy.get(`[data-cy="activo"]`).click().should('be.checked');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        voluntario = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', voluntarioPageUrlPattern);
    });
  });
});
