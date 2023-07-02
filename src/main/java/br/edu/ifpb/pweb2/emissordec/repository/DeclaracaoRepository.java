package br.edu.ifpb.pweb2.emissordec.repository;

import br.edu.ifpb.pweb2.emissordec.model.Declaracao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import br.edu.ifpb.pweb2.emissordec.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface DeclaracaoRepository extends JpaRepository<Declaracao, Long> {
    List<Declaracao> getDeclaracaoByEstudante(Long id);
    
    @Query(value = "select d.documento from Declaracao d where d.id = :idDeclaracao")
    Documento findDocumentoById(@Param ("idDeclaracao") Long idDeclaracao);


    @Query(value = "SELECT * FROM tb_declaracao WHERE data_vencimento < CURRENT_DATE", nativeQuery = true)
    List<Declaracao> buscarDeclaracaoVencida();

    
    @Query(value = "SELECT * FROM `tb_declaracao` WHERE `data_vencimento` BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL :qtdDias DAY)", nativeQuery = true)
    List<Declaracao> declaracoNDias(@Param ("qtdDias") Long qtdDias);
        

}

