INSERT INTO categoria (id, nome) VALUES (1, 'Bebidas Quentes');
INSERT INTO categoria (id, nome) VALUES (2, 'Doces');
INSERT INTO categoria (id, nome) VALUES (3, 'Salgados');

INSERT INTO produto (id, nome, descricao, preco, categoria_id) VALUES (1, 'Café Expresso', 'Café forte em dose pequena', 5.50, 1);
INSERT INTO produto (id, nome, descricao, preco, categoria_id) VALUES (2, 'Cappuccino', 'Café com leite vaporizado e espuma', 7.00, 1);

INSERT INTO cliente (id, nome, email) VALUES (1, 'João da Silva', 'joao@email.com');
INSERT INTO cliente (id, nome, email) VALUES (2, 'Maria Souza', 'maria@email.com');