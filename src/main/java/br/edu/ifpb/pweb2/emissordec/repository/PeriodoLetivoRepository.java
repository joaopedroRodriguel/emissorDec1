package br.edu.ifpb.pweb2.emissordec.repository;

import br.edu.ifpb.pweb2.emissordec.model.PeriodoLetivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PeriodoLetivoRepository extends JpaRepository<PeriodoLetivo, Long> {
    @Query(value = "select * from tb_periodo where instituicao_id is null ",nativeQuery = true)
    List<PeriodoLetivo> listarPeriodosSemInstituicoes();
    @Query(value="select * from tb_periodo where  instituicao_id = :id ",nativeQuery = true)
    List<PeriodoLetivo> listarPeriodosIntituicao(Long id);
}
