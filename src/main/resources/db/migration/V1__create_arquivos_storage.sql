CREATE TABLE arquivos_storage (
    id SERIAL PRIMARY KEY,
    caminho_original VARCHAR(60),
    caminho_storage VARCHAR(60),
    nome_original VARCHAR(255),
    extensao VARCHAR(6),
    tamanho BIGINT,
    modulo_sistema VARCHAR(24),
    data_inclusao TIMESTAMP,
    fk_id_pessoa BIGINT,
    status CHAR(1),
    descricao VARCHAR(200),
    privado BOOLEAN,
    uri VARCHAR(200)
);