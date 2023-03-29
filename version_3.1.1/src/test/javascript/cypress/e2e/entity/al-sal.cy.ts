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

describe('AlSal e2e test', () => {
  const alSalPageUrl = '/al-sal';
  const alSalPageUrlPattern = new RegExp('/al-sal(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const alSalSample = { fechaSalida: '2023-02-15' };

  let alSal;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/al-sals+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/al-sals').as('postEntityRequest');
    cy.intercept('DELETE', '/api/al-sals/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (alSal) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/al-sals/${alSal.id}`,
      }).then(() => {
        alSal = undefined;
      });
    }
  });

  it('AlSals menu should load AlSals page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('al-sal');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AlSal').should('exist');
    cy.url().should('match', alSalPageUrlPattern);
  });

  describe('AlSal page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(alSalPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AlSal page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/al-sal/new$'));
        cy.getEntityCreateUpdateHeading('AlSal');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', alSalPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/al-sals',
          body: alSalSample,
        }).then(({ body }) => {
          alSal = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/al-sals+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/al-sals?page=0&size=20>; rel="last",<http://localhost/api/al-sals?page=0&size=20>; rel="first"',
              },
              body: [alSal],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(alSalPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details AlSal page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('alSal');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', alSalPageUrlPattern);
      });

      it('edit button click should load edit AlSal page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AlSal');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', alSalPageUrlPattern);
      });

      it('edit button click should load edit AlSal page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AlSal');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', alSalPageUrlPattern);
      });

      it('last delete button click should delete instance of AlSal', () => {
        cy.intercept('GET', '/api/al-sals/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('alSal').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', alSalPageUrlPattern);

        alSal = undefined;
      });
    });
  });

  describe('new AlSal page', () => {
    beforeEach(() => {
      cy.visit(`${alSalPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AlSal');
    });

    it('should create an instance of AlSal', () => {
      cy.get(`[data-cy="fechaSalida"]`).type('2023-02-15').blur().should('have.value', '2023-02-15');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        alSal = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', alSalPageUrlPattern);
    });
  });
});
