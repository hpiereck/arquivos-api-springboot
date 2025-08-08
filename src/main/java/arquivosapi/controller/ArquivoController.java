package arquivosapi.controller;

import arquivosapi.domain.dto.ArquivoDTO;
import arquivosapi.domain.model.Arquivo;
import arquivosapi.domain.service.ArquivoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.Normalizer;
import java.util.List;
import java.util.UUID;

@RestController
public class ArquivoController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("LOG_PERSONALIZADO");

    private final ArquivoService arquivoService;

    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    @PostMapping
    public ResponseEntity<String> salvar(@RequestParam("arquivo") MultipartFile arquivo) {
        String logId = UUID.randomUUID().toString();
        String metodo = "ArquivoController.salvar";

        try {
            String nomeOriginal = arquivo.getOriginalFilename();
            long tamanhoArquivo = arquivo.getSize();

            if (temAcento(nomeOriginal)) {
                throw new RuntimeException("O nome do arquivo não pode conter acentos.");
            }

            long tamanhoMaximo = 5 * 1024 * 1024; // 5MB em bytes
            if (tamanhoArquivo > tamanhoMaximo) {
                throw new RuntimeException("O arquivo não pode exceder 5MB.");
            }

            Long id = arquivoService.salvar(arquivo);

            Arquivo arquivoEntity = arquivoService.buscarEntidade(id);

            return ResponseEntity.ok("Arquivo salvo com sucesso. ID: " + id);

        } catch (Exception e) {
            throw e;
        }
    }

    private boolean temAcento(String texto) {
        if (texto == null) return false;
        String textoSemAcentos = Normalizer.normalize(texto, Normalizer.Form.NFD);
        return !texto.equals(textoSemAcentos);
    }

    @GetMapping
    public ResponseEntity<List<ArquivoDTO>> listar(@RequestParam(required = false) Boolean somenteNaoExcluidos) {
        String logId = UUID.randomUUID().toString();
        String metodo = "ArquivoController.listar";

        try {
            List<ArquivoDTO> arquivos = arquivoService.listar(somenteNaoExcluidos);

            return ResponseEntity.ok(arquivos);

        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        String logId = UUID.randomUUID().toString();
        String metodo = "ArquivoController.deletar";

        try {
            Arquivo arquivoEntity = arquivoService.buscarEntidade(id);

            arquivoService.deletar(id);

            return ResponseEntity.ok("Arquivo apagado com sucesso");

        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getArquivoDescriptografado(@PathVariable Long id) {
        String logId = UUID.randomUUID().toString();
        String metodo = "ArquivoController.getArquivoDescriptografado";

        try {
            Arquivo arquivoEntity = arquivoService.buscarEntidade(id);

            byte[] dados = arquivoService.getArquivoDescriptografado(id);


            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + id + "\"")
                    .body(dados);
        } catch (Exception e) {
            throw e;
        }
    }
}
