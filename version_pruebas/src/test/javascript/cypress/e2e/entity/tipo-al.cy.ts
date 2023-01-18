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

describe('TipoAl e2e test', () => {
  const tipoAlPageUrl = '/tipo-al';
  const tipoAlPageUrlPattern = new RegExp('/tipo-al(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const tipoAlSample = { nombreAlimento: 'copying Guinea Amarillo', frutaYVerdura: false };

  let tipoAl;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/tipo-als+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/tipo-als').as('postEntityRequest');
    cy.intercept('DELETE', '/api/tipo-als/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (tipoAl) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/tipo-als/${tipoAl.id}`,
      }).then(() => {
        tipoAl = undefined;
      });
    }
  });

  it('TipoAls menu should load TipoAls page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('tipo-al');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('TipoAl').should('exist');
    cy.url().should('match', tipoAlPageUrlPattern);
  });

  describe('TipoAl page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(tipoAlPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create TipoAl page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/tipo-al/new$'));
        cy.getEntityCreateUpdateHeading('TipoAl');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', tipoAlPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/tipo-als',
          body: tipoAlSample,
        }).then(({ body }) => {
          tipoAl = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/tipo-als+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/tipo-als?page=0&size=20>; rel="last",<http://localhost/api/tipo-als?page=0&size=20>; rel="first"',
              },
              body: [tipoAl],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(tipoAlPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details TipoAl page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('tipoAl');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', tipoAlPageUrlPattern);
      });

      it('edit button click should load edit TipoAl page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TipoAl');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', tipoAlPageUrlPattern);
      });

      it('edit button click should load edit TipoAl page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TipoAl');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', tipoAlPageUrlPattern);
      });

      it('last delete button click should delete instance of TipoAl', () => {
        cy.intercept('GET', '/api/tipo-als/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('tipoAl').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', tipoAlPageUrlPattern);

        tipoAl = undefined;
      });
    });
  });

  describe('new TipoAl page', () => {
    beforeEach(() => {
      cy.visit(`${tipoAlPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('TipoAl');
    });

    it('should create an instance of TipoAl', () => {
      cy.get(`[data-cy="nombreAlimento"]`).type('Rioja interactive').should('have.value', 'Rioja interactive');

      cy.get(`[data-cy="frutaYVerdura"]`).should('not.be.checked');
      cy.get(`[data-cy="frutaYVerdura"]`).click().should('be.checked');

      cy.get(`[data-cy="descripcion"]`).type('Bosnia').should('have.value', 'Bosnia');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        tipoAl = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', tipoAlPageUrlPattern);
    });
  });
});
