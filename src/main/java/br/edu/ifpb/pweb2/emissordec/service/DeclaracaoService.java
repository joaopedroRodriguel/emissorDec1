package br.edu.ifpb.pweb2.emissordec.service;

import br.edu.ifpb.pweb2.emissordec.model.Declaracao;
import br.edu.ifpb.pweb2.emissordec.repository.DeclaracaoRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.w3c.dom.Text;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;



@Service
public class DeclaracaoService {
    @Autowired
    DeclaracaoRepository declaracaoRepository;
    public List<Declaracao> list() {
        return declaracaoRepository.findAll();
    }
    public Optional<Declaracao> search(Long id) {
        return declaracaoRepository.findById(id);
    }
    @Transactional
    public Declaracao insert(Declaracao declaracao){
        return declaracaoRepository.save(declaracao);
    }
    public Declaracao update(Long id, Declaracao newDeclaracao){
        Optional<Declaracao> oldDeclaracao = declaracaoRepository.findById(id);
        Declaracao declaracao = oldDeclaracao.get();
        BeanUtils.copyProperties(newDeclaracao, declaracao, "id");
        return declaracaoRepository.save(declaracao);
    }
    public void delete(Long id) {
        declaracaoRepository.deleteById(id);
    }
    public List<Declaracao> searchByEst(Long id){
        return declaracaoRepository.getDeclaracaoByEstudante(id);
    }

    public List<Declaracao> declaracoesVencidas() {
        return declaracaoRepository.buscarDeclaracaoVencidas();
    }

    public List<Declaracao> DeclaracoesPorVencer(int qtddDias) {
        LocalDate dataVencimento = LocalDate.now().plusDays(qtddDias);
        List<Declaracao> declaracoes = declaracaoRepository.buscarDataVencimento(dataVencimento);
        return declaracoes;
    }

}
