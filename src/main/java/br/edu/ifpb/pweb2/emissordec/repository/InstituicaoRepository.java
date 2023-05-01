package br.edu.ifpb.pweb2.emissordec.repository;

import br.edu.ifpb.pweb2.emissordec.model.Instituicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {
    Optional<Instituicao> getInstituicaoBySiglaEquals(String toUpperCase);
}
