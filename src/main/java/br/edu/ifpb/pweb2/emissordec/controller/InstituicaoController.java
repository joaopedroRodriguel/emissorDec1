package br.edu.ifpb.pweb2.emissordec.controller;

import br.edu.ifpb.pweb2.emissordec.model.Estudante;
import br.edu.ifpb.pweb2.emissordec.model.Instituicao;
import br.edu.ifpb.pweb2.emissordec.model.PeriodoLetivo;
import br.edu.ifpb.pweb2.emissordec.service.EstudanteService;
import br.edu.ifpb.pweb2.emissordec.service.InstituicaoService;
import br.edu.ifpb.pweb2.emissordec.service.PeriodoLetivoService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/intituicoes")
public class InstituicaoController {

    @Autowired
    InstituicaoService instituicaoService;

    @Autowired
    PeriodoLetivoService periodoLetivoService;

    @RequestMapping("/form")
    public ModelAndView getForm(ModelAndView model) {
        model.addObject("instituicao", new Instituicao(new PeriodoLetivo()));
        model.addObject("titulo", "cadastrado");
        model.setViewName("instituicoes/form");
        return model;
    }

    @ModelAttribute("periodosItens")
    public List<PeriodoLetivo> getPeriodos() {
        return periodoLetivoService.list();
    }
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(Instituicao instituicao, ModelAndView model, RedirectAttributes attrs) {
        if (instituicao.getPeriodos() != null) {
            Optional<PeriodoLetivo> opPeriodoLetivo = periodoLetivoService.search(instituicao.getPeriodos().get());
            instituicao.setPeriodos(opPeriodoLetivo.get());
        }

        if (instituicao.getId() == null) {
            attrs.addFlashAttribute("mensagem", "Instituicao cadastrada com sucesso!");
        } else {
            attrs.addFlashAttribute("mensagem", "Instituicao editada com sucesso!");
        }
        instituicaoService.insert(instituicao);

        model.setViewName("redirect:instituicoes");

        return model;

    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listInstituicoes(ModelAndView model) {
        //model.addObject("instituicao", "listar");
        model.addObject("instituicoes", instituicaoService.list());
        model.setViewName("instituicoes/list");
        return model;
    }

    @RequestMapping("/{id}")
    public ModelAndView getInstituicaoById(@PathVariable(value = "id") Long id, ModelAndView model) {
        model.addObject("instituicao", "encontrado");
        Optional<Instituicao> opInstituicao = instituicaoService.search((id));
        if (opInstituicao.isPresent()) {
            model.setViewName("instituicoes/form");
            model.addObject("instituicao", opInstituicao.get());
        } else {
            model.setViewName("instituicoes/list");
            model.addObject("mensagem", "instituicao com id " + id + " não encontrado.");
        }
        return model;
    }

    @RequestMapping("/excluir/{id}")
    public ModelAndView deleteInstituicaoById(@PathVariable("id") Long id, ModelAndView mav, RedirectAttributes attr) {
        instituicaoService.delete((id));
        attr.addFlashAttribute("mensagem", "Instituicao removida com sucesso!");
        mav.setViewName("redirect:/instituicoes");
        return mav;
    }

    @RequestMapping(value = "/edite/{id}")
    public ModelAndView editeInstituicao(@PathVariable("id") Long id, Instituicao newInstituicao, ModelAndView model) {
        model.setViewName("instituicoes/form");
        Optional<Instituicao> instituicao = instituicaoService.search(id);
        model.addObject("instituicao", instituicao.get());
        model.addObject("titulo", "editado");
        return model;
    }










}
