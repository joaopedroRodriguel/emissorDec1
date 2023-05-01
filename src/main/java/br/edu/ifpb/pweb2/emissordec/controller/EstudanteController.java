package br.edu.ifpb.pweb2.emissordec.controller;

import br.edu.ifpb.pweb2.emissordec.model.Declaracao;
import br.edu.ifpb.pweb2.emissordec.model.Estudante;
import br.edu.ifpb.pweb2.emissordec.model.Instituicao;
import br.edu.ifpb.pweb2.emissordec.service.EstudanteService;
import br.edu.ifpb.pweb2.emissordec.service.InstituicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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

    @RequestMapping("/form")
    public ModelAndView getForm(Estudante estudante, ModelAndView mav) {
        mav.setViewName("estudantes/form");
        mav.addObject("estudante", estudante);
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView declaracoes(ModelAndView mav,@PathVariable("id") Long id) {
        mav.setViewName("estudantes/list");
        mav.addObject("declaracoes", this.declaracoes(id));
        return mav;
    }
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(@Valid Estudante estudante, ModelAndView mav, BindingResult validation, RedirectAttributes attrs) {
        if (validation.hasErrors()) {
            mav.setViewName("estudantes/form");
            return mav;
        }
        if (estudante.getId() == null) {
            attrs.addFlashAttribute("mensagem", "Estudante cadastrado com sucesso!");

        } else {
            attrs.addFlashAttribute("mensagem", "Estudante editado com sucesso!");
        }
        estudanteService.insert(estudante);
        mav.setViewName("redirect:estudantes");
        return mav;
    }

    @ModelAttribute("menu")
    public String selectMenu() {
        return "estudante";
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listEstudantes(ModelAndView model) {
        model.addObject("estudantes", estudanteService.list());
        model.setViewName("estudantes/list");
        return model;
    }

    public List<Declaracao> declaracoes(Long idEstudante) {
        Estudante estudante = this.estudanteService.search(idEstudante);
        return  estudante.getDeclaracoes();
    }

    @RequestMapping("/excluir/{id}")
    public ModelAndView deleteEstudanteById(@PathVariable("id") Long id, ModelAndView model, RedirectAttributes attr) {
        estudanteService.delete((id));
        attr.addFlashAttribute("mensagem", "Estudante removido com sucesso!");
        model.setViewName("redirect:/estudantes");
        return model;
    }

    @RequestMapping(value = "/edite/{id}")
    public ModelAndView editEstudante(ModelAndView modelAndView, @PathVariable("idEstudante") Long idEstudante
    ) {
        List<Instituicao> instituicoes = new ArrayList<>();
        instituicoes.addAll(this.estudanteService.listarInstituicoes());
        instituicoes.addAll(this.instituicaoService.list());
        modelAndView.setViewName("estudantes/form");
        modelAndView.addObject("estudante", estudanteService.search(idEstudante));
        modelAndView.addObject("instituicoes", instituicoes);
        return modelAndView;
    }

    @ModelAttribute("instituicoes")
    public List<Instituicao> instituicoes() {
        return this.estudanteService.listarInstituicoes();
    }

    @ModelAttribute("estudantes")
    public List<Estudante> estudantes() {
        return this.estudanteService.list();
    }


}
