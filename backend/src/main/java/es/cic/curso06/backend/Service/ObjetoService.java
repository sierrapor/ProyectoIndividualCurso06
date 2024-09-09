package es.cic.curso06.backend.Service;

import es.cic.curso06.backend.Model.Objeto;
import es.cic.curso06.backend.Repository.ObjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObjetoService {

    @Autowired
    private ObjetoRepository objetoRepository;

    public List<Objeto> findAll() {
        return objetoRepository.findAll();
    }

    public Objeto findById(Long id) {
        Optional<Objeto> objeto = objetoRepository.findById(id);
        return objeto.orElse(null);
    }

    public Objeto save(Objeto objeto) {
        return objetoRepository.save(objeto);
    }

    public void delete(Objeto objeto) {
        objetoRepository.delete(objeto);
    }
}