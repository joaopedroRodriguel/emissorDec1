package br.edu.ifpb.pweb2.emissordec.service;

import br.edu.ifpb.pweb2.emissordec.model.Instituicao;
import br.edu.ifpb.pweb2.emissordec.model.PeriodoLetivo;
import br.edu.ifpb.pweb2.emissordec.repository.InstituicaoRepository;
import br.edu.ifpb.pweb2.emissordec.repository.PeriodoLetivoRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class InstituicaoService {
    @Autowired
    InstituicaoRepository instituicaoRepository;
    public List<Instituicao> list() {
        return instituicaoRepository.findAll();
    }
    public Optional<Instituicao> search(Long id) {
        return instituicaoRepository.findById(id);
    }
    @Transactional
    public Instituicao insert(Instituicao instituicao){
        return instituicaoRepository.save(instituicao);
    }
    public Instituicao update(Long id, Instituicao newInstituicao){
        Optional<Instituicao> oldInstituicao = instituicaoRepository.findById(id);
        Instituicao instituicao = oldInstituicao.get();
        BeanUtils.copyProperties(newInstituicao, instituicao, "id");
        return instituicaoRepository.save(instituicao);
    }
    public void delete(Long id) {
        instituicaoRepository.deleteById(id);
    }
}