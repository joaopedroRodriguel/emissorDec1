package br.edu.ifpb.pweb2.emissordec.service;

import br.edu.ifpb.pweb2.emissordec.model.Declaracao;
import br.edu.ifpb.pweb2.emissordec.repository.DeclaracaoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
