package br.edu.ifpb.pweb2.emissordec.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.util.Date;
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
    @NotNull
    @Min(value = 2000, message = "Valor deve ser maior que 2000")
    private int ano;
    @NotNull
    @Min(value = 0, message = "Valor deve ser maior que 0")
    private int periodo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Data deve ser futura")
    private Date fim;
    @OneToMany(mappedBy = "periodoLetivo",
            targetEntity=Declaracao.class,
            cascade=CascadeType.ALL)
    private List<Declaracao> declaracoes;
    
    @ManyToOne
    @JoinColumn(name = "instituicao_id")
    private Instituicao instituicao;

    public Instituicao geInstituicao(){
        return this.instituicao;
    }
    public PeriodoLetivo(Instituicao instituicao) {
         this.instituicao = instituicao;
    }
    
}
