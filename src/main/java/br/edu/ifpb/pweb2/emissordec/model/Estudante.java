package br.edu.ifpb.pweb2.emissordec.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tb_estudante")
public class Estudante {
    @Id
    @Column(name="estudante_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campo Obrigatório!")
    private String nome;

    @NotBlank(message = "Campo Obrigatório!")
    private String matricula;

    @OneToOne
    @JoinColumn(name = "username")
    private User user;

    @ManyToOne
    @JoinColumn(name = "instituicao_id")
    @ToString.Exclude
    private Instituicao instituicao;
    
    @OneToMany(mappedBy = "estudante" ,
            targetEntity=Declaracao.class,
            cascade=CascadeType.ALL)
    private List<Declaracao> declaracoes;

    private boolean admin;
<<<<<<< HEAD
    @OneToOne(cascade = CascadeType.ALL)
    // cascade 
=======
    
    @OneToOne
>>>>>>> b815afb7ed9d50d04eb04665c8ca4704ead82311
    private Declaracao declaracaoAtual;
}
