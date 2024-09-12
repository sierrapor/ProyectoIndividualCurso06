// cypress/integration/notificaciones.spec.js

describe('Notificaciones Flow', () => {
  beforeEach(() => {
    // Visita la página principal de astros antes de cada test
    cy.visit('/');
  });

  it('should display a success notification when an astro is created', () => {
    cy.contains('Astros').click();
    cy.get('.create-button').click();
    cy.url().should('include', '/astrosForm');

    // Fill the form with valid data
    cy.get('input#nombre').type('Nuevo Astro');
    cy.get('input#descripcion').type('Descripción del nuevo astro');
    cy.get('input#masa').type('1.989e30');
    cy.get('input#densidad').type('1.408');
    cy.get('input#distancia').type('4.22');
    cy.get('input#magnitudAparente').type('1.46');
    cy.get('input#corrimientoAlRojo').type('0.0001');
    cy.get('input#temperatura').type('5778');
    cy.get('input#edad').type('4.6');
    cy.get('select#tipo').select('Estrella'); // Asegúrate de que el tipo "Estrella" exista

    // Submit the form
    cy.get('button[type="submit"]').click();

    // Check for the success notification
    cy.get('.notification.success').should('contain', 'El astro "Nuevo Astro" ha sido creado correctamente.');
  });

  it('should display a success notification when a tipo is created', () => {
    cy.contains('Tipos').click();
    cy.get('.create-button').click();
    cy.url().should('include', '/tiposForm');

    // Fill the form with valid data
    cy.get('input#nombre').type('Nuevo Tipo');
    cy.get('input#descripcion').type('Descripción del nuevo tipo');
    cy.get('input#luminoso').check();
    cy.get('input#orbitante').check();

    // Submit the form
    cy.get('button[type="submit"]').click();

    // Check for the success notification
    cy.get('.notification.success').should('contain', 'El tipo "Nuevo Tipo" ha sido creado correctamente.');
  });

  it('should display a success notification when an astro is updated', () => {
    cy.contains('Astros').click();

    // Assuming there is at least one astro in the list
    cy.get('tbody tr').first().within(() => {
      cy.get('.edit-button').click();
    });

    // Edit the form with valid data
    cy.get('input#nombre').clear().type('Astro Editado');
    cy.get('input#descripcion').clear().type('Descripción del astro editado');
    cy.get('input#masa').clear().type('1.989e30');
    cy.get('input#densidad').clear().type('1.408');
    cy.get('input#distancia').clear().type('4.22');
    cy.get('input#magnitudAparente').clear().type('1.46');
    cy.get('input#corrimientoAlRojo').clear().type('0.0001');
    cy.get('input#temperatura').clear().type('5778');
    cy.get('input#edad').clear().type('4.6');
    cy.get('select#tipo').select('Estrella'); // Asegúrate de que el tipo "Estrella" exista

    // Submit the form
    cy.get('button[type="submit"]').click();

    // Check for the success notification
    cy.get('.notification.success').should('contain', 'El astro "Astro Editado" ha sido actualizado correctamente.');
  });

  it('should display a success notification when a tipo is updated', () => {
    cy.contains('Tipos').click();

    // Assuming there is at least one tipo in the list
    cy.get('tbody tr').first().within(() => {
      cy.get('.edit-button').click();
    });

    // Edit the form with valid data
    cy.get('input#nombre').clear().type('Tipo Editado');
    cy.get('input#descripcion').clear().type('Descripción del tipo editado');
    cy.get('input#luminoso').uncheck();
    cy.get('input#orbitante').uncheck();

    // Submit the form
    cy.get('button[type="submit"]').click();

    // Check for the success notification
    cy.get('.notification.success').should('contain', 'El tipo "Tipo Editado" ha sido editado correctamente.');
  });

  it('should display a success notification when an astro is deleted', () => {
    cy.contains('Astros').click();

    // Assuming there is at least one astro in the list
    cy.get('tbody tr').first().within(() => {
      cy.get('.delete-button').click();
    });

    // Confirm the deletion
    cy.get('.confirm-button').click();

    // Check for the success notification
    cy.get('.notification.success').should('contain', 'El astro "Astro Editado" ha sido borrado correctamente.');
  });

  it('should display a success notification when a tipo is deleted', () => {
    cy.contains('Tipos').click();

    // Assuming there is at least one tipo in the list
    cy.get('tbody tr').first().within(() => {
      cy.get('.delete-button').click();
    });

    // Confirm the deletion
    cy.get('.confirm-button').click();

    // Check for the success notification
    cy.get('.notification.success').should('contain', 'El tipo "Tipo Editado" ha sido borrado correctamente.');
  });
});