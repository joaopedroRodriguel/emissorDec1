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

    public List<Estudante> list() {
        return estudanteRepository.findAll();
    }

    public Optional<Estudante> search(Long id) {
        return estudanteRepository.findById(id);
    }

    public Estudante insert(Estudante estudante){
        return estudanteRepository.save(estudante);
    }

    public Estudante update(Long id, Estudante newEstudante){
        Optional<Estudante> oldEstudante = estudanteRepository.findById(id);
        Estudante estudante = oldEstudante.get();
        BeanUtils.copyProperties(newEstudante, estudante, "id");
        return estudanteRepository.save(estudante);
    }

    public void delete(Long id) {
        estudanteRepository.deleteById(id);
    }
}