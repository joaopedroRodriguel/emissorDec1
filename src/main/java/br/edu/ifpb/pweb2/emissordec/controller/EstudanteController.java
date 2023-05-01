package br.edu.ifpb.pweb2.emissordec.controller;

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
        //model.addObject("estudante", "listar");
        model.addObject("estudantes", estudanteService.list());
        model.setViewName("estudantes/list");
        return model;
    }

    @RequestMapping("/{id}")
    public ModelAndView getEstudanteById(@PathVariable(value = "id") Long id, ModelAndView model) {
        model.addObject("estudante", "estudante");
        Optional<Estudante> opEstudante = estudanteService.search((id));
        if (opEstudante.isPresent()) {
            model.setViewName("estudantes/form");
            model.addObject("estudante", opEstudante.get());
        } else {
            model.setViewName("estudantes/list");
            model.addObject("mensagem", "estudante com id " + id + " não encontrado.");
        }
        return model;
    }

    @RequestMapping("/excluir/{id}")
    public ModelAndView deleteEstudanteById(@PathVariable("id") Long id, ModelAndView model, RedirectAttributes attr) {
        estudanteService.delete((id));
        attr.addFlashAttribute("mensagem", "Estudante removido com sucesso!");
        model.setViewName("redirect:/estudantes");
        return model;
    }

    @RequestMapping(value = "/edite/{id}")
    public ModelAndView editeEstudante(@PathVariable("id") Long id, Estudante newEstudante, ModelAndView model) {
        model.setViewName("estudantes/form");
        Optional<Estudante> estudante = estudanteService.search(id);
        model.addObject("estudante", estudante.get());
        model.addObject("titulo", "editado");
        return model;
    }

    @ModelAttribute("instituicaoItens")
    public List<Instituicao> getInstituicoes() {
        return instituicaoService.list();
    }
}
