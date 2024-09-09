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

import es.cic.curso06.backend.Model.Objeto;
import es.cic.curso06.backend.Service.ObjetoService;

@RestController
@RequestMapping("/api/objetos")
public class ObjetoController {

    @Autowired
    private ObjetoService objetoService;

    @GetMapping
    public List<Objeto> getAllObjetos() {
        return objetoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Objeto> getObjetoById(@PathVariable Long id) {
        Objeto objeto = objetoService.findById(id);
        if (objeto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(objeto);
    }

    @PostMapping
    public Objeto createObjeto(@RequestBody Objeto objeto) {
        return objetoService.save(objeto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Objeto> updateObjeto(@PathVariable Long id, @RequestBody Objeto objetoDetails) {
        Objeto objeto = objetoService.findById(id);
        if (objeto == null) {
            return ResponseEntity.notFound().build();
        }
        Objeto updatedObjeto = objetoService.save(objeto);
        return ResponseEntity.ok(updatedObjeto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObjeto(@PathVariable Long id) {
        Objeto objeto = objetoService.findById(id);
        if (objeto == null) {
            return ResponseEntity.notFound().build();
        }
        objetoService.delete(objeto);
        return ResponseEntity.noContent().build();
    }
}