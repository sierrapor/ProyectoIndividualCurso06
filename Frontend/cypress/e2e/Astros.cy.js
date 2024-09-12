// cypress/integration/astros.spec.js

describe('Astros Flow', () => {
  beforeEach(() => {
    // Visita la página principal de astros antes de cada test
    cy.visit('/astros');
  });

  // Crea un tipo para poder seleccionarlo posteriormente
  it('should create a new tipo', () => {
    cy.visit('/tipos');
    // Click en el botón "Crear"
    cy.get('.create-button').click();

    // Verifica que estamos en la página de creación
    cy.url().should('include', '/tiposForm');

    // Rellena el formulario
    cy.get('#nombre').type('Test');
    cy.get('#descripcion').type('Descripción testeada');
    cy.get('#luminoso').check();
    cy.get('#orbitante').check();

    // Envía el formulario
    cy.get('.submit-button').click();

    // Verifica que estamos de vuelta en la página de tipos y que se muestra el mensaje de éxito
    cy.url().should('include', '/tipos');
    cy.contains('El tipo "Test" ha sido creado correctamente.');
  });

  it('should create a new astro', () => {
    // Click en el botón "Crear"
    cy.get('.create-button').click();

    // Verifica que estamos en la página de creación
    cy.url().should('include', '/astrosForm');

    // Rellena el formulario
    cy.get('#nombre').type('Nuevo Astro');
    cy.get('#descripcion').type('Descripción del nuevo astro');
    cy.get('#masa').type('1000');
    cy.get('#densidad').type('5000');
    cy.get('#distancia').type('100');
    cy.get('#magnitudAparente').type('1.5');
    cy.get('#corrimientoAlRojo').type('0.1');
    cy.get('#temperatura').type('3000');
    cy.get('#edad').type('5000');

    // Espera a que el tipo "Test" esté disponible en el select
    cy.get('#tipo').should('contain', 'Test').select('Test');

    // Envía el formulario
    cy.get('.submit-button').click();

    // Verifica que estamos de vuelta en la página de astros y que se muestra el mensaje de éxito
    cy.url().should('include', '/astros');
    cy.contains('El astro "Nuevo Astro" ha sido creado correctamente.');
  });

  it('should edit an existing astro', () => {
    // Click en el botón "Editar" del primer astro en la lista
    cy.get('tbody tr').first().find('.edit-button').click();

    // Verifica que estamos en la página de edición
    cy.url().should('include', '/astrosForm');

    // Edita el formulario
    cy.get('#nombre').clear().type('Astro Editado');
    cy.get('#descripcion').clear().type('Descripción del astro editado');
    cy.get('#masa').clear().type('2000');
    cy.get('#densidad').clear().type('6000');
    cy.get('#distancia').clear().type('200');
    cy.get('#magnitudAparente').clear().type('2.5');
    cy.get('#corrimientoAlRojo').clear().type('0.2');
    cy.get('#temperatura').clear().type('4000');
    cy.get('#edad').clear().type('6000');
    cy.get('#tipo').select('1'); // Selecciona el segundo tipo disponible

    // Envía el formulario
    cy.get('.submit-button').click();

    // Verifica que estamos de vuelta en la página de astros y que se muestra el mensaje de éxito
    cy.url().should('include', '/astros');
    cy.contains('El astro "Astro Editado" ha sido actualizado correctamente.');
  });

  it('should delete an existing astro', () => {
    // Click en el botón "Borrar" del primer astro en la lista
    cy.get('tbody tr').first().find('.delete-button').click();

    // Verifica que se muestra el modal de confirmación
    cy.get('.modal').should('be.visible');
    cy.contains('¿Estás seguro de que deseas borrar el astro');

    // Confirma la eliminación
    cy.get('.confirm-button').click();

    // Verifica que el astro ha sido eliminado y que se muestra el mensaje de éxito
    cy.contains('El astro "Astro Editado" ha sido borrado correctamente.');
  });
});