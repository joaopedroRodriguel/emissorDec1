package br.edu.ifpb.pweb2.emissordec.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tb_instituicao")
public class Instituicao {

    @Id
    @Column(name="instituicao_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message="Campo obrigatório!")
    private String nome;
    @NotBlank(message="Campo obrigatório!")
    private String sigla;

    private String fone;

    @OneToMany(mappedBy = "instituicao",
            targetEntity=Estudante.class,
            cascade=CascadeType.ALL)
    private List<Estudante> estudantes;

    @OneToMany (mappedBy = "instituicao" ,
            targetEntity=PeriodoLetivo.class,
            cascade=CascadeType.ALL)
    private List<PeriodoLetivo> periodos;

    public Instituicao(PeriodoLetivo periodoLetivo) {
        this.periodos = (List<PeriodoLetivo>) periodoLetivo;
    }
}
