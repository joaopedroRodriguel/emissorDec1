package br.edu.ifpb.pweb2.emissordec.service;

import br.edu.ifpb.pweb2.emissordec.model.Estudante;
import br.edu.ifpb.pweb2.emissordec.model.Instituicao;
import br.edu.ifpb.pweb2.emissordec.repository.EstudanteRepository;
import br.edu.ifpb.pweb2.emissordec.repository.InstituicaoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EstudanteService {

    @Autowired
    EstudanteRepository estudanteRepository;

    @Autowired
    InstituicaoRepository instituicaoRepository;

    @Transactional
    public String insert(Estudante newEstudante) {
        Optional<Estudante> opestudante = this.estudanteRepository.getEstudanteByMatricula(newEstudante.getMatricula());
        if (opestudante.isPresent()) {
            Estudante estudante = opestudante.get();
            estudante.setNome(newEstudante.getNome());
            estudante.setMatricula(newEstudante.getMatricula());
            estudante.setInstituicaoAtual(newEstudante.getInstituicaoAtual());

            this.estudanteRepository.save(estudante);
            return "Estudante editado com sucesso";
        }
        this.estudanteRepository.save(newEstudante);
        return "Estudante cadastrado com sucesso";
    }

    public List<Estudante> list() {
        return estudanteRepository.findAll();
    }

    public Estudante search(Long idEstudante) {
        Optional<Estudante> estudanteOptional = this.estudanteRepository.findById(idEstudante);
        return estudanteOptional.orElseGet(estudanteOptional::orElseThrow);
    }

    public String delete(Long idEstudante) {
        Optional<Estudante> opestudantes = this.estudanteRepository.findById(idEstudante);
        if (opestudantes.isPresent()) {
            this.estudanteRepository.deleteById(idEstudante);
            return "Estudante deletado com sucesso";
        }
        return "Estudante não encontrado";
    }

    public List<Instituicao> listarInstituicoes() {
        return this.instituicaoRepository.findAll();
    }
}
