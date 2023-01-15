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

describe('Socio e2e test', () => {
  const socioPageUrl = '/socio';
  const socioPageUrlPattern = new RegExp('/socio(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const socioSample = {
    nombre: 'withdrawal cross-media International',
    primerApellido: 'XML Sorprendente',
    email: 'Javier77@yahoo.com',
    telefonoContacto: 'Cantabria',
    iBAN: 'Música mindshare',
    dni: 'Salud Comunidad',
    fechaNacimiento: '2023-01-14',
    sexo: 'funcionalidad circuito Money',
    fechaAlta: '2023-01-14',
    contribucionMensual: 15297,
    periodoPago: 'Metal PNG Datos',
    activo: false,
    comunicacion: false,
    direccion: 'Cataluña e-services Castilla',
    codigoPostal: 'Técnico intranet superestructura',
  };

  let socio;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/socios+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/socios').as('postEntityRequest');
    cy.intercept('DELETE', '/api/socios/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (socio) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/socios/${socio.id}`,
      }).then(() => {
        socio = undefined;
      });
    }
  });

  it('Socios menu should load Socios page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('socio');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Socio').should('exist');
    cy.url().should('match', socioPageUrlPattern);
  });

  describe('Socio page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(socioPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Socio page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/socio/new$'));
        cy.getEntityCreateUpdateHeading('Socio');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', socioPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/socios',
          body: socioSample,
        }).then(({ body }) => {
          socio = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/socios+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/socios?page=0&size=20>; rel="last",<http://localhost/api/socios?page=0&size=20>; rel="first"',
              },
              body: [socio],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(socioPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Socio page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('socio');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', socioPageUrlPattern);
      });

      it('edit button click should load edit Socio page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Socio');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', socioPageUrlPattern);
      });

      it('edit button click should load edit Socio page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Socio');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', socioPageUrlPattern);
      });

      it('last delete button click should delete instance of Socio', () => {
        cy.intercept('GET', '/api/socios/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('socio').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', socioPageUrlPattern);

        socio = undefined;
      });
    });
  });

  describe('new Socio page', () => {
    beforeEach(() => {
      cy.visit(`${socioPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Socio');
    });

    it('should create an instance of Socio', () => {
      cy.get(`[data-cy="nombre"]`).type('Qatari index').should('have.value', 'Qatari index');

      cy.get(`[data-cy="primerApellido"]`).type('XSS Navarra experiences').should('have.value', 'XSS Navarra experiences');

      cy.get(`[data-cy="segundoApellido"]`).type('Sorprendente').should('have.value', 'Sorprendente');

      cy.get(`[data-cy="email"]`).type('Claudio.Mondragn49@yahoo.com').should('have.value', 'Claudio.Mondragn49@yahoo.com');

      cy.get(`[data-cy="telefonoContacto"]`).type('La Perseverando').should('have.value', 'La Perseverando');

      cy.get(`[data-cy="iBAN"]`).type('Borders Silver').should('have.value', 'Borders Silver');

      cy.get(`[data-cy="dni"]`).type('y').should('have.value', 'y');

      cy.get(`[data-cy="fechaNacimiento"]`).type('2023-01-14').blur().should('have.value', '2023-01-14');

      cy.get(`[data-cy="sexo"]`).type('overriding').should('have.value', 'overriding');

      cy.get(`[data-cy="fechaAlta"]`).type('2023-01-14').blur().should('have.value', '2023-01-14');

      cy.get(`[data-cy="fechaBaja"]`).type('2023-01-15').blur().should('have.value', '2023-01-15');

      cy.get(`[data-cy="contribucionMensual"]`).type('38493').should('have.value', '38493');

      cy.get(`[data-cy="periodoPago"]`).type('Aragón Innovador').should('have.value', 'Aragón Innovador');

      cy.get(`[data-cy="activo"]`).should('not.be.checked');
      cy.get(`[data-cy="activo"]`).click().should('be.checked');

      cy.get(`[data-cy="nucleoAsociado"]`).type('mano Negro').should('have.value', 'mano Negro');

      cy.get(`[data-cy="comunicacion"]`).should('not.be.checked');
      cy.get(`[data-cy="comunicacion"]`).click().should('be.checked');

      cy.get(`[data-cy="direccion"]`).type('Venezuela').should('have.value', 'Venezuela');

      cy.get(`[data-cy="codigoPostal"]`).type('Nepal').should('have.value', 'Nepal');

      cy.get(`[data-cy="provincia"]`).type('productize').should('have.value', 'productize');

      cy.get(`[data-cy="pais"]`).type('Marroquinería').should('have.value', 'Marroquinería');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        socio = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', socioPageUrlPattern);
    });
  });
});
