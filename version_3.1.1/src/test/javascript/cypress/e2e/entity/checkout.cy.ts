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

describe('Checkout e2e test', () => {
  const checkoutPageUrl = '/checkout';
  const checkoutPageUrlPattern = new RegExp('/checkout(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const checkoutSample = { fechaSalida: '2023-02-14', peso: 76511 };

  let checkout;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/checkouts+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/checkouts').as('postEntityRequest');
    cy.intercept('DELETE', '/api/checkouts/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (checkout) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/checkouts/${checkout.id}`,
      }).then(() => {
        checkout = undefined;
      });
    }
  });

  it('Checkouts menu should load Checkouts page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('checkout');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Checkout').should('exist');
    cy.url().should('match', checkoutPageUrlPattern);
  });

  describe('Checkout page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(checkoutPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Checkout page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/checkout/new$'));
        cy.getEntityCreateUpdateHeading('Checkout');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', checkoutPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/checkouts',
          body: checkoutSample,
        }).then(({ body }) => {
          checkout = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/checkouts+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/checkouts?page=0&size=20>; rel="last",<http://localhost/api/checkouts?page=0&size=20>; rel="first"',
              },
              body: [checkout],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(checkoutPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Checkout page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('checkout');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', checkoutPageUrlPattern);
      });

      it('edit button click should load edit Checkout page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Checkout');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', checkoutPageUrlPattern);
      });

      it('edit button click should load edit Checkout page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Checkout');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', checkoutPageUrlPattern);
      });

      it('last delete button click should delete instance of Checkout', () => {
        cy.intercept('GET', '/api/checkouts/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('checkout').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', checkoutPageUrlPattern);

        checkout = undefined;
      });
    });
  });

  describe('new Checkout page', () => {
    beforeEach(() => {
      cy.visit(`${checkoutPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Checkout');
    });

    it('should create an instance of Checkout', () => {
      cy.get(`[data-cy="fechaSalida"]`).type('2023-02-15').blur().should('have.value', '2023-02-15');

      cy.get(`[data-cy="peso"]`).type('90544').should('have.value', '90544');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        checkout = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', checkoutPageUrlPattern);
    });
  });
});
