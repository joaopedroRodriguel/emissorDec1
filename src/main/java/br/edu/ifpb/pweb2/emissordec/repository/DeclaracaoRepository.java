package br.edu.ifpb.pweb2.emissordec.repository;

import br.edu.ifpb.pweb2.emissordec.model.Declaracao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface DeclaracaoRepository extends JpaRepository<Declaracao, Long> {
    List<Declaracao> getDeclaracaoByEstudante(Long id);
}
