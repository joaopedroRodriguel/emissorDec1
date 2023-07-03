package br.edu.ifpb.pweb2.emissordec.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;

import java.io.IOException;
import java.time.LocalDate;
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Data deve ser futura")
    private Date dataVencimento;

    private String observacao;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST})
//    @JoinColumn(name = "estudante_id")
//    private Estudante estudante;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "id_estudante")
    private Estudante estudante;

    @ManyToOne
    @JoinColumn(name = "periodo_id")
    private PeriodoLetivo periodoLetivo;
    
    @Lob
    private byte[] documento;

    @NotBlank(message = "Campo Obrigatório")
    private String nome;   
    
    public void setDocumento(MultipartFile multipartFile) throws IOException {
        this.documento = multipartFile.getBytes();
        this.nome = multipartFile.getOriginalFilename();
    }

    public Estudante getEstudante(){
        return this.estudante;
    }
    public Declaracao (Estudante estudante) {
        this.estudante = estudante;
    }


}
