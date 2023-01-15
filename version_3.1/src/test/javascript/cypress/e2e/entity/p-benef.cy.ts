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

describe('PBenef e2e test', () => {
  const pBenefPageUrl = '/p-benef';
  const pBenefPageUrlPattern = new RegExp('/p-benef(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const pBenefSample = {
    nombre: 'Música Música',
    primerApellido: 'la Seguro open-source',
    fechaNacimiento: 'sensor Rioja',
    sexo: 'XSS Croacia Masía',
    parentesco: 'frictionless Especialista',
    situacionProfesional: 'Blanco',
  };

  let pBenef;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/p-benefs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/p-benefs').as('postEntityRequest');
    cy.intercept('DELETE', '/api/p-benefs/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (pBenef) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/p-benefs/${pBenef.id}`,
      }).then(() => {
        pBenef = undefined;
      });
    }
  });

  it('PBenefs menu should load PBenefs page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('p-benef');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PBenef').should('exist');
    cy.url().should('match', pBenefPageUrlPattern);
  });

  describe('PBenef page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(pBenefPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PBenef page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/p-benef/new$'));
        cy.getEntityCreateUpdateHeading('PBenef');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', pBenefPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/p-benefs',
          body: pBenefSample,
        }).then(({ body }) => {
          pBenef = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/p-benefs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/p-benefs?page=0&size=20>; rel="last",<http://localhost/api/p-benefs?page=0&size=20>; rel="first"',
              },
              body: [pBenef],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(pBenefPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PBenef page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('pBenef');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', pBenefPageUrlPattern);
      });

      it('edit button click should load edit PBenef page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PBenef');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', pBenefPageUrlPattern);
      });

      it('edit button click should load edit PBenef page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PBenef');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', pBenefPageUrlPattern);
      });

      it('last delete button click should delete instance of PBenef', () => {
        cy.intercept('GET', '/api/p-benefs/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('pBenef').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', pBenefPageUrlPattern);

        pBenef = undefined;
      });
    });
  });

  describe('new PBenef page', () => {
    beforeEach(() => {
      cy.visit(`${pBenefPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PBenef');
    });

    it('should create an instance of PBenef', () => {
      cy.get(`[data-cy="nombre"]`).type('Calleja').should('have.value', 'Calleja');

      cy.get(`[data-cy="primerApellido"]`).type('SMTP collaborative alarm').should('have.value', 'SMTP collaborative alarm');

      cy.get(`[data-cy="segundoApellido"]`).type('Nakfa tangible').should('have.value', 'Nakfa tangible');

      cy.get(`[data-cy="fechaNacimiento"]`).type('cross-platform deposit Guapo').should('have.value', 'cross-platform deposit Guapo');

      cy.get(`[data-cy="sexo"]`).type('Obligatorio superestructura online').should('have.value', 'Obligatorio superestructura online');

      cy.get(`[data-cy="parentesco"]`).type('virtual').should('have.value', 'virtual');

      cy.get(`[data-cy="situacionProfesional"]`).type('Regional Franc grow').should('have.value', 'Regional Franc grow');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        pBenef = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', pBenefPageUrlPattern);
    });
  });
});
