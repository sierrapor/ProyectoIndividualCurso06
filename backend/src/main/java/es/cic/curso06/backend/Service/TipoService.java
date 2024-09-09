package es.cic.curso06.backend.Service;

import es.cic.curso06.backend.Model.Tipo;
import es.cic.curso06.backend.Repository.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoService {

    @Autowired
    private TipoRepository tipoRepository;

    public List<Tipo> findAll() {
        return tipoRepository.findAll();
    }

    public Tipo findById(Long id) {
        Optional<Tipo> tipo = tipoRepository.findById(id);
        return tipo.orElse(null);
    }

    public Tipo save(Tipo tipo) {
        return tipoRepository.save(tipo);
    }

    public void delete(Tipo tipo) {
        tipoRepository.delete(tipo);
    }
}