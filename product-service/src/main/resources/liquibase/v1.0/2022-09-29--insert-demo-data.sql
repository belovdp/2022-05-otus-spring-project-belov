--liquibase formatted sql

--changeset BelovDP:testDemoData context:demo
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (1, false, false, true, 0, 'Категория 1', null);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (2, false, false, true, 0, 'Категория 2', null);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (3, false, false, true, 0, 'Категория 3', null);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (4, false, false, true, 0, 'Категория 4', null);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (5, false, false, false, 0, 'Категория 1.1', 1);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (6, false, false, true, 0, 'Категория 1.2', 1);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (7, false, false, true, 0, 'Категория 1.3', 1);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (8, false, false, true, 0, 'Категория 1.1.1', 5);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (9, false, false, true, 0, 'Категория 1.1.2', 5);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (10, false, false, true, 0, 'Категория 1.2.1', 6);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (11, false, false, true, 0, 'Категория 1.2.2', 6);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (12, false, false, true, 0, 'Категория 2.1', 2);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (13, false, false, true, 0, 'Категория 2.2', 2);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (14, false, false, true, 0, 'Категория 2.3', 2);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (15, false, false, true, 0, 'Категория 2.1.1', 12);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (16, false, false, true, 0, 'Категория 2.1.2', 12);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (17, false, false, true, 0, 'Категория 2.1.3', 12);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (18, false, false, true, 0, 'Категория 4.1', 4);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (19, false, false, true, 0, 'Категория 4.2', 4);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (20, false, false, true, 0, 'Категория 4.3', 4);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (21, false, false, true, 0, 'Категория 4.4', 4);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (22, false, false, true, 0, 'Категория 4.1.1', 18);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (23, false, false, true, 0, 'Категория 4.1.2', 18);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (24, false, false, true, 0, 'Категория 4.1.2.1', 23);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (25, false, false, true, 0, 'Категория 4.1.2.2', 23);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (26, false, false, true, 0, 'Категория 4.1.2.3', 23);
INSERT INTO categories (id, deleted, hide, published, sort_index, title, parent_id)
VALUES (27, false, false, true, 0, 'Категория 4.1.2.4', 23);
ALTER TABLE categories
    ALTER COLUMN id RESTART WITH 28;

INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (1, false, true, 0, 'Продукт 1');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (2, false, true, 0, 'Продукт 2');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (3, false, true, 0, 'Продукт 3');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (4, false, true, 0, 'Продукт 4');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (5, false, true, 0, 'Продукт 5');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (6, false, true, 0, 'Продукт 6');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (7, false, true, 0, 'Продукт 7');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (8, false, true, 0, 'Продукт 8');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (9, false, true, 0, 'Продукт 9');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (10, false, true, 0, 'Продукт 10');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (11, false, true, 0, 'Продукт 11');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (12, false, true, 0, 'Продукт 12');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (13, false, true, 0, 'Продукт 13');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (14, false, true, 0, 'Продукт 14');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (15, false, true, 0, 'Продукт 15');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (16, false, true, 0, 'Продукт 16');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (17, false, true, 0, 'Продукт 17');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (18, false, true, 0, 'Продукт 18');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (19, false, true, 0, 'Продукт 19');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (20, false, true, 0, 'Продукт 20');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (21, false, true, 0, 'Продукт 21');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (22, false, true, 0, 'Продукт 22');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (23, false, true, 0, 'Продукт 23');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (24, false, true, 0, 'Продукт 24');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (25, false, true, 0, 'Продукт 25');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (26, false, true, 0, 'Продукт 26');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (27, false, true, 0, 'Продукт 27');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (28, false, true, 0, 'Продукт 28');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (29, false, true, 0, 'Продукт 29');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (30, false, true, 0, 'Продукт 30');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (31, false, true, 0, 'Продукт 31');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (32, false, true, 0, 'Продукт 32');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (33, false, true, 0, 'Продукт 33');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (34, false, true, 0, 'Продукт 34');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (35, false, true, 0, 'Продукт 35');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (36, false, true, 0, 'Продукт 36');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (37, false, true, 0, 'Продукт 37');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (38, false, true, 0, 'Продукт 38');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (39, false, true, 0, 'Продукт 39');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (40, false, true, 0, 'Продукт 40');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (41, false, true, 0, 'Продукт 41');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (42, false, true, 0, 'Продукт 42');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (43, false, true, 0, 'Продукт 43');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (44, false, true, 0, 'Продукт 44');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (45, false, true, 0, 'Продукт 45');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (46, false, true, 0, 'Продукт 46');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (47, false, true, 0, 'Продукт 47');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (48, false, true, 0, 'Продукт 48');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (49, false, true, 0, 'Продукт 49');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (50, false, true, 0, 'Продукт 50');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (51, false, true, 0, 'Продукт 51');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (52, false, true, 0, 'Продукт 52');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (53, false, true, 0, 'Продукт 53');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (54, false, true, 0, 'Продукт 54');
INSERT INTO products (id, deleted, published, sort_index, title)
VALUES (55, false, true, 0, 'Продукт 55');
ALTER TABLE products
    ALTER COLUMN id RESTART WITH 57;

INSERT INTO products_categories (category_id, product_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 4),
       (2, 5),
       (2, 6),
       (3, 7),
       (3, 8),
       (4, 9),
       (4, 10),
       (5, 11),
       (5, 12),
       (6, 13),
       (7, 14),
       (7, 15),
       (8, 16),
       (9, 17),
       (10, 18),
       (10, 19),
       (11, 20),
       (12, 21),
       (12, 22),
       (13, 23),
       (13, 25),
       (13, 26),
       (14, 27),
       (14, 28),
       (15, 29),
       (16, 30),
       (17, 31),
       (17, 32),
       (18, 33),
       (19, 34),
       (19, 35),
       (20, 36),
       (21, 37),
       (22, 38),
       (23, 39),
       (24, 40),
       (25, 41),
       (26, 42),
       (27, 43),
       (27, 44),
       (27, 45),
       (27, 46),
       (27, 47),
       (27, 48),
       (27, 49),
       (27, 50),
       (27, 51),
       (27, 52),
       (27, 53),
       (27, 54),
       (27, 55);

