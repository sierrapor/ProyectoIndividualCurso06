package es.cic.curso06.backend;

import es.cic.curso06.backend.Controller.AstroController;
import es.cic.curso06.backend.Model.Astro;
import es.cic.curso06.backend.Service.AstroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AstroController.class)
public class AstroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AstroService astroService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAstros() throws Exception {
        List<Astro> astros = Arrays.asList(new Astro(), new Astro());
        when(astroService.findAll()).thenReturn(astros);

        mockMvc.perform(get("/api/astros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(astros.size()));
    }

    @Test
    public void testGetAstroById() throws Exception {
        Astro astro = new Astro();
        when(astroService.findById(anyLong())).thenReturn(astro);

        mockMvc.perform(get("/api/astros/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(astro.getId()));
    }

    @Test
    public void testCreateAstro() throws Exception {
        Astro astro = new Astro();
        when(astroService.save(any(Astro.class))).thenReturn(astro);

        mockMvc.perform(post("/api/astros")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Test Astro\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(astro.getId()));
    }

    @Test
    public void testUpdateAstro() throws Exception {
        Astro astro = new Astro();
        astro.setId(1L);
        when(astroService.findById(anyLong())).thenReturn(astro);
        when(astroService.save(any(Astro.class))).thenReturn(astro);

        mockMvc.perform(put("/api/astros/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"nombre\":\"Updated Astro\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(astro.getId()));
    }

    @Test
    public void testDeleteAstro() throws Exception {
        Astro astro = new Astro();
        when(astroService.findById(anyLong())).thenReturn(astro);

        mockMvc.perform(delete("/api/astros/1"))
                .andExpect(status().isNoContent());
    }
}