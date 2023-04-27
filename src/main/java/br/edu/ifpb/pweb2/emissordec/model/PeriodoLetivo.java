package br.edu.ifpb.pweb2.emissordec.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "Campo Obrigatório!")
    private int ano;
    @NotBlank(message = "Campo Obrigatório!")
    private int periodo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotBlank(message = "Campo Obrigatório!")
    private LocalDate inicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotBlank(message = "Campo Obrigatório!")
    private LocalDate fim;

    @OneToMany(mappedBy = "periodoLetivo",
            targetEntity=Declaracao.class,
            cascade=CascadeType.ALL)
    private List<Declaracao> declaracoes;

    @ManyToOne
    @JoinColumn(name = "instituicao_id")
    private Instituicao instituicao;

    public PeriodoLetivo(Instituicao instituicao) {
        this.instituicao = instituicao;
    }
}
