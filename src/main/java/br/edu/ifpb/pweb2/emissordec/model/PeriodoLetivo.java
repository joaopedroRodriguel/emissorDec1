package br.edu.ifpb.pweb2.emissordec.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tb_periodo")
public class PeriodoLetivo {

    @Id
    @Column(name="periodo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int ano;

    private int periodo;

    private LocalDate inicio;

    private LocalDate fim;

    @OneToMany(mappedBy = "periodoLetivo",
            targetEntity=Declaracao.class,
            cascade=CascadeType.ALL)
    private List<Declaracao> declaracoes;

    @ManyToOne
    @JoinColumn(name = "instituicao_id")
    private Instituicao instituicao;
}
