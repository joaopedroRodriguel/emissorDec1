package br.edu.ifpb.pweb2.emissordec.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
    @Pattern(regexp = "[0-9]{11}", message = "Exatamente 11 números")
    private String fone;
    @OneToMany(mappedBy = "instituicao",
            targetEntity=Estudante.class,
            cascade=CascadeType.ALL)
    private List<Estudante> estudantes;
    @OneToMany (mappedBy = "instituicao" ,
            targetEntity=PeriodoLetivo.class,
            cascade=CascadeType.ALL)
    private List<PeriodoLetivo> periodos;

    @OneToOne(orphanRemoval = true)
    private PeriodoLetivo periodoAtual;
}
