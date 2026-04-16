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

-- 4. ESTOQUE (Composição)

INSERT INTO estoque (quantidade, data_atualizacao) VALUES (15, CURRENT_TIMESTAMP);
INSERT INTO estoque (quantidade, data_atualizacao) VALUES (8, CURRENT_TIMESTAMP);


-- 5. PRODUTOS E TECLADOS (Herança)

-- Teclado 1: Redragon
INSERT INTO produto (nome, preco) VALUES ('Redragon Kumara', 250.00);
INSERT INTO teclado (id, modelo, idioma, comFio, iluminacaoRgb, id_marca, id_switch, id_keycap, id_estoque) 
VALUES (1, 'K552', 'PT-BR', true, true, 1, 1, 1, 1);

-- Teclado 2: HyperX
INSERT INTO produto (nome, preco) VALUES ('HyperX Alloy Origins', 450.00);
INSERT INTO teclado (id, modelo, idioma, comFio, iluminacaoRgb, id_marca, id_switch, id_keycap, id_estoque) 
VALUES (2, 'Alloy Core', 'US', true, true, 2, 2, 1, 2);

-- Tabela intermediária de Categorias (Muitos para Muitos)
INSERT INTO teclado_categoria (id_teclado, id_categoria) VALUES (1, 1);
INSERT INTO teclado_categoria (id_teclado, id_categoria) VALUES (2, 1);