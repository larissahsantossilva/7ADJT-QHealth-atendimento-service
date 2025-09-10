INSERT INTO atendimento.fila
(id, unidade_saude_id, nome_fila, tipo_fila, data_criacao, data_ultima_alteracao)
VALUES
    ('c1b2a3d4-e5f6-a7b8-c9d0-a1b2c3d4e5f6', 'f1e2d3c4-b5a6-f7e8-d9c0-b1a2c3d4e5f6', 'HOSPITAL 1', 'NORMAL', NOW(), NULL),
    ('d2c3b4e5-f6a7-b8c9-d0e1-b2c3d4e5f6a7', 'e2d3c4b5-a6f7-e8d9-c0b1-a2c3d4e5f6a7', 'HOSPITAL 1', 'PREFERENCIAL', NOW(), NULL),
    ('e3d4c5f6-a7b8-c9d0-e1f2-c3d4e5f6a7b8', 'd3c4b5a6-f7e8-d9c0-b1a2-c3d4e5f6a7b8', 'HOSPITAL 2', 'NORMAL', NOW(), NULL),
    ('f4e5d6a7-b8c9-d0e1-f2a3-d4e5f6a7b8c9', 'c4b5a6f7-e8d9-c0b1-a2c3-d4e5f6a7b8c9', 'HOSPITAL 2', 'PREFERENCIAL', NOW(), NULL);