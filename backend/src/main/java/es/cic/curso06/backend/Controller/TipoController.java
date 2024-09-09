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
    public ResponseEntity<Tipo> createTipo(@RequestBody Tipo tipo) {
        if (tipo.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(tipoService.save(tipo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tipo> updateTipo(@PathVariable Long id, @RequestBody Tipo tipo) {
        if (tipoService.findById(id) == null || tipo.getId() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tipoService.save(tipo));
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