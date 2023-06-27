package br.edu.ifpb.pweb2.emissordec.repository;

import br.edu.ifpb.pweb2.emissordec.model.Declaracao;
import br.edu.ifpb.pweb2.emissordec.model.Estudante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface EstudanteRepository extends JpaRepository<Estudante, Long> {

    Optional<Estudante> getEstudanteByMatricula(String matricula);
    @Query(value = "SELECT e.* from tb_estudante e LEFT JOIN tb_declaracao d on d.estudante_id = e.estudante_id WHERE d.estudante_id is null ", nativeQuery = true)
    List<Estudante> listarEstudantesSemDeclaracoes();

    @Query("from Estudante e join fetch e.user u where u.username = :username")
    Estudante findByUsername(@Param("username") String username);
}
