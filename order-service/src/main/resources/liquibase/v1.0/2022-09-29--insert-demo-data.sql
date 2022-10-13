--liquibase formatted sql

--changeset BelovDP:testDemoData context:demo
INSERT INTO orders (id, created, email, phone, note, address, user_id, username)
VALUES (1, '2022-10-10 23:00:00', 'test1@email.ru', '+7(991) 999-99-99', 'Заметка 1', 'г Москва' , '3c9dd655-85ba-4760-bd89-a676e16264ae', 'Вася');
INSERT INTO orders (id, created, email, phone, note, address, user_id, username)
VALUES (2, '2022-05-10 23:00:00', 'test2@email.ru', '+7(992) 999-99-99', 'Заметка 2', 'г Москва', '132eb191-2e7b-4ea2-8871-e3f295c4c63e', 'Владимир');
INSERT INTO orders (id, created, email, phone, note, address, user_id, username)
VALUES (3, '2022-06-10 23:00:00', 'test3@email.ru', '+7(993) 999-99-99', 'Заметка 3', 'г Москва', '36c7648d-1e1f-4dfb-9242-93eaf831c870', 'Инокентий');
INSERT INTO orders (id, created, email, phone, note, address, user_id, username)
VALUES (4, '2022-09-10 23:00:00', 'test4@email.ru', '+7(994) 999-99-99', 'Заметка 4', 'г Москва', '36c7648d-1e1f-4dfb-9242-93eaf831c870', 'Инокентий');
INSERT INTO orders (id, created, email, phone, note, address, user_id, username)
VALUES (5, '2022-09-10 23:00:00', 'test5@email.ru', '+7(995) 999-99-99', 'Заметка 5', 'г Москва', '36c7648d-1e1f-4dfb-9242-93eaf831c870', 'Инокентий');
ALTER TABLE orders
    ALTER COLUMN id RESTART WITH 6;

INSERT INTO order_items (order_id, product_id, title, price, count)
VALUES (1, 33, 'Продукт 33', 4999, 1);
INSERT INTO order_items (order_id, product_id, title, price, count)
VALUES (1, 35, 'Продукт 35', 3999, 10);
INSERT INTO order_items (order_id, product_id, title, price, count)
VALUES (2, 12, 'Продукт 12', 100, 2);
INSERT INTO order_items (order_id, product_id, title, price, count)
VALUES (2, 13, 'Продукт 13', 300, 3);
INSERT INTO order_items (order_id, product_id, title, price, count)
VALUES (3, 16, 'Продукт 16', 300, 1);
INSERT INTO order_items (order_id, product_id, title, price, count)
VALUES (3, 17, 'Продукт 17', 400, 1);
INSERT INTO order_items (order_id, product_id, title, price, count)
VALUES (4, 1, 'Продукт 1', 1300, 1);
INSERT INTO order_items (order_id, product_id, title, price, count)
VALUES (5, 5, 'Продукт 5', 1500, 1);