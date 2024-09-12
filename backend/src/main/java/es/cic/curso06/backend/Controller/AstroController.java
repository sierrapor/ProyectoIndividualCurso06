package es.cic.curso06.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import es.cic.curso06.backend.Model.Astro;
import es.cic.curso06.backend.Service.AstroService;

@RestController
@RequestMapping("/api/astros")
public class AstroController {

    @Autowired
    private AstroService astroService;

    @GetMapping
    public ResponseEntity<List<Astro>> getAllAstros() {
        return ResponseEntity.ok(astroService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Astro> getAstroById(@PathVariable Long id) {
        Astro astro = astroService.findById(id);
        if (astro == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(astro);
    }

    @PostMapping
    public ResponseEntity<Astro> createAstro(@RequestBody Astro astro) {
        if (astro.getId() != null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(astroService.save(astro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Astro> updateAstro(@PathVariable Long id, @RequestBody Astro astro) {
        if (astro.getId() == null || !astro.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Astro existingAstro = astroService.findById(id);
        if (existingAstro == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(astroService.save(astro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAstro(@PathVariable Long id) {
        Astro astro = astroService.findById(id);
        if (astro == null) {
            return ResponseEntity.notFound().build();
        }
        astroService.delete(astro);
        return ResponseEntity.noContent().build();
    }
}