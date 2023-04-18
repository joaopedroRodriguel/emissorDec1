package br.edu.ifpb.pweb2.emissordec.controller;

import br.edu.ifpb.pweb2.emissordec.model.Estudante;
import br.edu.ifpb.pweb2.emissordec.model.Instituicao;
import br.edu.ifpb.pweb2.emissordec.service.EstudanteService;
import br.edu.ifpb.pweb2.emissordec.service.InstituicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/estudantes")
public class EstudanteController {

    @Autowired
    EstudanteService estudanteService;

    @Autowired
    InstituicaoService instituicaoService;

    @RequestMapping("/form")
    public ModelAndView getForm(ModelAndView model) {
        model.addObject("estudante", new Estudante(new Instituicao()));
        model.addObject("estudante", "cadastrar");
        model.setViewName("estudantes/form");
        return model;
    }

    @ModelAttribute("instituicaoItens")
    public List<Instituicao> getInstituicoes() {
        return instituicaoService.list();
    }
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(Estudante estudante, ModelAndView model, RedirectAttributes attrs) {
        Instituicao instituicao = null;
        Optional<Instituicao> opInstituicao = instituicaoService.search(estudante.getInstituicao().getId());
        if (opInstituicao.isPresent()) {
            instituicao = opInstituicao.get();
            estudante.setInstituicao(instituicao);
            estudanteService.insert(estudante);
            attrs.addFlashAttribute("mensagem", "Estudante cadastrado com sucesso!");
            model.setViewName("redirect:estudantes");
        } else {
            model.addObject("mensagem",
                    "Estudante com id = " + estudante.getInstituicao().getId() + " não encontrado.");
            model.setViewName("estudantes/form");
        }
        return model;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listEstudantes(ModelAndView model) {
        model.addObject("estudante", "listar");
        model.addObject("estudante", estudanteService.list());
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
    public ModelAndView deleteEstudanteById(@PathVariable("id") Long id, ModelAndView mav, RedirectAttributes attr) {
        estudanteService.delete((id));
        attr.addFlashAttribute("mensagem", "Estudante removido com sucesso!");
        mav.setViewName("redirect:/estudantes");
        return mav;
    }

    @RequestMapping(value = "/edite/{id}")
    public ModelAndView editeEstudante(@PathVariable("id") Long id, Estudante newEstudante, ModelAndView model) {
        model.setViewName("estudantes/form");
        Estudante estudante = estudanteService.update(id, newEstudante);
        model.addObject("estudante", estudante);
        return model;

    }
}
