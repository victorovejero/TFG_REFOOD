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

describe('Nucleo e2e test', () => {
  const nucleoPageUrl = '/nucleo';
  const nucleoPageUrlPattern = new RegExp('/nucleo(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const nucleoSample = {
    idNucleo: 'Electrónica withdrawal',
    nombre: 'virtual Bedfordshire Algodón',
    direccion: 'parsing',
    codigoPostal: 'Unido',
    provincia: 'and Increible Hormigon',
    responsable: 'División',
    telefono: 'Asistente Acero no-volátil',
    email: 'Vctor92@hotmail.com',
    activo: true,
  };

  let nucleo;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/nucleos+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/nucleos').as('postEntityRequest');
    cy.intercept('DELETE', '/api/nucleos/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (nucleo) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/nucleos/${nucleo.id}`,
      }).then(() => {
        nucleo = undefined;
      });
    }
  });

  it('Nucleos menu should load Nucleos page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('nucleo');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Nucleo').should('exist');
    cy.url().should('match', nucleoPageUrlPattern);
  });

  describe('Nucleo page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(nucleoPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Nucleo page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/nucleo/new$'));
        cy.getEntityCreateUpdateHeading('Nucleo');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', nucleoPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/nucleos',
          body: nucleoSample,
        }).then(({ body }) => {
          nucleo = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/nucleos+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/nucleos?page=0&size=20>; rel="last",<http://localhost/api/nucleos?page=0&size=20>; rel="first"',
              },
              body: [nucleo],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(nucleoPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Nucleo page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('nucleo');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', nucleoPageUrlPattern);
      });

      it('edit button click should load edit Nucleo page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Nucleo');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', nucleoPageUrlPattern);
      });

      it('edit button click should load edit Nucleo page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Nucleo');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', nucleoPageUrlPattern);
      });

      it('last delete button click should delete instance of Nucleo', () => {
        cy.intercept('GET', '/api/nucleos/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('nucleo').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', nucleoPageUrlPattern);

        nucleo = undefined;
      });
    });
  });

  describe('new Nucleo page', () => {
    beforeEach(() => {
      cy.visit(`${nucleoPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Nucleo');
    });

    it('should create an instance of Nucleo', () => {
      cy.get(`[data-cy="idNucleo"]`).type('navigate direccional').should('have.value', 'navigate direccional');

      cy.get(`[data-cy="nombre"]`).type('Música syndicate Senior').should('have.value', 'Música syndicate Senior');

      cy.get(`[data-cy="direccion"]`).type('Distrito transmitting').should('have.value', 'Distrito transmitting');

      cy.get(`[data-cy="codigoPostal"]`).type('Jordanian').should('have.value', 'Jordanian');

      cy.get(`[data-cy="provincia"]`).type('withdrawal aggregate users').should('have.value', 'withdrawal aggregate users');

      cy.get(`[data-cy="responsable"]`).type('Infraestructura Granito').should('have.value', 'Infraestructura Granito');

      cy.get(`[data-cy="telefono"]`).type('Dollar Borders Humano').should('have.value', 'Dollar Borders Humano');

      cy.get(`[data-cy="email"]`).type('Soledad_Toro@gmail.com').should('have.value', 'Soledad_Toro@gmail.com');

      cy.get(`[data-cy="activo"]`).should('not.be.checked');
      cy.get(`[data-cy="activo"]`).click().should('be.checked');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        nucleo = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', nucleoPageUrlPattern);
    });
  });
});
