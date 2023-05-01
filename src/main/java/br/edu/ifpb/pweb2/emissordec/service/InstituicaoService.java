package br.edu.ifpb.pweb2.emissordec.service;

import br.edu.ifpb.pweb2.emissordec.model.Instituicao;
import br.edu.ifpb.pweb2.emissordec.model.PeriodoLetivo;
import br.edu.ifpb.pweb2.emissordec.repository.InstituicaoRepository;
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

    @Autowired
    PeriodoLetivoService periodoLetivoService;



    public List<Instituicao> list() {
        return instituicaoRepository.findAll();
    }

    public Optional<Instituicao> search(Long id) {
        return instituicaoRepository.findById(id);
    }

    @Transactional
    public String insert(Instituicao newInstituicao) {
        Optional<Instituicao> opInstituicao = this.instituicaoRepository.getInstituicaoBySiglaEquals(newInstituicao.getSigla().toUpperCase());
        if (opInstituicao.isPresent()) {
            Instituicao instituicao = opInstituicao.get();
            instituicao.setNome(newInstituicao.getNome());
            instituicao.setFone(newInstituicao.getFone());
            instituicao.setSigla(newInstituicao.getSigla());
            instituicao.setPeriodos(newInstituicao.getPeriodos());
            if(!instituicao.getPeriodos().contains(newInstituicao.getPeriodos())){
                instituicao.addPeriodo((PeriodoLetivo) newInstituicao.getPeriodos());
            }

            this.instituicaoRepository.save(instituicao);
            return "Instituição editada com sucesso";
        }
        newInstituicao.addPeriodo((PeriodoLetivo) newInstituicao.getPeriodos());
        this.instituicaoRepository.save(newInstituicao);
        return "Instituição cadastrada com sucesso";
    }

    //falta acabar
    public void delete(Long id) {
        Optional<Instituicao> opInstituicao = this.instituicaoRepository.findById(id);
        if (opInstituicao.isPresent()) {
            this.instituicaoRepository.deleteById(id);
            return "Instituição deletada com sucesso";
        }
        return "Instituição não encontrada, impossível deletar";
    }
}
