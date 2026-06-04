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

-- 4. ESTOQUE (INCLUINDO O PRODUTO ESGOTADO PARA TESTE DA VITRINE)
INSERT INTO estoque (quantidade, data_atualizacao) VALUES (15, CURRENT_TIMESTAMP);
INSERT INTO estoque (quantidade, data_atualizacao) VALUES (8, CURRENT_TIMESTAMP);
INSERT INTO estoque (quantidade, data_atualizacao) VALUES (0, CURRENT_TIMESTAMP); -- Estoque zerado

-- 5. PRODUTOS E TECLADOS (Herança JOINED)
-- Teclado 1: Redragon
INSERT INTO produto (nome, preco) VALUES ('Redragon Kumara', 250.00);
INSERT INTO teclado (id, modelo, idioma, comFio, iluminacaoRgb, id_marca, id_switch, id_keycap, id_estoque) 
VALUES (1, 'K552', 'PT-BR', true, true, 1, 1, 1, 1);

-- Teclado 2: HyperX
INSERT INTO produto (nome, preco) VALUES ('HyperX Alloy Origins', 450.00);
INSERT INTO teclado (id, modelo, idioma, comFio, iluminacaoRgb, id_marca, id_switch, id_keycap, id_estoque) 
VALUES (2, 'Alloy Core', 'US', true, true, 2, 2, 1, 2);

-- Teclado 3: Logitech (O Esgotado)
INSERT INTO produto (nome, preco) VALUES ('Logitech G Pro X', 750.00);
INSERT INTO teclado (id, modelo, idioma, comFio, iluminacaoRgb, id_marca, id_switch, id_keycap, id_estoque) 
VALUES (3, 'G Pro X', 'US', false, true, 3, 2, 1, 3);

-- Tabela intermediária de Categorias (Many-to-Many)
INSERT INTO teclado_categoria (id_teclado, id_categoria) VALUES (1, 1);
INSERT INTO teclado_categoria (id_teclado, id_categoria) VALUES (2, 1);
INSERT INTO teclado_categoria (id_teclado, id_categoria) VALUES (3, 2);

-- 6. USUÁRIOS (Senha: 123456 - Utilizando o Hash BCrypt)
INSERT INTO usuario (nome, login, senha_hash, perfil, email, dataCadastro) 
VALUES ('Administrador', 'admin', '$2a$10$DyM4Suxa8FSzEPiNujfKNuhobl/1tV/aGpPb9yz4sqPSqsmm1Tby2', 'ADMIN', 'admin@email.com', CURRENT_TIMESTAMP);

INSERT INTO usuario (nome, login, senha_hash, perfil, email, dataCadastro) 
VALUES ('Joao Cliente', 'joao', '$2a$10$DyM4Suxa8FSzEPiNujfKNuhobl/1tV/aGpPb9yz4sqPSqsmm1Tby2', 'USER', 'joao@email.com', CURRENT_TIMESTAMP);

-- 7. ENDEREÇOS E CARTÕES DO CLIENTE (Vinculados ao Joao - ID 2)
INSERT INTO endereco (logradouro, numero, bairro, cep, complemento, ativo, principal, id_municipio, id_usuario) 
VALUES ('Avenida NS 5', 'Lote 10', 'Plano Diretor Sul', '77000-000', 'Apto 101', true, true, 1, 2);

INSERT INTO cartaocredito (numeroCartao, nomeTitular, cpfTitular, bandeira, ativo, id_usuario) 
VALUES ('1234567890123456', 'JOAO CLIENTE', '11122233344', 'MASTERCARD', true, 2);

-- 8. CUPONS (Um ativo e um expirado para testes)
INSERT INTO cupom (codigo, percentualDesconto, ativo) VALUES ('NOTA10', 15.0, true);
INSERT INTO cupom (codigo, percentualDesconto, ativo) VALUES ('EXPIRADO', 50.0, false);

-- 9. PEDIDO HISTÓRICO (Para testar a rota de listagem de pedidos do usuário)
-- Usando a Forma de Pagamento 3 (que costuma ser Cartão de Crédito no Enum)
INSERT INTO pedido (dataHora, valorTotal, valorDesconto, formaPagamento, id_endereco, id_cartao, id_cupom, id_usuario) 
VALUES (CURRENT_TIMESTAMP, 250.00, 0.0, 3, 1, 1, null, 2); 

INSERT INTO itempedido (quantidade, precoUnitario, id_pedido, id_teclado) 
VALUES (1, 250.00, 1, 1);

-- 10. LISTA DE DESEJOS (Many-to-Many)
INSERT INTO usuario_lista_desejos (id_usuario, id_teclado) VALUES (2, 2);

-- 11. ATUALIZAÇÃO DAS SEQUENCES (Essencial para o Quarkus não dar erro de ID duplicado no POST)
ALTER SEQUENCE estado_id_seq RESTART WITH 3;
ALTER SEQUENCE municipio_id_seq RESTART WITH 3;
ALTER SEQUENCE marca_id_seq RESTART WITH 4;
ALTER SEQUENCE categoria_id_seq RESTART WITH 3;
ALTER SEQUENCE switch_id_seq RESTART WITH 3;
ALTER SEQUENCE keycap_id_seq RESTART WITH 2;
ALTER SEQUENCE estoque_id_seq RESTART WITH 4;
ALTER SEQUENCE produto_id_seq RESTART WITH 4;
ALTER SEQUENCE usuario_id_seq RESTART WITH 3;
ALTER SEQUENCE endereco_id_seq RESTART WITH 2;
ALTER SEQUENCE cartaocredito_id_seq RESTART WITH 2;
ALTER SEQUENCE cupom_id_seq RESTART WITH 3;
ALTER SEQUENCE pedido_id_seq RESTART WITH 2;
ALTER SEQUENCE itempedido_id_seq RESTART WITH 2;