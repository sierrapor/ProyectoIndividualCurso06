package es.cic.curso06.backend;

import es.cic.curso06.backend.Model.Tipo;
import es.cic.curso06.backend.Repository.TipoRepository;
import es.cic.curso06.backend.Service.TipoService;

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

public class TipoServiceTest {

    @Mock
    private TipoRepository tipoRepository;

    @InjectMocks
    private TipoService tipoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        List<Tipo> tipos = Arrays.asList(new Tipo(), new Tipo());
        when(tipoRepository.findAll()).thenReturn(tipos);

        List<Tipo> result = tipoService.findAll();
        assertEquals(tipos.size(), result.size());
    }

    @Test
    public void testFindById() {
        Tipo tipo = new Tipo();
        when(tipoRepository.findById(anyLong())).thenReturn(Optional.of(tipo));

        Tipo result = tipoService.findById(1L);
        assertNotNull(result);
        assertEquals(tipo, result);
    }

    @Test
    public void testSave() {
        Tipo tipo = new Tipo();
        when(tipoRepository.save(any(Tipo.class))).thenReturn(tipo);

        Tipo result = tipoService.save(tipo);
        assertNotNull(result);
        assertEquals(tipo, result);
    }

    @Test
    public void testDelete() {
        Tipo tipo = new Tipo();
        doNothing().when(tipoRepository).delete(any(Tipo.class));

        tipoService.delete(tipo);
        verify(tipoRepository, times(1)).delete(tipo);
    }
}