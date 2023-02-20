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

describe('Donante e2e test', () => {
  const donantePageUrl = '/donante';
  const donantePageUrlPattern = new RegExp('/donante(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const donanteSample = {
    idDonante: 'Personal Plástico',
    nombre: 'Account Raton',
    categoria: 'Loan',
    direccion: 'Métricas',
    codigoPostal: 'Mesa backing deposit',
    provincia: 'Cantabria Pasaje Investment',
    telefono: 'programming back-end Cambridgeshire',
    email: 'Jernimo_Venegas@gmail.com',
    responsable: 'tolerancia Prolongación',
    fechaAlta: '2023-02-14',
    activo: true,
  };

  let donante;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/donantes+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/donantes').as('postEntityRequest');
    cy.intercept('DELETE', '/api/donantes/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (donante) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/donantes/${donante.id}`,
      }).then(() => {
        donante = undefined;
      });
    }
  });

  it('Donantes menu should load Donantes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('donante');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Donante').should('exist');
    cy.url().should('match', donantePageUrlPattern);
  });

  describe('Donante page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(donantePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Donante page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/donante/new$'));
        cy.getEntityCreateUpdateHeading('Donante');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', donantePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/donantes',
          body: donanteSample,
        }).then(({ body }) => {
          donante = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/donantes+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/donantes?page=0&size=20>; rel="last",<http://localhost/api/donantes?page=0&size=20>; rel="first"',
              },
              body: [donante],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(donantePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Donante page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('donante');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', donantePageUrlPattern);
      });

      it('edit button click should load edit Donante page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Donante');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', donantePageUrlPattern);
      });

      it('edit button click should load edit Donante page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Donante');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', donantePageUrlPattern);
      });

      it('last delete button click should delete instance of Donante', () => {
        cy.intercept('GET', '/api/donantes/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('donante').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', donantePageUrlPattern);

        donante = undefined;
      });
    });
  });

  describe('new Donante page', () => {
    beforeEach(() => {
      cy.visit(`${donantePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Donante');
    });

    it('should create an instance of Donante', () => {
      cy.get(`[data-cy="idDonante"]`).type('multi-byte Dollar').should('have.value', 'multi-byte Dollar');

      cy.get(`[data-cy="nombre"]`).type('up').should('have.value', 'up');

      cy.get(`[data-cy="categoria"]`).type('Jordania Mascotas').should('have.value', 'Jordania Mascotas');

      cy.get(`[data-cy="direccion"]`).type('División enable superestructura').should('have.value', 'División enable superestructura');

      cy.get(`[data-cy="codigoPostal"]`).type('mobile Lugar Juguetería').should('have.value', 'mobile Lugar Juguetería');

      cy.get(`[data-cy="provincia"]`).type('Horizontal datos').should('have.value', 'Horizontal datos');

      cy.get(`[data-cy="telefono"]`).type('1080p Respuesta').should('have.value', '1080p Respuesta');

      cy.get(`[data-cy="email"]`).type('Ariadna97@hotmail.com').should('have.value', 'Ariadna97@hotmail.com');

      cy.get(`[data-cy="responsable"]`).type('haptic Violeta').should('have.value', 'haptic Violeta');

      cy.get(`[data-cy="fechaAlta"]`).type('2023-02-14').blur().should('have.value', '2023-02-14');

      cy.get(`[data-cy="fechaBaja"]`).type('2023-02-15').blur().should('have.value', '2023-02-15');

      cy.get(`[data-cy="comentarios"]`).type('multiestado Card Balboa').should('have.value', 'multiestado Card Balboa');

      cy.get(`[data-cy="activo"]`).should('not.be.checked');
      cy.get(`[data-cy="activo"]`).click().should('be.checked');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        donante = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', donantePageUrlPattern);
    });
  });
});
