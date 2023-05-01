package br.edu.ifpb.pweb2.emissordec.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.emissordec.model.Instituicao;
import br.edu.ifpb.pweb2.emissordec.model.PeriodoLetivo;
import br.edu.ifpb.pweb2.emissordec.service.InstituicaoService;
import br.edu.ifpb.pweb2.emissordec.service.PeriodoLetivoService;

import javax.validation.Valid;

@Controller
@RequestMapping("/instituicoes")
public class InstituicaoController {

    @Autowired
    InstituicaoService instituicaoService;

    @Autowired
    PeriodoLetivoService periodoLetivoService;

    String mensagem;

    @RequestMapping("/form")
    public ModelAndView getForm(Instituicao instituicao,ModelAndView mav) {
        mav.setViewName("instituicoes/form");
        mav.addObject("instituicao", instituicao);
        return mav;
    }

    @ModelAttribute("menu")
    public String selectMenu() {
        return "instituicao";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(@Valid Instituicao instituicao, ModelAndView mav, BindingResult validation, RedirectAttributes attrs) {
        if (validation.hasErrors()) {
            mav.setViewName("instituicoes/form");
            return mav;
        }
        if (instituicao.getId() == null) {
            attrs.addFlashAttribute("mensagem", "Instituicao cadastrado com sucesso!");

        } else {
            attrs.addFlashAttribute("mensagem", "Instituicao editada com sucesso!");
        }
        instituicaoService.insert(instituicao);
        mav.setViewName("redirect:instituicoes");
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listInstituicoes(ModelAndView mav) {
        //model.addObject("instituicao", "listar");
        mav.addObject("instituicoes", instituicaoService.list());
        mav.setViewName("instituicoes/list");
        return mav;
    }

    @RequestMapping("/{id}")
    public ModelAndView getInstituicaoById(@PathVariable(value = "id") Long id, ModelAndView mav) {
        mav.addObject("instituicao", "encontrado");
        Optional<Instituicao> opInstituicao = instituicaoService.search((id));
        if (opInstituicao.isPresent()) {
            mav.setViewName("instituicoes/form");
            mav.addObject("instituicao", opInstituicao.get());
        } else {
            mav.setViewName("instituicoes/list");
            mav.addObject("mensagem", "instituicao com id " + id + " não encontrado.");
        }
        return mav;
    }

    @RequestMapping("/excluir/{id}")
    public ModelAndView deleteInstituicaoById(@PathVariable("id") Long id, ModelAndView mav, RedirectAttributes attr) {
        instituicaoService.delete((id));
        attr.addFlashAttribute("mensagem", "Instituicao removida com sucesso!");
        mav.setViewName("redirect:/instituicoes");
        return mav;
    }

    @ModelAttribute("periodosItens")
    public List<PeriodoLetivo> getPeriodos() {
        return periodoLetivoService.list();
    }


}
