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

describe('Intol e2e test', () => {
  const intolPageUrl = '/intol';
  const intolPageUrlPattern = new RegExp('/intol(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const intolSample = { nombre: 'Cataluña Identidad Acero' };

  let intol;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/intols+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/intols').as('postEntityRequest');
    cy.intercept('DELETE', '/api/intols/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (intol) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/intols/${intol.id}`,
      }).then(() => {
        intol = undefined;
      });
    }
  });

  it('Intols menu should load Intols page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('intol');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Intol').should('exist');
    cy.url().should('match', intolPageUrlPattern);
  });

  describe('Intol page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(intolPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Intol page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/intol/new$'));
        cy.getEntityCreateUpdateHeading('Intol');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', intolPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/intols',
          body: intolSample,
        }).then(({ body }) => {
          intol = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/intols+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/intols?page=0&size=20>; rel="last",<http://localhost/api/intols?page=0&size=20>; rel="first"',
              },
              body: [intol],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(intolPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Intol page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('intol');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', intolPageUrlPattern);
      });

      it('edit button click should load edit Intol page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Intol');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', intolPageUrlPattern);
      });

      it('edit button click should load edit Intol page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Intol');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', intolPageUrlPattern);
      });

      it('last delete button click should delete instance of Intol', () => {
        cy.intercept('GET', '/api/intols/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('intol').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', intolPageUrlPattern);

        intol = undefined;
      });
    });
  });

  describe('new Intol page', () => {
    beforeEach(() => {
      cy.visit(`${intolPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Intol');
    });

    it('should create an instance of Intol', () => {
      cy.get(`[data-cy="nombre"]`).type('parsing Negro').should('have.value', 'parsing Negro');

      cy.get(`[data-cy="descripcion"]`).type('invoice compressing').should('have.value', 'invoice compressing');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        intol = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', intolPageUrlPattern);
    });
  });
});
