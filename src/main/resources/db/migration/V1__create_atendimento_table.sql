-- Criação do schema
CREATE SCHEMA IF NOT EXISTS atendimento;

-- Garante que tudo será criado dentro do schema medisync
SET search_path TO atendimento;

-- Criação da extensão para UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Criação da tabela de fila
CREATE TABLE fila (
    id UUID PRIMARY KEY,
    idUnidadeSaude UUID NOT NULL,
    nomeFila VARCHAR(255) NOT NULL,
    tipoFila VARCHAR(255) NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW(),
    data_ultima_alteracao TIMESTAMP
);

-- Criação da tabela de itens do pedido
CREATE TABLE atendimento (
    id UUID PRIMARY KEY,
    idPaciente UUID NOT NULL,
    idFila UUID NOT NULL,
    posicaoFila INTEGER NOT NULL,
    idAnamnese UUID NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW(),
    data_ultima_alteracao TIMESTAMP,

    CONSTRAINT fk_fila
        FOREIGN KEY (idFila)
        REFERENCES fila (id)
);

-- Indexes para performance
CREATE INDEX idx_fila ON fila (id);
CREATE INDEX idx_atendimento ON atendimento (id);
