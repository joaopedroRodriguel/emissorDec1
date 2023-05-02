package br.edu.ifpb.pweb2.emissordec.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tb_declaracao")
public class Declaracao {
    @Id
    @Column(name="declaracao_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataRecebimento;
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "estudante_id")
    private Estudante estudante;
    @ManyToOne
    @JoinColumn(name = "periodo_id")
    private PeriodoLetivo periodoLetivo;
}
