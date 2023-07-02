package br.edu.ifpb.pweb2.emissordec.controller;

import br.edu.ifpb.pweb2.emissordec.model.Declaracao;
import br.edu.ifpb.pweb2.emissordec.model.Documento;
import br.edu.ifpb.pweb2.emissordec.model.Estudante;
import br.edu.ifpb.pweb2.emissordec.model.PeriodoLetivo;
import br.edu.ifpb.pweb2.emissordec.repository.DeclaracaoRepository;
import br.edu.ifpb.pweb2.emissordec.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/declaracoes")
public class DeclaracaoController {
    @Autowired
    DeclaracaoService declaracaoService;
    @Autowired
    PeriodoLetivoService periodoLetivoService;
    @Autowired
    EstudanteService estudanteService;
    @Autowired
    InstituicaoService instituicaoService;

    @Autowired
    DocumentoService documentoService;

    @Autowired
    DeclaracaoRepository declaracaoRepository;

    @ModelAttribute("menu")
    public String selectMenu() {
        return "declaracao";
    }
    @RequestMapping("/form")
    public ModelAndView getForm(Declaracao declaracao, ModelAndView mav) {
        mav.setViewName("declaracoes/form");
        mav.addObject("declaracao", declaracao);
        return mav;
    }
    @RequestMapping("/consultas")
    public ModelAndView getConsultas(Declaracao declaracao, ModelAndView mav) {
        mav.setViewName("declaracoes/queries");
        mav.addObject("declaracao", declaracao);
        return mav;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(@Valid Declaracao declaracao, BindingResult validation, ModelAndView mav, RedirectAttributes attrs) {
        if (validation.hasErrors()) {
            mav.addObject("message", validation.getAllErrors());
            mav.setViewName("declaracoes/form");
            return mav;
        }
        if (declaracao.getId() == null) {
            attrs.addFlashAttribute("message", "Declaracao cadastrada com sucesso!");

        } else {
            attrs.addFlashAttribute("message", "Declaracao editada com sucesso!");
        }
        declaracao.getEstudante().setDeclaracaoAtual(null);
        declaracao.getEstudante().setDeclaracaoAtual(declaracao);                                   
        declaracaoService.insert(declaracao);
        mav.setViewName("redirect:declaracoes");
        return mav;
    }    
    

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listeDeclaracoes(ModelAndView mav) {
        mav.addObject("declaracoes", declaracaoService.list());
        mav.setViewName("declaracoes/list");
        return mav;
    }

    @RequestMapping("/vencidas")
    public ModelAndView listarDeclaracoesVencidas(ModelAndView mav) {
        mav.addObject("declaracoes", declaracaoService.declaracoesVencidas());
        mav.setViewName("declaracoes/list");
        return mav;
    }

    
    @RequestMapping("/{id}")
    public ModelAndView getDeclaracaoById(@PathVariable(value = "id") Long id, ModelAndView mav) {
        mav.addObject("declaracao", "declaracao");
        Optional<Declaracao> opDeclaracao = declaracaoService.search((id));
        if (opDeclaracao.isPresent()) {
            mav.setViewName("declaracoes/form");
            mav.addObject("declaracao", opDeclaracao.get());
        } else {
            mav.setViewName("declaracoes/list");
            mav.addObject("message", "declaracao com id " + id + " não encontrado.");
        }
        return mav;
    }


    @RequestMapping("/declaracoesValidas")
    public ModelAndView getDeclaracaoVencida(@RequestParam("dias") Long dias, ModelAndView mav) {
        mav.setViewName("declaracoes/list");                
        mav.addObject("declaracoes", declaracaoService.DeclaracoesPorVencer(dias));                            
        return mav;
    }


    @RequestMapping("/excluir/{id}")
    public ModelAndView deleteDeclaracaoById(@PathVariable("id") Long id, ModelAndView mav, RedirectAttributes attr) {
        Optional<Declaracao> declaracao = declaracaoService.search(id);
        declaracao.get().setEstudante(null);
        declaracao.get().setPeriodoLetivo(null);
        declaracaoService.delete((id));
        attr.addFlashAttribute("message", "Declaracao removida com sucesso!");
        mav.setViewName("redirect:/declaracoes");
        return mav;
    }
    @RequestMapping(value = "/edite/{id}")
    public ModelAndView editeDeclaracao(@PathVariable("id") Long id, Declaracao newDeclaracao, ModelAndView mav) {
        mav.setViewName("declaracoes/form");
        Optional<Declaracao> declaracao = declaracaoService.search(id);
        mav.addObject("declaracao", declaracao.get());
        //declaracao.get().getEstudante().setDeclaracaoAtual(newDeclaracao);
        return mav;
    }    

    @RequestMapping(value = "/{id}/documentos/upload", method = RequestMethod.POST)
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile arquivo,
                                   @PathVariable("id") Long id, ModelAndView mav) {
        String mensagem = "";
        String proxPagina = "";
        try {
            Optional<Declaracao> opDeclaracao = declaracaoRepository.findById(id);
            Declaracao declaracao = null;
            if (opDeclaracao.isPresent()) {
                declaracao = opDeclaracao.get();
                String nomeArquivo = StringUtils.cleanPath(arquivo.getOriginalFilename());
                Documento documento = documentoService.grave(declaracao, nomeArquivo, arquivo.getBytes());
                documento.setUrl(this.buildUrl(declaracao.getId(), documento.getId()));
                declaracaoRepository.save(declaracao);
                mensagem = "Documento carregado com sucesso: " + arquivo.getOriginalFilename();
                proxPagina = String.format("redirect:/declaracoes/%s/documentos", declaracao.getId().toString());
            }
        } catch (Exception e) {
            mensagem = "Não foi possível carregar o documento: " + arquivo.getOriginalFilename() + "! "
                    + e.getMessage();
            proxPagina = "/declaracoes/documentos/form";
        }
        mav.addObject("mensagem", mensagem);
        mav.setViewName(proxPagina);
        return mav;
    }

    private String buildUrl(Long idDeclaracao, Long idDocumento) {
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/declaracoes/")
                .path(idDeclaracao.toString())
                .path("/documentos/")
                .path(String.valueOf(idDocumento))
                .toUriString();
        return fileDownloadUri;
    }

    @RequestMapping("/{id}/download")
    public ResponseEntity<byte[]> getDocumento(@PathVariable("id") Long idDoc) {
        Declaracao declaracao = declaracaoService.search(idDoc).get();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + declaracao.getNome() + "\"")
                .body(declaracao.getDocumento());
    }


    @RequestMapping(method = RequestMethod.GET, value = "/declaracoesvencidas")
    public ResponseEntity<?> declaracoesvencidas() {
        return new ResponseEntity<>(declaracaoService.declaracoesVencidas(), HttpStatus.ACCEPTED);
    }

    @ModelAttribute("periodoLetivoItens")
    public List<PeriodoLetivo> getPeriodoLetivos() {
        return estudanteService.list().get(0).getInstituicao().getPeriodos();
    }
    @ModelAttribute("estudantesItens")
    public List<Estudante> getEstudantes() {
        return estudanteService.list();
    }

   

}
