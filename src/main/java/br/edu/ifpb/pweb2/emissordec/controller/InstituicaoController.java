package br.edu.ifpb.pweb2.emissordec.controller;

import br.edu.ifpb.pweb2.emissordec.model.Estudante;
import br.edu.ifpb.pweb2.emissordec.model.Instituicao;
import br.edu.ifpb.pweb2.emissordec.service.EstudanteService;
import br.edu.ifpb.pweb2.emissordec.service.InstituicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import java.util.Optional;

@Controller
@RequestMapping("/intituicoes")
public class InstituicaoController {

    @Autowired
    InstituicaoService instituicaoService;

    @RequestMapping("/form")
    public ModelAndView getForm(ModelAndView model) {
        model.addObject("instituicao", new Instituicao());
        model.addObject("instituicao", "cadastrar");
        model.setViewName("instituicoes/form");
        return model;
    }

    // Falta terminar esse método, estou tendo dúvidas em relação ao relacionamento
    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public String cadastreInstituicao(Instituicao instituicao, Model model, RedirectAttributes attr) {
        instituicaoService.insert(instituicao);
        attr.addFlashAttribute("mensagem", "Instituicao cadastrada com sucesso!");
        return "redirect:/instituicoes";
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listeInstituicoes(ModelAndView model) {
        model.setViewName("instituicoes/list");
        model.addObject("instituicao", "listar");
        model.addObject("instituicao", instituicaoService.list());
        return model;
    }

    @RequestMapping("/{id}")
    public ModelAndView getInstituicaoById(@PathVariable(value = "id") Long id, ModelAndView model) {
        model.addObject("instituicao", "estudante");
        Optional<Instituicao> opInstituicao = instituicaoService.search((id));
        if (opInstituicao.isPresent()) {
            model.setViewName("instituicoes/form");
            model.addObject("instituicao", opInstituicao.get());
        } else {
            model.setViewName("instituicoes/list");
            model.addObject("mensagem", "instituicoes com id " + id + " não encontrado.");
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
        Instituicao instituicao = instituicaoService.update(id, newInstituicao);
        model.addObject("instituicao", instituicao);
        return model;

    }










}
