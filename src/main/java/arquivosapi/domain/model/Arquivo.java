package arquivosapi.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "arquivos_storage")
@Data
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String caminhoOriginal;
    private String caminhoStorage;
    private String nomeOriginal;
    private String extensao;
    private Long tamanho;
    private String moduloSistema;
    private LocalDateTime dataInclusao;
    private Long fkIdPessoa;
    private String status;
    private String descricao;
    private Boolean privado;
    private String uri;
    private Boolean excluido = false;

}
