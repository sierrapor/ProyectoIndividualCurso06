package es.cic.curso06.backend;

import es.cic.curso06.backend.Controller.TipoController;
import es.cic.curso06.backend.Model.Tipo;
import es.cic.curso06.backend.Service.TipoService;
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

@WebMvcTest(TipoController.class)
public class TipoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TipoService tipoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTipos() throws Exception {
        List<Tipo> tipos = Arrays.asList(new Tipo(), new Tipo());
        when(tipoService.findAll()).thenReturn(tipos);

        mockMvc.perform(get("/api/tipos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(tipos.size()));
    }

    @Test
    public void testGetTipoById() throws Exception {
        Tipo tipo = new Tipo();
        when(tipoService.findById(anyLong())).thenReturn(tipo);

        mockMvc.perform(get("/api/tipos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(tipo.getId()));
    }

    @Test
    public void testCreateTipo() throws Exception {
        Tipo tipo = new Tipo();
        when(tipoService.save(any(Tipo.class))).thenReturn(tipo);

        mockMvc.perform(post("/api/tipos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Test Tipo\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(tipo.getId()));
    }

    @Test
    public void testUpdateTipo() throws Exception {
        Tipo tipo = new Tipo();
        tipo.setId(1L);
        when(tipoService.findById(anyLong())).thenReturn(tipo);
        when(tipoService.save(any(Tipo.class))).thenReturn(tipo);

        mockMvc.perform(put("/api/tipos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"nombre\":\"Updated Tipo\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(tipo.getId()));
    }

    @Test
    public void testDeleteTipo() throws Exception {
        Tipo tipo = new Tipo();
        when(tipoService.findById(anyLong())).thenReturn(tipo);

        mockMvc.perform(delete("/api/tipos/1"))
                .andExpect(status().isNoContent());
    }
}