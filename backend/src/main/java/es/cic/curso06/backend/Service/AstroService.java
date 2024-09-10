package es.cic.curso06.backend.Service;

import es.cic.curso06.backend.Model.Astro;
import es.cic.curso06.backend.Repository.AstroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AstroService {

    @Autowired
    private AstroRepository astroRepository;

    public List<Astro> findAll() {
        return astroRepository.findAll();
    }

    public Astro findById(Long id) {
        Optional<Astro> astro = astroRepository.findById(id);
        return astro.orElse(null);
    }

    public Astro save(Astro astro) {
        return astroRepository.save(astro);
    }

    public void delete(Astro astro) {
        astroRepository.delete(astro);
    }
}