package br.edu.ifpb.pweb2.emissordec.service;

import br.edu.ifpb.pweb2.emissordec.model.Estudante;
import br.edu.ifpb.pweb2.emissordec.model.PeriodoLetivo;
import br.edu.ifpb.pweb2.emissordec.repository.PeriodoLetivoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PeriodoLetivoService {

    @Autowired
    PeriodoLetivoRepository periodoLetivoRepository;

    public List<PeriodoLetivo> list() {
        return periodoLetivoRepository.findAll();
    }

    public Optional<PeriodoLetivo> search(Long id) {
        return periodoLetivoRepository.findById(id);
    }
    @Transactional
    public PeriodoLetivo insert(PeriodoLetivo periodoLetivo){
        return periodoLetivoRepository.save(periodoLetivo);
    }

    public PeriodoLetivo update(Long id, PeriodoLetivo newPeriodoLetivo){
        Optional<PeriodoLetivo> oldPeriodoLetivo = periodoLetivoRepository.findById(id);
        PeriodoLetivo periodoLetivo = oldPeriodoLetivo.get();
        BeanUtils.copyProperties(newPeriodoLetivo, periodoLetivo, "id");
        return periodoLetivoRepository.save(periodoLetivo);
    }

    public void delete(Long id) {
        periodoLetivoRepository.deleteById(id);
    }
}
