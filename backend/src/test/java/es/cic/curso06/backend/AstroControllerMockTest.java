package es.cic.curso06.backend;

import es.cic.curso06.backend.Controller.AstroController;
import es.cic.curso06.backend.Model.Astro;
import es.cic.curso06.backend.Service.AstroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AstroControllerMockTest {

    @Mock
    private AstroService astroService;

    @InjectMocks
    private AstroController astroController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAstros() {
        List<Astro> astros = Arrays.asList(new Astro(), new Astro());
        when(astroService.findAll()).thenReturn(astros);

        ResponseEntity<List<Astro>> response = astroController.getAllAstros();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(astros, response.getBody());
    }

    @Test
    public void testGetAstroById() {
        Astro astro = new Astro();
        when(astroService.findById(anyLong())).thenReturn(astro);

        ResponseEntity<Astro> response = astroController.getAstroById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(astro, response.getBody());
    }

    @Test
    public void testGetAstroByIdNotFound() {
        when(astroService.findById(anyLong())).thenReturn(null);

        ResponseEntity<Astro> response = astroController.getAstroById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateAstro() {
        Astro astro = new Astro();
        when(astroService.save(any(Astro.class))).thenReturn(astro);

        ResponseEntity<Astro> response = astroController.createAstro(astro);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(astro, response.getBody());
    }

    @Test
    public void testCreateAstroWithId() {
        Astro astro = new Astro();
        astro.setId(1L);

        ResponseEntity<Astro> response = astroController.createAstro(astro);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testUpdateAstro() {
        Astro astro = new Astro();
        astro.setId(1L);
        when(astroService.findById(anyLong())).thenReturn(astro);
        when(astroService.save(any(Astro.class))).thenReturn(astro);

        ResponseEntity<Astro> response = astroController.updateAstro(1L, astro);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(astro, response.getBody());
    }

    @Test
    public void testUpdateAstroNotFound() {
        Astro astro = new Astro();
        astro.setId(1L);
        when(astroService.findById(anyLong())).thenReturn(null);

        ResponseEntity<Astro> response = astroController.updateAstro(1L, astro);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateAstroBadRequest() {
        Astro astro = new Astro();

        ResponseEntity<Astro> response = astroController.updateAstro(1L, astro);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDeleteAstro() {
        Astro astro = new Astro();
        when(astroService.findById(anyLong())).thenReturn(astro);
        doNothing().when(astroService).delete(any(Astro.class));

        ResponseEntity<Void> response = astroController.deleteAstro(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(astroService, times(1)).delete(astro);
    }

    @Test
    public void testDeleteAstroNotFound() {
        when(astroService.findById(anyLong())).thenReturn(null);

        ResponseEntity<Void> response = astroController.deleteAstro(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}