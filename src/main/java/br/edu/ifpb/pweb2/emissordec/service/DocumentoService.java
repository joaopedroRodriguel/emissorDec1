package br.edu.ifpb.pweb2.emissordec.service;

import br.edu.ifpb.pweb2.emissordec.model.Declaracao;
import br.edu.ifpb.pweb2.emissordec.model.Documento;
import br.edu.ifpb.pweb2.emissordec.repository.DeclaracaoRepository;
import br.edu.ifpb.pweb2.emissordec.repository.DocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class DocumentoService {
    @Autowired
    private DocumentoRepository documentoRepository;
    @Autowired
    private DeclaracaoRepository declaracaoRepository;
    public Documento grave(Declaracao declaracao, String nomeArquivo, byte[] bytes) throws IOException {
        Documento documento = new Documento(nomeArquivo, bytes);
        declaracao.setDocumento(documento);
        documentoRepository.save(documento);
        return documento;
    }
    public Documento getDocumento(Long id) {
        return documentoRepository.findById(id).get();
    }

    public Optional<Documento> getDocumentoOf(Long idDeclaracao) {
        return Optional.ofNullable(declaracaoRepository.findDocumentoById(idDeclaracao));
    }

}
