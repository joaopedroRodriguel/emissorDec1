package br.edu.ifpb.pweb2.emissordec.controller;

import br.edu.ifpb.pweb2.emissordec.model.Declaracao;
import br.edu.ifpb.pweb2.emissordec.model.Instituicao;
import br.edu.ifpb.pweb2.emissordec.service.DeclaracaoService;
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
@RequestMapping("/declaracoes")
public class DeclaracaoController {

    @Autowired
    DeclaracaoService declaracaoService;

    @RequestMapping("/form")
    public ModelAndView getForm(ModelAndView model) {
        model.addObject("declaracao", new Declaracao());
        model.addObject("declaracao", "cadastrar");
        model.setViewName("declaracoes/form");
        return model;
    }

    // Falta terminar esse método, estou tendo dúvidas em relação ao relacionamento
    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public String cadastreDeclaracao(Declaracao declaracao, Model model, RedirectAttributes attr) {
        declaracaoService.insert(declaracao);
        attr.addFlashAttribute("mensagem", "Declaracao cadastrada com sucesso!");
        return "redirect:/declaracoes";
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listeDeclaracoes(ModelAndView model) {
        model.setViewName("declaracoes/list");
        model.addObject("declaracao", "listar");
        model.addObject("declaracao", declaracaoService.list());
        return model;
    }

    @RequestMapping("/{id}")
    public ModelAndView getDeclaracaoById(@PathVariable(value = "id") Long id, ModelAndView model) {
        model.addObject("declaracao", "declaracao");
        Optional<Declaracao> opDeclaracao = declaracaoService.search((id));
        if (opDeclaracao.isPresent()) {
            model.setViewName("declaracoes/form");
            model.addObject("declaracao", opDeclaracao.get());
        } else {
            model.setViewName("declaracoes/list");
            model.addObject("mensagem", "declaracao com id " + id + " não encontrado.");
        }
        return model;
    }

    @RequestMapping("/excluir/{id}")
    public ModelAndView deleteDeclaracaoById(@PathVariable("id") Long id, ModelAndView mav, RedirectAttributes attr) {
        declaracaoService.delete((id));
        attr.addFlashAttribute("mensagem", "Declaracao removida com sucesso!");
        mav.setViewName("redirect:/declaracoes");
        return mav;
    }

    @RequestMapping(value = "/edite/{id}")
    public ModelAndView editeDeclaracao(@PathVariable("id") Long id, Declaracao newDeclaracao, ModelAndView model) {
        model.setViewName("declaracoes/form");
        Declaracao declaracao = declaracaoService.update(id, newDeclaracao);
        model.addObject("declaracao", declaracao);
        return model;

    }
}
