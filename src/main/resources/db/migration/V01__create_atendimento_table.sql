-- Criação do schema
CREATE SCHEMA IF NOT EXISTS atendimento;

-- Garante que tudo será criado dentro do schema medisync
SET search_path TO atendimento;

-- Criação da extensão para UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Criação da tabela de fila
CREATE TABLE fila (
    id UUID PRIMARY KEY,
    unidade_saude_id UUID NOT NULL,
    nome_fila VARCHAR(255) NOT NULL,
    tipo_fila VARCHAR(255) NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW(),
    data_ultima_alteracao TIMESTAMP
);

-- Criação da tabela de itens do pedido
CREATE TABLE atendimento (
    id UUID PRIMARY KEY,
    paciente_id UUID NOT NULL,
    fila_id UUID NOT NULL,
    posicao_fila INTEGER NOT NULL,
    anamnese_id UUID NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW(),
    data_ultima_alteracao TIMESTAMP,

    CONSTRAINT fk_fila
        FOREIGN KEY (fila_id)
        REFERENCES fila (id)
);

-- Indexes para performance
CREATE INDEX idx_fila ON fila (id);
CREATE INDEX idx_atendimento ON atendimento (id);
