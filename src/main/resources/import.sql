-- 1. ESTADOS E MUNICÍPIOS (Representando as 5 regiões do Brasil)
-- Região Norte (Frete Grátis)
INSERT INTO estado (sigla, nome, codigo_regiao) VALUES ('TO', 'Tocantins', 1);
INSERT INTO municipio (nome, id_estado) VALUES ('Palmas', 1);

-- Região Centro-Oeste (Frete Grátis)
INSERT INTO estado (sigla, nome, codigo_regiao) VALUES ('GO', 'Goiás', 5);
INSERT INTO municipio (nome, id_estado) VALUES ('Goiânia', 2);

-- Região Sudeste (Frete R$ 45,00)
INSERT INTO estado (sigla, nome, codigo_regiao) VALUES ('SP', 'São Paulo', 3);
INSERT INTO municipio (nome, id_estado) VALUES ('São Paulo', 3);

-- Região Sul (Frete R$ 45,00)
INSERT INTO estado (sigla, nome, codigo_regiao) VALUES ('RS', 'Rio Grande do Sul', 4);
INSERT INTO municipio (nome, id_estado) VALUES ('Porto Alegre', 4);

-- Região Nordeste (Frete R$ 45,00)
INSERT INTO estado (sigla, nome, codigo_regiao) VALUES ('BA', 'Bahia', 2);
INSERT INTO municipio (nome, id_estado) VALUES ('Salvador', 5);

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
-- Endereço 1: Tocantins (Frete Grátis)
INSERT INTO endereco (logradouro, numero, bairro, cep, complemento, ativo, principal, id_municipio, id_usuario) 
VALUES ('Avenida NS 5', 'Lote 10', 'Plano Diretor Sul', '77000-000', 'Apto 101', true, true, 1, 2);

-- Endereço 2: São Paulo (Frete Pago - R$ 45,00) - Usado para testar rotas interestaduais
INSERT INTO endereco (logradouro, numero, bairro, cep, complemento, ativo, principal, id_municipio, id_usuario) 
VALUES ('Avenida Paulista', '1000', 'Bela Vista', '01311-000', 'Bloco B', true, false, 3, 2);

INSERT INTO cartaocredito (numeroCartao, nomeTitular, cpfTitular, bandeira, ativo, id_usuario) 
VALUES ('1234567890123456', 'JOAO CLIENTE', '11122233344', 'MASTERCARD', true, 2);

-- 8. CUPONS (Um ativo e um expirado para testes)
INSERT INTO cupom (codigo, percentualDesconto, ativo) VALUES ('NOTA10', 15.0, true);
INSERT INTO cupom (codigo, percentualDesconto, ativo) VALUES ('EXPIRADO', 50.0, false);

-- 9. PEDIDO HISTÓRICO (Com a nova coluna valorFrete)

-- Pedido 1: AGUARDANDO_PAGAMENTO (Palmas - TO: Frete 0.0)
INSERT INTO pedido (dataHora, valorTotal, valorFrete, valorDesconto, formaPagamento, status, id_endereco, id_cartao, id_cupom, id_usuario) 
VALUES (CURRENT_TIMESTAMP, 250.00, 0.0, 0.0, 2, 'AGUARDANDO_PAGAMENTO', 1, 1, null, 2); 
INSERT INTO itempedido (quantidade, preco, id_pedido, id_teclado) VALUES (1, 250.00, 1, 1);

-- Pedido 2: PAGO (Palmas - TO: Frete 0.0)
INSERT INTO pedido (dataHora, valorTotal, valorFrete, valorDesconto, formaPagamento, status, id_endereco, id_cartao, id_cupom, id_usuario) 
VALUES (CURRENT_TIMESTAMP, 450.00, 0.0, 0.0, 2, 'PAGO', 1, 1, null, 2); 
INSERT INTO itempedido (quantidade, preco, id_pedido, id_teclado) VALUES (1, 450.00, 2, 2);

-- Pedido 3: ENVIADO (Palmas - TO: Frete 0.0)
INSERT INTO pedido (dataHora, valorTotal, valorFrete, valorDesconto, formaPagamento, status, id_endereco, id_cartao, id_cupom, id_usuario) 
VALUES (CURRENT_TIMESTAMP, 250.00, 0.0, 0.0, 2, 'ENVIADO', 1, 1, null, 2); 
INSERT INTO itempedido (quantidade, preco, id_pedido, id_teclado) VALUES (1, 250.00, 3, 1);

-- Pedido 4: ENTREGUE (Palmas - TO: Frete 0.0)
INSERT INTO pedido (dataHora, valorTotal, valorFrete, valorDesconto, formaPagamento, status, id_endereco, id_cartao, id_cupom, id_usuario) 
VALUES (CURRENT_TIMESTAMP, 700.00, 0.0, 0.0, 2, 'ENTREGUE', 1, 1, null, 2); 
INSERT INTO itempedido (quantidade, preco, id_pedido, id_teclado) VALUES (1, 250.00, 4, 1);
INSERT INTO itempedido (quantidade, preco, id_pedido, id_teclado) VALUES (1, 450.00, 4, 2);

-- Pedido 5: CANCELADO (São Paulo - SP: Frete 45.0 aplicado -> 250 do teclado + 45 do frete = 295.00)
INSERT INTO pedido (dataHora, valorTotal, valorFrete, valorDesconto, formaPagamento, status, id_endereco, id_cartao, id_cupom, id_usuario) 
VALUES (CURRENT_TIMESTAMP, 295.00, 45.0, 0.0, 2, 'CANCELADO', 2, 1, null, 2); 
INSERT INTO itempedido (quantidade, preco, id_pedido, id_teclado) VALUES (1, 250.00, 5, 1);

-- 10. LISTA DE DESEJOS (Many-to-Many)
INSERT INTO usuario_lista_desejos (id_usuario, id_teclado) VALUES (2, 2);

-- 11. ATUALIZAÇÃO DAS SEQUENCES (Recalculadas para os novos registros)
ALTER SEQUENCE estado_id_seq RESTART WITH 6;
ALTER SEQUENCE municipio_id_seq RESTART WITH 6;
ALTER SEQUENCE marca_id_seq RESTART WITH 4;
ALTER SEQUENCE categoria_id_seq RESTART WITH 3;
ALTER SEQUENCE switch_id_seq RESTART WITH 3;
ALTER SEQUENCE keycap_id_seq RESTART WITH 2;
ALTER SEQUENCE estoque_id_seq RESTART WITH 4;
ALTER SEQUENCE produto_id_seq RESTART WITH 4;
ALTER SEQUENCE usuario_id_seq RESTART WITH 3;
ALTER SEQUENCE endereco_id_seq RESTART WITH 3;
ALTER SEQUENCE cartaocredito_id_seq RESTART WITH 2;
ALTER SEQUENCE cupom_id_seq RESTART WITH 3;
ALTER SEQUENCE pedido_id_seq RESTART WITH 6;
ALTER SEQUENCE itempedido_id_seq RESTART WITH 7;