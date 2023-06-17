package br.edu.ifpb.pweb2.emissordec.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import br.edu.ifpb.pweb2.emissordec.model.Instituicao;
import br.edu.ifpb.pweb2.emissordec.model.PeriodoLetivo;
import br.edu.ifpb.pweb2.emissordec.repository.InstituicaoRepository;
import br.edu.ifpb.pweb2.emissordec.service.InstituicaoService;
import br.edu.ifpb.pweb2.emissordec.service.PeriodoLetivoService;
import br.edu.ifpb.pweb2.emissordec.ui.NavPage;
import br.edu.ifpb.pweb2.emissordec.ui.NavePageBuilder;

import javax.validation.Valid;

@Controller
@RequestMapping("/instituicoes")
public class InstituicaoController {
    @Autowired
    InstituicaoService instituicaoService;
    @Autowired
    PeriodoLetivoService periodoLetivoService;
    @Autowired
    InstituicaoRepository instituicaoRepository;
    @RequestMapping("/form")
    public ModelAndView getForm(Instituicao instituicao,ModelAndView mav) {
        mav.setViewName("instituicoes/form");
        mav.addObject("instituicao", instituicao);
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(@Valid Instituicao instituicao, BindingResult validation, ModelAndView mav, RedirectAttributes attrs) {
        if (validation.hasErrors()) {
            mav.addObject("message", "Erros de validação! Corrija-os e tente novamente.");
            mav.setViewName("instituicoes/form");
            return mav;
        }
        if (instituicao.getId() == null) {
            attrs.addFlashAttribute("message", "Instituicao cadastrado com sucesso!");

        } else {
            attrs.addFlashAttribute("message", "Instituicao editada com sucesso!");
        }
        instituicaoService.insert(instituicao);
        mav.setViewName("redirect:instituicoes");
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listInstituicoes(ModelAndView mav, @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page - 1, size);
        Page<Instituicao> pageInstituicoes = instituicaoRepository.findAll(paging);
        NavPage navPage = NavePageBuilder.newNavPage(pageInstituicoes.getNumber() + 1,
                pageInstituicoes.getTotalElements(), pageInstituicoes.getTotalPages(), size);
        mav.addObject("instituicoes", pageInstituicoes);
        mav.addObject("navPage", navPage);
        mav.setViewName("instituicoes/list");
        return mav;
    }

    @RequestMapping("/{id}")
    public ModelAndView getInstituicaoById(@PathVariable(value = "id") Long id, ModelAndView mav) {
        mav.addObject("instituicao", "encontrada");
        Optional<Instituicao> opInstituicao = instituicaoService.search((id));
        if (opInstituicao.isPresent()) {
            mav.setViewName("instituicoes/form");
            mav.addObject("instituicao", opInstituicao.get());
        } else {
            mav.setViewName("instituicoes/list");
            mav.addObject("message", "instituicao com id " + id + " não encontrado.");
        }
        return mav;
    }
    @RequestMapping("/excluir/{id}")
    public ModelAndView deleteInstituicaoById(@PathVariable("id") Long id, ModelAndView mav, RedirectAttributes attr) {
        Optional<Instituicao> instituicao = instituicaoService.search(id);
        instituicao.get().setEstudantes(null);
        instituicaoService.delete((id));
        attr.addFlashAttribute("message", "Instituicao removida com sucesso!");
        mav.setViewName("redirect:/instituicoes");
        return mav;
    }
    @RequestMapping(value = "/edite/{id}")
    public ModelAndView editeInstituicao(@PathVariable("id") Long id, Instituicao newInstituicao, ModelAndView mav) {
        mav.setViewName("instituicoes/form");
        Optional<Instituicao> instituicao = instituicaoService.search(id);
        mav.addObject("instituicao", instituicao.get());
        mav.addObject("titulo", "editado");
        return mav;
    }
    @ModelAttribute("periodosItens")
    public List<PeriodoLetivo> getPeriodos() {
        return periodoLetivoService.list();
    }


}
