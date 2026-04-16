-- 1. ESTADOS E MUNICÍPIOS
INSERT INTO estado (id, sigla, nome, codigo_regiao) VALUES (1, 'TO', 'Tocantins', 3);
INSERT INTO estado (id, sigla, nome, codigo_regiao) VALUES (2, 'GO', 'Goiás', 1);

INSERT INTO municipio (id, nome, id_estado) VALUES (1, 'Palmas', 1);
INSERT INTO municipio (id, nome, id_estado) VALUES (2, 'Porto Nacional', 1);

-- 2. MARCAS E CATEGORIAS
INSERT INTO marca (id, nome, descricao) VALUES (1, 'Redragon', 'Marca chinesa gamer');
INSERT INTO marca (id, nome, descricao) VALUES (2, 'HyperX', 'Marca americana gamer');

INSERT INTO categoria (id, nome, descricao) VALUES (1, 'Gamer', 'Foco em performance');
INSERT INTO categoria (id, nome, descricao) VALUES (2, 'Sem Fio', 'Conexão via Bluetooth');

-- 3. SWITCHES E KEYCAPS (Corrigido para forcaAtuacao sem underline)
INSERT INTO switch (id, nome, fabricante, id_tipo_switch, forcaAtuacao) VALUES (1, 'Outemu Blue', 'Outemu', 1, 60.0);
INSERT INTO switch (id, nome, fabricante, id_tipo_switch, forcaAtuacao) VALUES (2, 'Cherry MX Red', 'Cherry GmbH', 2, 45.0);

INSERT INTO keycap (id, nome, material, id_perfil, cor) VALUES (1, 'HyperX Pudding', 'PBT', 2, 'Preto');

-- 4. ESTOQUE (Composição: Cria o estoque antes)
INSERT INTO estoque (id, quantidade, data_atualizacao) VALUES (1, 15, CURRENT_TIMESTAMP);
INSERT INTO estoque (id, quantidade, data_atualizacao) VALUES (2, 8, CURRENT_TIMESTAMP);

-- 5. PRODUTOS E TECLADOS (Herança: Inserindo no Produto e depois no Teclado)
-- Teclado 1: Redragon
INSERT INTO produto (id, nome, preco) VALUES (1, 'Redragon Kumara', 250.00);
INSERT INTO teclado (id, modelo, idioma, comFio, iluminacaoRgb, id_marca, id_estoque) VALUES (1, 'K552', 'PT-BR', true, true, 1, 1);

-- Teclado 2: HyperX
INSERT INTO produto (id, nome, preco) VALUES (2, 'HyperX Alloy Origins', 450.00);
INSERT INTO teclado (id, modelo, idioma, comFio, iluminacaoRgb, id_marca, id_estoque) VALUES (2, 'Alloy Core', 'US', true, true, 2, 2);