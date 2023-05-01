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
    public ModelAndView getForm(Declaracao declaracao, ModelAndView mav) {
        mav.setViewName("declaracoes/form");
        mav.addObject("declaracao", declaracao);
        return mav;
    }

    @ModelAttribute("estudantesItens")
    public List<Estudante> getEstudantes() {
        return estudanteService.list();
    }

    @ModelAttribute("menu")
    public String selectMenu() {
        return "declaracao";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(@Valid Declaracao declaracao, ModelAndView mav, BindingResult validation, RedirectAttributes attrs) {
        if (validation.hasErrors()) {
            mav.setViewName("declaracoes/form");
            return mav;
        }
        if (declaracao.getId() == null) {
            attrs.addFlashAttribute("mensagem", "Declaracao cadastrada com sucesso!");

        } else {
            attrs.addFlashAttribute("mensagem", "Declaracao editada com sucesso!");
        }
        declaracaoService.insert(declaracao);
        mav.setViewName("redirect:declaracoes");
        return mav;

    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listeDeclaracoes(ModelAndView mav) {
        mav.addObject("declaracoes", declaracaoService.list());
        mav.setViewName("declaracoes/list");
        return mav;
    }

    @RequestMapping("/{id}")
    public ModelAndView getDeclaracaoById(@PathVariable(value = "id") Long id, ModelAndView mav) {
        mav.addObject("declaracao", "declaracao");
        Optional<Declaracao> opDeclaracao = declaracaoService.search((id));
        if (opDeclaracao.isPresent()) {
            mav.setViewName("declaracoes/form");
            mav.addObject("declaracao", opDeclaracao.get());
        } else {
            mav.setViewName("declaracoes/list");
            mav.addObject("mensagem", "declaracao com id " + id + " não encontrado.");
        }
        return mav;
    }

    @RequestMapping("/excluir/{id}")
    public ModelAndView deleteDeclaracaoById(@PathVariable("id") Long id, ModelAndView mav, RedirectAttributes attr) {
        declaracaoService.delete((id));
        attr.addFlashAttribute("mensagem", "Declaracao removida com sucesso!");
        mav.setViewName("redirect:/declaracoes");
        return mav;
    }

    @RequestMapping(value = "/edite/{id}")
    public ModelAndView editeDeclaracao(@PathVariable("id") Long id, Declaracao newDeclaracao, ModelAndView mav) {
        mav.setViewName("declaracoes/form");
        Optional<Declaracao> declaracao = declaracaoService.search(id);
        mav.addObject("declaracao", declaracao.get());
        return mav;

    }

    @ModelAttribute("periodoLetivoItens")
    public List<PeriodoLetivo> getPeriodoLetivos() {
        return periodoLetivoService.list();
    }
}
