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

describe('Tupper e2e test', () => {
  const tupperPageUrl = '/tupper';
  const tupperPageUrlPattern = new RegExp('/tupper(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const tupperSample = { peso: 36608, productor: 'Queso', modelo: 'online', precio: 962 };

  let tupper;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/tuppers+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/tuppers').as('postEntityRequest');
    cy.intercept('DELETE', '/api/tuppers/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (tupper) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/tuppers/${tupper.id}`,
      }).then(() => {
        tupper = undefined;
      });
    }
  });

  it('Tuppers menu should load Tuppers page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('tupper');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Tupper').should('exist');
    cy.url().should('match', tupperPageUrlPattern);
  });

  describe('Tupper page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(tupperPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Tupper page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/tupper/new$'));
        cy.getEntityCreateUpdateHeading('Tupper');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', tupperPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/tuppers',
          body: tupperSample,
        }).then(({ body }) => {
          tupper = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/tuppers+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/tuppers?page=0&size=20>; rel="last",<http://localhost/api/tuppers?page=0&size=20>; rel="first"',
              },
              body: [tupper],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(tupperPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Tupper page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('tupper');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', tupperPageUrlPattern);
      });

      it('edit button click should load edit Tupper page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Tupper');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', tupperPageUrlPattern);
      });

      it('edit button click should load edit Tupper page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Tupper');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', tupperPageUrlPattern);
      });

      it('last delete button click should delete instance of Tupper', () => {
        cy.intercept('GET', '/api/tuppers/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('tupper').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', tupperPageUrlPattern);

        tupper = undefined;
      });
    });
  });

  describe('new Tupper page', () => {
    beforeEach(() => {
      cy.visit(`${tupperPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Tupper');
    });

    it('should create an instance of Tupper', () => {
      cy.get(`[data-cy="peso"]`).type('95983').should('have.value', '95983');

      cy.get(`[data-cy="productor"]`).type('Berkshire').should('have.value', 'Berkshire');

      cy.get(`[data-cy="modelo"]`).type('Personal').should('have.value', 'Personal');

      cy.get(`[data-cy="precio"]`).type('29246').should('have.value', '29246');

      cy.get(`[data-cy="descripcion"]`).type('Nacional').should('have.value', 'Nacional');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        tupper = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', tupperPageUrlPattern);
    });
  });
});
