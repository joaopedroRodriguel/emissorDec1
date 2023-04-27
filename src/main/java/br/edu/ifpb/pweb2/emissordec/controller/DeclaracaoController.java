package br.edu.ifpb.pweb2.emissordec.controller;

import br.edu.ifpb.pweb2.emissordec.model.Declaracao;
import br.edu.ifpb.pweb2.emissordec.model.Estudante;
import br.edu.ifpb.pweb2.emissordec.model.Instituicao;
import br.edu.ifpb.pweb2.emissordec.model.PeriodoLetivo;
import br.edu.ifpb.pweb2.emissordec.service.DeclaracaoService;
import br.edu.ifpb.pweb2.emissordec.service.EstudanteService;
import br.edu.ifpb.pweb2.emissordec.service.InstituicaoService;
import br.edu.ifpb.pweb2.emissordec.service.PeriodoLetivoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/declaracoes")
public class DeclaracaoController {

    @Autowired
    DeclaracaoService declaracaoService;

    @Autowired
    PeriodoLetivoService periodoLetivoService;

    @Autowired
    EstudanteService estudanteService;

    @Autowired
    InstituicaoService instituicaoService;

    @RequestMapping("/form")
    public ModelAndView getForm(ModelAndView model) {
        model.addObject("declaracao", new Declaracao());
//        model.addObject("declaracao", "cadastrar");
        model.setViewName("declaracoes/form");
        return model;
    }

    @ModelAttribute("estudantesItens")
    public List<Estudante> getEstudantes() {
        return estudanteService.list();
    }

    @ModelAttribute("periodoLetivoItens")
    public List<PeriodoLetivo> getPeriodoLetivos() {
        return periodoLetivoService.list();
    }

    // Falta terminar esse método, estou tendo dúvidas em relação ao relacionamento
    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public ModelAndView cadastreDeclaracao(@Valid Declaracao declaracao, ModelAndView model, BindingResult result, RedirectAttributes attr) {
        // if (declaracao.getPeriodoLetivo() != null){
        //     Optional<PeriodoLetivo>  pl = periodoLetivoService.search(declaracao.getPeriodoLetivo().getId());
        //     declaracao.setPeriodoLetivo(pl.get());
        // }
        // if (declaracao.getEstudante() != null){
        //     Optional<Estudante>  est = estudanteService.search(declaracao.getEstudante().getId());
        //     declaracao.setEstudante(est.get());
        // }
        if (result.hasErrors()) {
            model.setViewName("declaracoes/form");
            return model;
        }
        if (declaracao.getId() == null) {
            attr.addFlashAttribute("mensagem", "Declaração cadastrada com sucesso!");
        } else {
            attr.addFlashAttribute("mensagem", "Declaração editada com sucesso!");
        }
        declaracaoService.insert(declaracao);
        model.setViewName("redirect:declaracoes");
        return model;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listeDeclaracoes(ModelAndView model) {       
//        model.addObject("declaracao", "listar");
        model.addObject("declaracoes", declaracaoService.list());
        model.setViewName("declaracoes/list");
        return model;
    }

    @RequestMapping("/{id}")
    public ModelAndView getDeclaracaoById(@PathVariable(value = "id") Long id, ModelAndView model) {
        model.addObject("declaracao", "declaracao");
        Optional<Declaracao> opDeclaracao = declaracaoService.search((id));
        if (opDeclaracao.isPresent()) {
            model.setViewName("declaracoes/form");
            model.addObject("declaracao", opDeclaracao.get());
        } else {
            model.setViewName("declaracoes/list");
            model.addObject("mensagem", "declaracao com id " + id + " não encontrado.");
        }
        return model;
    }

    @RequestMapping("/excluir/{id}")
    public ModelAndView deleteDeclaracaoById(@PathVariable("id") Long id, ModelAndView mav, RedirectAttributes attr) {
        declaracaoService.delete((id));
        attr.addFlashAttribute("mensagem", "Declaracao removida com sucesso!");
        mav.setViewName("redirect:/declaracoes");
        return mav;
    }

    @RequestMapping(value = "/edite/{id}")
    public ModelAndView editeDeclaracao(@PathVariable("id") Long id, Declaracao newDeclaracao, ModelAndView model) {
        model.setViewName("declaracoes/form");
        Declaracao declaracao = declaracaoService.update(id, newDeclaracao);
        model.addObject("declaracao", declaracao);
        return model;

    }
}
