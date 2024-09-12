// cypress/integration/tipos.spec.js

describe('Tipos Flow', () => {
    beforeEach(() => {
      // Visita la página principal de tipos antes de cada test
      cy.visit('/tipos');
    });
  
    it('should create a new tipo', () => {
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
  
    it('should edit an existing tipo', () => {
      // Click en el botón "Editar" del primer tipo en la lista
      cy.get('tbody tr').first().find('.edit-button').click();
  
      // Verifica que estamos en la página de edición
      cy.url().should('include', '/tiposForm');
  
      // Edita el formulario
      cy.get('#nombre').clear().type('Test Editado');
      cy.get('#descripcion').clear().type('Descripción editada');
      cy.get('#luminoso').uncheck();
      cy.get('#orbitante').uncheck();
  
      // Envía el formulario
      cy.get('.submit-button').click();
  
      // Verifica que estamos de vuelta en la página de tipos y que se muestra el mensaje de éxito
      cy.url().should('include', '/tipos');
      cy.contains('El tipo "Test Editado" ha sido editado correctamente.');
    });
  
    it('should delete an existing tipo', () => {
      // Click en el botón "Borrar" del primer tipo en la lista
      cy.get('tbody tr').first().find('.delete-button').click();
  
      // Verifica que se muestra el modal de confirmación
      cy.get('.modal').should('be.visible');
      cy.contains('¿Estás seguro de que deseas borrar el tipo "Test Editado"?');
  
      // Confirma la eliminación
      cy.get('.confirm-button').click();
  
      // Verifica que el tipo ha sido eliminado y que se muestra el mensaje de éxito
      cy.contains('El tipo "Test Editado" ha sido borrado correctamente.', { timeout: 10000 }).should('be.visible');
    });
  });