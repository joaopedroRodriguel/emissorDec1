package br.edu.ifpb.pweb2.emissordec.repository;

import br.edu.ifpb.pweb2.emissordec.model.Estudante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface EstudanteRepository extends JpaRepository<Estudante, Long> {

    Optional<Estudante> getEstudanteByMatricula(String matricula);
}
