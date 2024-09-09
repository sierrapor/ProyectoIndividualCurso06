package es.cic.curso06.backend.Controller;

import es.cic.curso06.backend.Model.Tipo;
import es.cic.curso06.backend.Service.TipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos")
public class TipoController {

    @Autowired
    private TipoService tipoService;

    @GetMapping
    public List<Tipo> getAllTipos() {
        return tipoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tipo> getTipoById(@PathVariable Long id) {
        Tipo tipo = tipoService.findById(id);
        if (tipo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tipo);
    }

    @PostMapping
    public Tipo createTipo(@RequestBody Tipo tipo) {
        return tipoService.save(tipo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tipo> updateTipo(@PathVariable Long id, @RequestBody Tipo tipoDetails) {
        Tipo tipo = tipoService.findById(id);
        if (tipo == null) {
            return ResponseEntity.notFound().build();
        }
        tipo.setNombre(tipoDetails.getNombre());
        tipo.setDescripcion(tipoDetails.getDescripcion());
        tipo.setLuminoso(tipoDetails.isLuminoso());
        tipo.setOrbitante(tipoDetails.isOrbitante());
        tipo.setAstros(tipoDetails.getAstros());
        Tipo updatedTipo = tipoService.save(tipo);
        return ResponseEntity.ok(updatedTipo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipo(@PathVariable Long id) {
        Tipo tipo = tipoService.findById(id);
        if (tipo == null) {
            return ResponseEntity.notFound().build();
        }
        tipoService.delete(tipo);
        return ResponseEntity.noContent().build();
    }
}