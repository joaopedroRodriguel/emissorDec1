package br.edu.ifpb.pweb2.emissordec.controller;

import br.edu.ifpb.pweb2.emissordec.model.Estudante;
import br.edu.ifpb.pweb2.emissordec.model.Instituicao;
import br.edu.ifpb.pweb2.emissordec.model.PeriodoLetivo;
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
@RequestMapping("/periodos")
public class PeriodoLetivoController {

    @Autowired
    PeriodoLetivoService periodoLetivoService;
    @RequestMapping("/form")
    public ModelAndView getForm(PeriodoLetivo periodoLetivo, ModelAndView mav) {
        mav.setViewName("periodos/form");
        mav.addObject("periodoletivo", periodoLetivo);
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(@Valid PeriodoLetivo periodoLetivo, ModelAndView mav, BindingResult validation, RedirectAttributes attrs) {
        if (validation.hasErrors()) {
            mav.setViewName("periodos/form");
            return mav;
        }
        if (periodoLetivo.getId() == null) {
            attrs.addFlashAttribute("mensagem", "periodoLetivo cadastrado com sucesso!");

        } else {
            attrs.addFlashAttribute("mensagem", "periodoLetivo editado com sucesso!");
        }
        periodoLetivoService.insert(periodoLetivo);
        mav.setViewName("redirect:periodos");
        return mav;

    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listPeriodos(ModelAndView model) {
        model.addObject("periodoLetivo", "listar");
        model.addObject("periodoLetivo", periodoLetivoService.list());
        model.setViewName("periodos/list");
        return model;
    }

    @RequestMapping("/{id}")
    public ModelAndView getPeriodoById(@PathVariable(value = "id") Long id, ModelAndView model) {
        model.addObject("periodoLetivo", "periodoLetivo");
        Optional<PeriodoLetivo> opPeriodoLetivo = periodoLetivoService.search((id));
        if (opPeriodoLetivo.isPresent()) {
            model.setViewName("periodos/form");
            model.addObject("periodoLetivo", opPeriodoLetivo.get());
        } else {
            model.setViewName("periodos/list");
            model.addObject("mensagem", "PeriodoLetivo com id " + id + " não encontrado.");
        }
        return model;
    }

    @RequestMapping("/excluir/{id}")
    public ModelAndView deletePeriodoById(@PathVariable("id") Long id, ModelAndView mav, RedirectAttributes attr) {
        periodoLetivoService.delete((id));
        attr.addFlashAttribute("mensagem", "PeriodoLetivo removido com sucesso!");
        mav.setViewName("redirect:/periodos");
        return mav;
    }

    @RequestMapping(value = "/edite/{id}")
    public ModelAndView editePeriodo(@PathVariable("id") Long id, PeriodoLetivo periodoLetivo, ModelAndView model) {
        model.setViewName("periodos/form");
        PeriodoLetivo periodo = periodoLetivoService.update(id, periodoLetivo);
        model.addObject("periodoLetivo", periodo);
        return model;

    }

}
