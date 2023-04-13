package br.edu.ifpb.pweb2.emissordec.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    private String nome;

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
}
