package br.edu.ifpb.pweb2.emissordec.controller;

import br.edu.ifpb.pweb2.emissordec.model.Declaracao;
import br.edu.ifpb.pweb2.emissordec.model.Estudante;
import br.edu.ifpb.pweb2.emissordec.model.Instituicao;
import br.edu.ifpb.pweb2.emissordec.repository.EstudanteRepository;
import br.edu.ifpb.pweb2.emissordec.service.DeclaracaoService;
import br.edu.ifpb.pweb2.emissordec.service.EstudanteService;
import br.edu.ifpb.pweb2.emissordec.service.InstituicaoService;
import br.edu.ifpb.pweb2.emissordec.ui.NavPage;
import br.edu.ifpb.pweb2.emissordec.ui.NavePageBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@Controller
@RequestMapping("/estudantes")
public class EstudanteController {
    @Autowired
    EstudanteService estudanteService;
    @Autowired
    InstituicaoService instituicaoService;
    @Autowired
    DeclaracaoService declaracaoService;
    @Autowired
    EstudanteRepository estudanteRepository;
    @RequestMapping("/form")
    public ModelAndView getForm(Estudante estudante, ModelAndView mav) {
        mav.setViewName("estudantes/form");
        mav.addObject("estudante", estudante);
        return mav;
    }
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(@Valid Estudante estudante, ModelAndView mav, BindingResult validation, RedirectAttributes attrs) {
        if (validation.hasErrors()) {
            mav.setViewName("estudantes/form");
            return mav;
        }
        if (estudante.getId() == null) {
            attrs.addFlashAttribute("message", "Estudante cadastrado com sucesso!");

        } else {
            attrs.addFlashAttribute("message", "Estudante editado com sucesso!");
        }
        estudanteService.insert(estudante);
        mav.setViewName("redirect:estudantes");
        return mav;
    }
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listEstudantes(ModelAndView mav, @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "3") int size) {

        Pageable paging = PageRequest.of(page - 1, size);
        Page<Estudante> pageEstudantes = estudanteRepository.findAll(paging);
        NavPage navPage = NavePageBuilder.newNavPage(pageEstudantes.getNumber() + 1,
                pageEstudantes.getTotalElements(), pageEstudantes.getTotalPages(), size);
        mav.addObject("estudantes", pageEstudantes);
        mav.addObject("navPage", navPage);
        mav.setViewName("estudantes/list");
        return mav;
    }
    @RequestMapping("/{id}")
    public ModelAndView getEstudanteById(@PathVariable(value = "id") Long id, ModelAndView mav) {
        mav.addObject("estudante", "estudante");
        Optional<Estudante> opEstudante = estudanteService.search((id));
        if (opEstudante.isPresent()) {
            mav.setViewName("estudantes/form");
            mav.addObject("estudante", opEstudante.get());
        } else {
            mav.setViewName("estudantes/list");
            mav.addObject("message", "estudante com id " + id + " não encontrado.");
        }
        return mav;
    }
    @RequestMapping("/excluir/{id}")
    public ModelAndView deleteEstudanteById(@PathVariable("id") Long id, ModelAndView mav, RedirectAttributes attr) {
        Optional<Estudante> estudante = estudanteService.search(id);
        estudante.get().setInstituicao(null);
        estudanteService.delete((id));
        attr.addFlashAttribute("message", "Estudante removido com sucesso!");
        mav.setViewName("redirect:/estudantes");
        return mav;
    }
    @RequestMapping(value = "/edite/{id}")
    public ModelAndView editeEstudante(@PathVariable("id") Long id, Estudante newEstudante, ModelAndView mav) {
        mav.setViewName("estudantes/form");
        Optional<Estudante> estudante = estudanteService.search(id);
        mav.addObject("estudante", estudante.get());
        mav.addObject("titulo", "editado");
        return mav;
    }
    @ModelAttribute("instituicaoItens")
    public List<Instituicao> getInstituicoes() {
        return instituicaoService.list();
    }

    @ModelAttribute("declaracaoItens")
    public List<Declaracao> getDeclaracoes() {
        return declaracaoService.list();
    }
}
