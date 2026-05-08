-- 1. ESTADOS E MUNICÍPIOS
INSERT INTO estado (sigla, nome, codigo_regiao) VALUES ('TO', 'Tocantins', 3);
INSERT INTO estado (sigla, nome, codigo_regiao) VALUES ('GO', 'Goiás', 1);

INSERT INTO municipio (nome, id_estado) VALUES ('Palmas', 1);
INSERT INTO municipio (nome, id_estado) VALUES ('Porto Nacional', 1);

-- 2. MARCAS E CATEGORIAS
INSERT INTO marca (nome, descricao) VALUES ('Redragon', 'Marca chinesa gamer');
INSERT INTO marca (nome, descricao) VALUES ('HyperX', 'Marca americana gamer');
INSERT INTO marca (nome, descricao) VALUES ('Logitech', 'Marca suíça gamer');

INSERT INTO categoria (nome, descricao) VALUES ('Gamer', 'Foco em performance');
INSERT INTO categoria (nome, descricao) VALUES ('Sem Fio', 'Conexão via Bluetooth');

-- 3. SWITCHES E KEYCAPS
INSERT INTO switch (nome, fabricante, id_tipo_switch, forcaAtuacao) VALUES ('Outemu Blue', 'Outemu', 1, 60.0);
INSERT INTO switch (nome, fabricante, id_tipo_switch, forcaAtuacao) VALUES ('Cherry MX Red', 'Cherry GmbH', 2, 45.0);

INSERT INTO keycap (nome, material, id_perfil, cor) VALUES ('HyperX Pudding', 'PBT', 2, 'Preto');

-- 4. ESTOQUE
INSERT INTO estoque (quantidade, data_atualizacao) VALUES (15, CURRENT_TIMESTAMP);
INSERT INTO estoque (quantidade, data_atualizacao) VALUES (8, CURRENT_TIMESTAMP);

-- 5. PRODUTOS E TECLADOS (Herança JOINED)
-- Teclado 1: Redragon
INSERT INTO produto (nome, preco) VALUES ('Redragon Kumara', 250.00);
INSERT INTO teclado (id, modelo, idioma, comFio, iluminacaoRgb, id_marca, id_switch, id_keycap, id_estoque) 
VALUES (1, 'K552', 'PT-BR', true, true, 1, 1, 1, 1);

-- Teclado 2: HyperX
INSERT INTO produto (nome, preco) VALUES ('HyperX Alloy Origins', 450.00);
INSERT INTO teclado (id, modelo, idioma, comFio, iluminacaoRgb, id_marca, id_switch, id_keycap, id_estoque) 
VALUES (2, 'Alloy Core', 'US', true, true, 2, 2, 1, 2);

-- Tabela intermediária de Categorias (Many-to-Many)
INSERT INTO teclado_categoria (id_teclado, id_categoria) VALUES (1, 1);
INSERT INTO teclado_categoria (id_teclado, id_categoria) VALUES (2, 1);

-- 6. USUÁRIOS (Senha: 123456)
-- Utilizando o Hash BCrypt que você gerou
INSERT INTO usuario (nome, login, senha_hash, perfil, dataCadastro) 
VALUES ('Administrador', 'admin', '$2a$10$DyM4Suxa8FSzEPiNujfKNuhobl/1tV/aGpPb9yz4sqPSqsmm1Tby2', 'ADMIN', CURRENT_TIMESTAMP);

INSERT INTO usuario (nome, login, senha_hash, perfil, dataCadastro) 
VALUES ('João Cliente', 'joao', '$2a$10$DyM4Suxa8FSzEPiNujfKNuhobl/1tV/aGpPb9yz4sqPSqsmm1Tby2', 'USER', CURRENT_TIMESTAMP);

-- 7. ATUALIZAÇÃO DAS SEQUENCES (Essencial para não dar erro de ID duplicado)
ALTER SEQUENCE estado_id_seq RESTART WITH 3;
ALTER SEQUENCE municipio_id_seq RESTART WITH 3;
ALTER SEQUENCE marca_id_seq RESTART WITH 4;
ALTER SEQUENCE categoria_id_seq RESTART WITH 3;
ALTER SEQUENCE switch_id_seq RESTART WITH 3;
ALTER SEQUENCE keycap_id_seq RESTART WITH 2;
ALTER SEQUENCE estoque_id_seq RESTART WITH 3;
ALTER SEQUENCE produto_id_seq RESTART WITH 3;
ALTER SEQUENCE usuario_id_seq RESTART WITH 3;