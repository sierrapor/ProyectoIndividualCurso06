package es.cic.curso06.backend;

import es.cic.curso06.backend.Model.Astro;
import es.cic.curso06.backend.Repository.AstroRepository;
import es.cic.curso06.backend.Service.AstroService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AstroServiceTest {

    @Mock
    private AstroRepository astroRepository;

    @InjectMocks
    private AstroService astroService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        List<Astro> astros = Arrays.asList(new Astro(), new Astro());
        when(astroRepository.findAll()).thenReturn(astros);

        List<Astro> result = astroService.findAll();
        assertEquals(astros.size(), result.size());
    }

    @Test
    public void testFindById() {
        Astro astro = new Astro();
        when(astroRepository.findById(anyLong())).thenReturn(Optional.of(astro));

        Astro result = astroService.findById(1L);
        assertNotNull(result);
        assertEquals(astro, result);
    }

    @Test
    public void testSave() {
        Astro astro = new Astro();
        when(astroRepository.save(any(Astro.class))).thenReturn(astro);

        Astro result = astroService.save(astro);
        assertNotNull(result);
        assertEquals(astro, result);
    }

    @Test
    public void testDelete() {
        Astro astro = new Astro();
        doNothing().when(astroRepository).delete(any(Astro.class));

        astroService.delete(astro);
        verify(astroRepository, times(1)).delete(astro);
    }
}