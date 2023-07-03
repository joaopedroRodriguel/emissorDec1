package br.edu.ifpb.pweb2.emissordec.service;

import br.edu.ifpb.pweb2.emissordec.model.Declaracao;
import br.edu.ifpb.pweb2.emissordec.repository.DeclaracaoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        declaracao.getEstudante().setDeclaracaoAtual(declaracao);
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
        return declaracaoRepository.buscarDeclaracaoVencida();
    }

    public List<Declaracao> DeclaracoesPorVencer(Long qtddDias) {        
        List<Declaracao> declaracoes = declaracaoRepository.declaracoNDias(qtddDias);
        return declaracoes;
    }



}
