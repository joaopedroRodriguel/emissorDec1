package br.edu.ifpb.pweb2.emissordec.repository;

import br.edu.ifpb.pweb2.emissordec.model.Declaracao;
import br.edu.ifpb.pweb2.emissordec.model.Estudante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface EstudanteRepository extends JpaRepository<Estudante, Long> {

    Optional<Estudante> getEstudanteByMatricula(String matricula);
    @Query(value = "select * from tb_estudante where declaracao_atual_declaracao_id is null ",nativeQuery = true)
    List<Declaracao> listarEstudanteDeclaracaoVencida();
}
