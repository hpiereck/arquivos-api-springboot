package arquivosapi.domain.dto;

import arquivosapi.domain.model.Arquivo;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class ArquivoDTO {
    private Long id;
    private String caminhoOriginal;
    private String caminhoStorage;
    private String nomeOriginal;
    private String extensao;
    private Long tamanho;
    private String moduloSistema;
    private String dataInclusao;
    private Long fkIdPessoa;
    private String status;
    private String descricao;
    private Boolean privado;
    private String uri;
    private Boolean excluido;

    public ArquivoDTO(Arquivo arquivo) {
        this.id = arquivo.getId();
        this.caminhoOriginal = arquivo.getCaminhoOriginal();
        this.caminhoStorage = arquivo.getCaminhoStorage();
        this.nomeOriginal = arquivo.getNomeOriginal();
        this.extensao = arquivo.getExtensao();
        this.tamanho = arquivo.getTamanho();
        this.moduloSistema = arquivo.getModuloSistema();
        this.dataInclusao = arquivo.getDataInclusao() != null
                ? arquivo.getDataInclusao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                : null;
        this.fkIdPessoa = arquivo.getFkIdPessoa();
        this.status = arquivo.getStatus();
        this.descricao = arquivo.getDescricao();
        this.privado = arquivo.getPrivado();
        this.uri = arquivo.getUri();
        this.excluido = arquivo.getExcluido();
    }
}
