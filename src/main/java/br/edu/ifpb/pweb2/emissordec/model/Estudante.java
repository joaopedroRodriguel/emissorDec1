package br.edu.ifpb.pweb2.emissordec.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.ArrayList;
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
    @JoinColumn(name = "instituicao_atual_id")
    private Instituicao instituicaoAtual;

    @OneToMany(mappedBy = "estudante", cascade = CascadeType.ALL)
    private List<Declaracao> declaracoes = new ArrayList<>();
}
