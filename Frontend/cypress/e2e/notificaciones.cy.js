describe('Notification Tests', () => {
    beforeEach(() => {
      cy.visit('/');
    });
  
    it('should display a success notification when an astro is created', () => {
      cy.contains('Astros').click();
      cy.contains('button', 'Crear').click();
      cy.url().should('include', '/astrosForm');
  
      // Fill the form with valid data
      cy.get('input#nombre').type('Nuevo Astro');
      cy.get('input#descripcion').type('Descripción del nuevo astro');
      cy.get('input#masa').type('1.989e30');
      cy.get('input#densidad').type('1410');
      cy.get('input#distancia').type('4.24');
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
      cy.contains('button', 'Crear').click();
      cy.url().should('include', '/tiposForm');
  
      // Fill the form with valid data
      cy.get('input#nombre').type('Nuevo Tipo');
      cy.get('input#descripcion').type('Descripción del nuevo tipo');
  
      // Submit the form
      cy.get('button[type="submit"]').click();
  
      // Check for the success notification
      cy.get('.notification.success').should('contain', 'El tipo "Nuevo Tipo" ha sido creado correctamente.');
    });
  
    it('should display a success notification when an astro is updated', () => {
      cy.contains('Astros').click();
  
      // Assuming there is at least one astro in the list
      cy.get('tbody tr').first().within(() => {
        cy.get('button.edit-button').click();
      });
  
      cy.url().should('include', '/astrosForm');
  
      // Update the form with valid data
      cy.get('input#nombre').clear().type('Astro Actualizado');
      cy.get('input#descripcion').clear().type('Descripción del astro actualizado');
  
      // Submit the form
      cy.get('button[type="submit"]').click();
  
      // Check for the success notification
      cy.get('.notification.success').should('contain', 'El astro "Astro Actualizado" ha sido actualizado correctamente.');
    });
  
    it('should display a success notification when a tipo is updated', () => {
      cy.contains('Tipos').click();
  
      // Assuming there is at least one tipo in the list
      cy.get('tbody tr').first().within(() => {
        cy.get('button.edit-button').click();
      });
  
      cy.url().should('include', '/tiposForm');
  
      // Update the form with valid data
      cy.get('input#nombre').clear().type('Tipo Actualizado');
      cy.get('input#descripcion').clear().type('Descripción del tipo actualizado');
  
      // Submit the form
      cy.get('button[type="submit"]').click();
  
      // Check for the success notification
      cy.get('.notification.success').should('contain', 'El tipo "Tipo Actualizado" ha sido actualizado correctamente.');
    });
  
    it('should display a success notification when an astro is deleted', () => {
      cy.contains('Astros').click();
  
      // Assuming there is at least one astro in the list
      cy.get('tbody tr').first().within(() => {
        cy.get('button.delete-button').click();
      });
  
      // Confirm deletion
      cy.get('.modal-content').within(() => {
        cy.contains('button', 'Sí').click();
      });
  
      // Check for the success notification
      cy.get('.notification.success').should('contain', 'ha sido borrado correctamente.');
    });
  
    it('should display an error notification when a tipo with associated astros is deleted', () => {
      cy.contains('Tipos').click();
  
      // Assuming there is at least one tipo in the list with associated astros
      cy.get('tbody tr').first().within(() => {
        cy.get('button.delete-button').click();
      });
  
      // Check for the error notification
      cy.get('.notification.error').should('contain', 'No se puede borrar el tipo');
    });
  
    it('should hide the notification after the specified duration', () => {
      cy.contains('Astros').click();
      cy.contains('button', 'Crear').click();
      cy.url().should('include', '/astrosForm');
  
      // Fill the form with valid data
      cy.get('input#nombre').type('Temporal Astro');
      cy.get('input#descripcion').type('Descripción temporal');
      cy.get('input#masa').type('1.989e30');
      cy.get('input#densidad').type('1410');
      cy.get('input#distancia').type('4.24');
      cy.get('input#magnitudAparente').type('1.46');
      cy.get('input#corrimientoAlRojo').type('0.0001');
      cy.get('input#temperatura').type('5778');
      cy.get('input#edad').type('4.6');
      cy.get('select#tipo').select('Estrella'); // Asegúrate de que el tipo "Estrella" exista
  
      // Submit the form
      cy.get('button[type="submit"]').click();
  
      // Check for the success notification
      cy.get('.notification.success').should('contain', 'El astro "Temporal Astro" ha sido creado correctamente.');
  
      // Wait for the notification to disappear
      cy.wait(5000); // Espera el tiempo de duración de la notificación
      cy.get('.notification.success').should('not.exist');
    });
  });