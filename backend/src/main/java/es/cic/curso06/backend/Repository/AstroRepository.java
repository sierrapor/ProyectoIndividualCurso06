package es.cic.curso06.backend.Repository;

import es.cic.curso06.backend.Model.Astro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AstroRepository extends JpaRepository<Astro, Long> {
}