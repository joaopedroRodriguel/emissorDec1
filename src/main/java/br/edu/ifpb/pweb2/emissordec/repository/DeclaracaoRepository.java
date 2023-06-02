package br.edu.ifpb.pweb2.emissordec.repository;

import br.edu.ifpb.pweb2.emissordec.model.Declaracao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface DeclaracaoRepository extends JpaRepository<Declaracao, Long> {
    List<Declaracao> getDeclaracaoByEstudante(Long id);

    @Query(value = "SELECT * FROM DECLARACAO DEC JOIN DECLARACAO_PERIODO_LETIVO DPL ON DEC.ID = DPL.DECLARACAO_ID " +
            "JOIN PERIODO_LETIVO PL ON DPL.PERIODO_LETIVO_ID = PL.ID WHERE CURRENT_DATE > PL.FIM", nativeQuery = true)
    List<Declaracao> buscarDeclaracaoVencidas();

    //Falta testar e ver se essa query está funcionando
}
