-- ESTADOS
INSERT INTO estado (sigla, nome, codigo_regiao) VALUES ('TO', 'Tocantins', 3);
INSERT INTO estado (sigla, nome, codigo_regiao) VALUES ('GO', 'Goiás', 1);
INSERT INTO estado (sigla, nome, codigo_regiao) VALUES ('SP', 'São Paulo', 4);
INSERT INTO estado (sigla, nome, codigo_regiao) VALUES ('RJ', 'Rio de Janeiro', 4);

-- MUNICIPIOS
INSERT INTO municipio (nome, id_estado) VALUES ('Palmas', 1);
INSERT INTO municipio (nome, id_estado) VALUES ('Porto Nacional', 1);
INSERT INTO municipio (nome, id_estado) VALUES ('Goiânia', 2);
INSERT INTO municipio (nome, id_estado) VALUES ('Aparecida de Goiânia', 2);
INSERT INTO municipio (nome, id_estado) VALUES ('São Paulo', 3);
INSERT INTO municipio (nome, id_estado) VALUES ('Guarulhos', 3);
INSERT INTO municipio (nome, id_estado) VALUES ('Rio de Janeiro', 4);
INSERT INTO municipio (nome, id_estado) VALUES ('Niterói', 4);

-- MARCAS
INSERT INTO marca (nome, descricao) VALUES ('Redragon', 'Marca chinesa de periféricos gamer');
INSERT INTO marca (nome, descricao) VALUES ('HyperX', 'Marca americana de periféricos gamer');
INSERT INTO marca (nome, descricao) VALUES ('Logitech', 'Marca suíça de periféricos gamer');
INSERT INTO marca (nome, descricao) VALUES ('Corsair', 'Marca americana de periféricos gamer');
INSERT INTO marca (nome, descricao) VALUES ('Anne', 'Marca chinesa de periféricos gamer');

-- CATEGORIAS
INSERT INTO categoria (nome, descricao) VALUES ('Gamer', 'Equipamentos focados em baixo tempo de resposta e performance');
INSERT INTO categoria (nome, descricao) VALUES ('Produtividade', 'Teclados focados em ergonomia e digitação silenciosa');
INSERT INTO categoria (nome, descricao) VALUES ('Custom', 'Teclados premium voltados para entusiastas da customização');

-- SWITCHES
-- (id_tipo_switch: 1=Blue/Clicky, 2=Red/Linear, 3=Brown/Tactile)
INSERT INTO switch (nome, fabricante, id_tipo_switch, forca_atuacao) VALUES ('Outemu Blue', 'Outemu', 1, 60.0);
INSERT INTO switch (nome, fabricante, id_tipo_switch, forca_atuacao) VALUES ('Cherry MX Red', 'Cherry GmbH', 2, 45.0);
INSERT INTO switch (nome, fabricante, id_tipo_switch, forca_atuacao) VALUES ('Gateron Brown', 'Gateron', 3, 55.0);

-- TECLADOS
INSERT INTO teclado (nome, id_tipo_switch, id_formato, id_marca, preco) VALUES ('Redragon Kumara K552', 2, 2, 1, 250.00);
INSERT INTO teclado (nome, id_tipo_switch, id_formato, id_marca, preco) VALUES ('HyperX Alloy Origins', 2, 2, 2, 450.00);
INSERT INTO teclado (nome, id_tipo_switch, id_formato, id_marca, preco) VALUES ('Logitech G Pro X', 3, 2, 3, 600.00);
INSERT INTO teclado (nome, id_tipo_switch, id_formato, id_marca, preco) VALUES ('Corsair K70 RGB', 1, 3, 4, 700.00);
INSERT INTO teclado (nome, id_tipo_switch, id_formato, id_marca, preco) VALUES ('Anne Pro 2', 1, 1, 5, 350.00);

-- KEYCAPS
-- (id_perfil: 1=Cherry, 2=OEM, 3=SA, 4=XDA)
INSERT INTO keycap (nome, material, id_perfil, cor) VALUES ('HyperX Pudding', 'PBT', 2, 'Preto/Translúcido');
INSERT INTO keycap (nome, material, id_perfil, cor) VALUES ('Akko Neon', 'PBT', 1, 'Rosa e Azul');
INSERT INTO keycap (nome, material, id_perfil, cor) VALUES ('Corsair Pro', 'ABS', 2, 'Preto');