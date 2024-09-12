describe('Navigation Test', () => {
  beforeEach(() => {
    cy.visit('/');
  });

  it('should navigate to the Inicio page', () => {
    cy.contains('Inicio').click();
    cy.url().should('include', '/');
    cy.contains('h1', 'Bienvenido al Catálogo de Objetos Astronómicos');
  });

  it('should navigate to the Tipos page', () => {
    cy.contains('Tipos').click();
    cy.url().should('include', '/tipos');
    cy.contains('h1', 'Tipos de Objetos Astronómicos');
  });

  it('should navigate to the Astros page', () => {
    cy.contains('Astros').click();
    cy.url().should('include', '/astros');
    cy.contains('h1', 'Astros');
  });

  it('should navigate back to the Inicio page from Tipos', () => {
    cy.contains('Tipos').click();
    cy.contains('Inicio').click();
    cy.url().should('include', '/');
    cy.contains('h1', 'Bienvenido al Catálogo de Objetos Astronómicos');
  });

  it('should navigate back to the Inicio page from Astros', () => {
    cy.contains('Astros').click();
    cy.contains('Inicio').click();
    cy.url().should('include', '/');
    cy.contains('h1', 'Bienvenido al Catálogo de Objetos Astronómicos');
  });
});