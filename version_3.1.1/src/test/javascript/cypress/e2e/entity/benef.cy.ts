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

describe('Benef e2e test', () => {
  const benefPageUrl = '/benef';
  const benefPageUrlPattern = new RegExp('/benef(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const benefSample = {
    idBeneficiario: 'parse Fantástico withdrawal',
    nombreRepresentante: 'Acero communities',
    primerApellidoRepresentante: 'Guapo',
    numeroPersonas: 33190,
    email: 'Gabriela.Vigil41@hotmail.com',
    telefono: 'Azul superestructura killer',
    telefonoSecundario: 'fritas Bebes',
    direccion: 'Card Asistente Berkshire',
    codigoPostal: 'Avon',
    fechaAlta: '2023-01-15',
    numeroNinios: 12427,
    activo: true,
  };

  let benef;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/benefs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/benefs').as('postEntityRequest');
    cy.intercept('DELETE', '/api/benefs/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (benef) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/benefs/${benef.id}`,
      }).then(() => {
        benef = undefined;
      });
    }
  });

  it('Benefs menu should load Benefs page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('benef');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Benef').should('exist');
    cy.url().should('match', benefPageUrlPattern);
  });

  describe('Benef page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(benefPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Benef page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/benef/new$'));
        cy.getEntityCreateUpdateHeading('Benef');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', benefPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/benefs',
          body: benefSample,
        }).then(({ body }) => {
          benef = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/benefs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/benefs?page=0&size=20>; rel="last",<http://localhost/api/benefs?page=0&size=20>; rel="first"',
              },
              body: [benef],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(benefPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Benef page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('benef');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', benefPageUrlPattern);
      });

      it('edit button click should load edit Benef page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Benef');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', benefPageUrlPattern);
      });

      it('edit button click should load edit Benef page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Benef');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', benefPageUrlPattern);
      });

      it('last delete button click should delete instance of Benef', () => {
        cy.intercept('GET', '/api/benefs/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('benef').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', benefPageUrlPattern);

        benef = undefined;
      });
    });
  });

  describe('new Benef page', () => {
    beforeEach(() => {
      cy.visit(`${benefPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Benef');
    });

    it('should create an instance of Benef', () => {
      cy.get(`[data-cy="idBeneficiario"]`).type('Contabilidad real Asistente').should('have.value', 'Contabilidad real Asistente');

      cy.get(`[data-cy="nombreRepresentante"]`).type('EXE').should('have.value', 'EXE');

      cy.get(`[data-cy="primerApellidoRepresentante"]`).type('deposit San').should('have.value', 'deposit San');

      cy.get(`[data-cy="segundoApellidoRepresentante"]`).type('auxiliary').should('have.value', 'auxiliary');

      cy.get(`[data-cy="numeroPersonas"]`).type('68825').should('have.value', '68825');

      cy.get(`[data-cy="email"]`).type('JulioCsar.Leyva67@hotmail.com').should('have.value', 'JulioCsar.Leyva67@hotmail.com');

      cy.get(`[data-cy="telefono"]`).type('Avon').should('have.value', 'Avon');

      cy.get(`[data-cy="telefonoSecundario"]`).type('División Increible').should('have.value', 'División Increible');

      cy.get(`[data-cy="direccion"]`).type('generate').should('have.value', 'generate');

      cy.get(`[data-cy="codigoPostal"]`).type('acceso markets').should('have.value', 'acceso markets');

      cy.get(`[data-cy="fechaAlta"]`).type('2023-01-14').blur().should('have.value', '2023-01-14');

      cy.get(`[data-cy="fechaBaja"]`).type('2023-01-14').blur().should('have.value', '2023-01-14');

      cy.get(`[data-cy="numeroNinios"]`).type('22125').should('have.value', '22125');

      cy.get(`[data-cy="idDual"]`).type('Account Asistente').should('have.value', 'Account Asistente');

      cy.get(`[data-cy="activo"]`).should('not.be.checked');
      cy.get(`[data-cy="activo"]`).click().should('be.checked');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        benef = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', benefPageUrlPattern);
    });
  });
});
