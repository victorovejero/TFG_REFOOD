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

describe('AlEnt e2e test', () => {
  const alEntPageUrl = '/al-ent';
  const alEntPageUrlPattern = new RegExp('/al-ent(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const alEntSample = { peso: 35805, frutaYVerdura: false, fechaYHoraEntrada: '2023-02-15T11:21:24.220Z' };

  let alEnt;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/al-ents+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/al-ents').as('postEntityRequest');
    cy.intercept('DELETE', '/api/al-ents/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (alEnt) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/al-ents/${alEnt.id}`,
      }).then(() => {
        alEnt = undefined;
      });
    }
  });

  it('AlEnts menu should load AlEnts page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('al-ent');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AlEnt').should('exist');
    cy.url().should('match', alEntPageUrlPattern);
  });

  describe('AlEnt page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(alEntPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AlEnt page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/al-ent/new$'));
        cy.getEntityCreateUpdateHeading('AlEnt');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', alEntPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/al-ents',
          body: alEntSample,
        }).then(({ body }) => {
          alEnt = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/al-ents+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/al-ents?page=0&size=20>; rel="last",<http://localhost/api/al-ents?page=0&size=20>; rel="first"',
              },
              body: [alEnt],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(alEntPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details AlEnt page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('alEnt');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', alEntPageUrlPattern);
      });

      it('edit button click should load edit AlEnt page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AlEnt');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', alEntPageUrlPattern);
      });

      it('edit button click should load edit AlEnt page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AlEnt');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', alEntPageUrlPattern);
      });

      it('last delete button click should delete instance of AlEnt', () => {
        cy.intercept('GET', '/api/al-ents/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('alEnt').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', alEntPageUrlPattern);

        alEnt = undefined;
      });
    });
  });

  describe('new AlEnt page', () => {
    beforeEach(() => {
      cy.visit(`${alEntPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AlEnt');
    });

    it('should create an instance of AlEnt', () => {
      cy.get(`[data-cy="peso"]`).type('91331').should('have.value', '91331');

      cy.get(`[data-cy="frutaYVerdura"]`).should('not.be.checked');
      cy.get(`[data-cy="frutaYVerdura"]`).click().should('be.checked');

      cy.get(`[data-cy="fechaYHoraEntrada"]`).type('2023-02-15T11:47').blur().should('have.value', '2023-02-15T11:47');

      cy.get(`[data-cy="fechaYHoraRecogida"]`).type('2023-02-14T18:02').blur().should('have.value', '2023-02-14T18:02');

      cy.get(`[data-cy="fechaYHoraPreparacion"]`).type('2023-02-14T19:54').blur().should('have.value', '2023-02-14T19:54');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        alEnt = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', alEntPageUrlPattern);
    });
  });
});
