package br.edu.ifpb.pweb2.emissordec.controller;

import br.edu.ifpb.pweb2.emissordec.model.PeriodoLetivo;
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

    @ModelAttribute("menu")
    public String selectMenu() {
        return "periodoLetivo";
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
    public ModelAndView listPeriodos(ModelAndView mav) {
        mav.addObject("periodoLetivo", "listar");
        mav.addObject("periodoLetivo", periodoLetivoService.list());
        mav.setViewName("periodos/list");
        return mav;
    }

    @ModelAttribute("periodos")
    public List<PeriodoLetivo> periodoLetivos(){
        return  periodoLetivoService.list();
    }

    @RequestMapping("/{id}")
    public ModelAndView getPeriodoById(@PathVariable(value = "id") Long id, ModelAndView mav) {
        mav.addObject("periodoLetivo", "periodoLetivo");
        Optional<PeriodoLetivo> opPeriodoLetivo = periodoLetivoService.search((id));
        if (opPeriodoLetivo.isPresent()) {
            mav.setViewName("periodos/form");
            mav.addObject("periodoLetivo", opPeriodoLetivo.get());
        } else {
            mav.setViewName("periodos/list");
            mav.addObject("mensagem", "PeriodoLetivo com id " + id + " não encontrado.");
        }
        return mav;
    }

    @RequestMapping("/excluir/{id}")
    public ModelAndView deletePeriodoById(@PathVariable("id") Long id, ModelAndView mav, RedirectAttributes attr) {
        periodoLetivoService.delete((id));
        attr.addFlashAttribute("mensagem", "PeriodoLetivo removido com sucesso!");
        mav.setViewName("redirect:/periodos");
        return mav;
    }

    @RequestMapping(value = "/edite/{id}")
    public ModelAndView editePeriodo(@PathVariable("id") Long id, PeriodoLetivo periodoLetivo, ModelAndView mav) {
        mav.setViewName("periodos/form");
        Optional<PeriodoLetivo> periodo = periodoLetivoService.search(id);
        mav.addObject("periodo", periodo.get());
        mav.addObject("titulo", "editado");
        return mav;

    }

}
