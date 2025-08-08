package arquivosapi.domain.service;

import arquivosapi.domain.dto.ArquivoDTO;
import arquivosapi.domain.model.Arquivo;
import arquivosapi.domain.repository.ArquivoRepository;
import arquivosapi.infrastructure.util.CriptografiaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ArquivoService {

    private final Path diretorioArquivos = Paths.get("uploads");

    @Autowired
    private ArquivoRepository arquivoRepository;

    public ArquivoService() {
        try {
            Files.createDirectories(diretorioArquivos);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível criar pasta de uploads");
        }
    }

    public Long salvar(MultipartFile arquivo) {
        try {
            if (arquivo == null || arquivo.isEmpty()) {
                throw new RuntimeException("Arquivo está vazio ou nulo.");
            }

            String nomeOriginal = arquivo.getOriginalFilename();
            if (nomeOriginal == null || nomeOriginal.trim().isEmpty()) {
                throw new RuntimeException("Nome do arquivo é inválido.");
            }

            String extensao = getExtensao(nomeOriginal);
            String nomeUnico = UUID.randomUUID().toString().replace("-","") + "." + extensao;
            Path caminhoStorage = diretorioArquivos.resolve(nomeUnico);

            byte[] dadosCriptografados = CriptografiaUtil.processarAES(arquivo.getBytes(), Cipher.ENCRYPT_MODE);
            Files.write(caminhoStorage, dadosCriptografados, StandardOpenOption.CREATE_NEW);

            nomeOriginal = nomeOriginal.replaceAll("\\s+", "_");

            Arquivo entidade = new Arquivo();
            entidade.setNomeOriginal(nomeOriginal);
            entidade.setExtensao(extensao);
            entidade.setTamanho(arquivo.getSize());
            entidade.setCaminhoOriginal(nomeUnico);
            entidade.setCaminhoStorage(caminhoStorage.toString());
            entidade.setDataInclusao(LocalDateTime.now());
            entidade.setPrivado(false);
            entidade.setStatus("A");
            entidade.setModuloSistema("ARQUIVOS");
            entidade.setUri("/arquivos/" + nomeUnico);

            arquivoRepository.save(entidade);
            return entidade.getId();

        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar o arquivo no disco.");
        } catch (Exception e) {
            throw new RuntimeException("Erro na criptografia do arquivo.");
        }
    }

    public List<ArquivoDTO> listar(Boolean somenteNaoExcluidos) {
        List<Arquivo> lista;

        if (Boolean.TRUE.equals(somenteNaoExcluidos)) {
            lista = arquivoRepository.findByExcluidoFalse();
        } else {
            lista = arquivoRepository.findAll();
        }

        return lista.stream()
                .map(ArquivoDTO::new)
                .collect(Collectors.toList());
    }

    public void deletar(Long id) {
        Arquivo arquivo = arquivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arquivo não encontrado com ID: " + id));
        try {
            Path caminhoArquivo = diretorioArquivos.resolve(arquivo.getCaminhoOriginal());
            Files.deleteIfExists(caminhoArquivo);
            arquivo.setExcluido(true);
            arquivoRepository.save(arquivo);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao deletar arquivo físico");
        }
    }

    private String getExtensao(String nomeArquivo) {
        int index = nomeArquivo.lastIndexOf('.');
        return index > 0 ? nomeArquivo.substring(index + 1): "";
    }

    public byte[] getArquivoDescriptografado(Long id) {
        Arquivo arquivo = arquivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arquivo não encontrado com ID: " + id));
        try {
            Path caminhoArquivo = diretorioArquivos.resolve(arquivo.getCaminhoOriginal());
            byte[] dadosCriptografados = Files.readAllBytes(caminhoArquivo);
            return CriptografiaUtil.processarAES(dadosCriptografados,Cipher.DECRYPT_MODE);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao ler e descriptografar o arquivo");
        }
    }

    public Arquivo buscarEntidade(Long id) {
        return arquivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arquivo não encontrado com ID: " + id));
    }


}


