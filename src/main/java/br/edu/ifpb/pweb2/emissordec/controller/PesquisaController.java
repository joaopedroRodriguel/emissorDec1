package br.edu.ifpb.pweb2.emissordec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PesquisaController {
    
    @RequestMapping("/pesquisas")
    public String mostrarPaginaList() {
        return "pesquisas/list";
    }

}
