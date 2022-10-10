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

INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (1, false, true, 0, 'Продукт 1', 100);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (2, false, true, 0, 'Продукт 2', 200.50);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (3, false, true, 0, 'Продукт 3', 3000);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (4, false, true, 0, 'Продукт 4', 3500);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (5, false, true, 0, 'Продукт 5', 45000);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (6, false, true, 0, 'Продукт 6', 10000);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (7, false, true, 0, 'Продукт 7', 999);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (8, false, true, 0, 'Продукт 8', 999.99);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (9, false, true, 0, 'Продукт 9', 108);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (10, false, true, 0, 'Продукт 10', 700);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (11, false, true, 0, 'Продукт 11', 600);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (12, false, true, 0, 'Продукт 12', 2000);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (13, false, true, 0, 'Продукт 13', 3000);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (14, false, true, 0, 'Продукт 14', 5000);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (15, false, true, 0, 'Продукт 15', 15000);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (16, false, true, 0, 'Продукт 16', 10500);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (17, false, true, 0, 'Продукт 17', 2050);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (18, false, true, 0, 'Продукт 18', 2048);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (19, false, true, 0, 'Продукт 19', 1024);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (20, false, true, 0, 'Продукт 20', 128);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (21, false, true, 0, 'Продукт 21', 3000);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (22, false, true, 0, 'Продукт 22', 2500);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (23, false, true, 0, 'Продукт 23', 7000);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (24, false, true, 0, 'Продукт 24', 8000);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (25, false, true, 0, 'Продукт 25', 200);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (26, false, true, 0, 'Продукт 26', 3000);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (27, false, true, 0, 'Продукт 27', 5000);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (28, false, true, 0, 'Продукт 28', 1000);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (29, false, true, 0, 'Продукт 29', 3000);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (30, false, true, 0, 'Продукт 30', 5000);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (31, false, true, 0, 'Продукт 31', 6000);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (32, false, true, 0, 'Продукт 32', 7000);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (33, false, true, 0, 'Продукт 33', 1500);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (34, false, true, 0, 'Продукт 34', 2000);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (35, false, true, 0, 'Продукт 35', 244);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (36, false, true, 0, 'Продукт 36', 123);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (37, false, true, 0, 'Продукт 37', 321);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (38, false, true, 0, 'Продукт 38', 666);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (39, false, true, 0, 'Продукт 39', 404);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (40, false, true, 0, 'Продукт 40', 1999);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (41, false, true, 0, 'Продукт 41', 3000);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (42, false, true, 0, 'Продукт 42', 150);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (43, false, true, 0, 'Продукт 43', 50);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (44, false, true, 0, 'Продукт 44', 199);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (45, false, true, 0, 'Продукт 45', 77);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (46, false, true, 0, 'Продукт 46', 88);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (47, false, true, 0, 'Продукт 47', 2999);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (48, false, true, 0, 'Продукт 48', 299);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (49, false, true, 0, 'Продукт 49', 1000);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (50, false, true, 0, 'Продукт 50', 1050);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (51, false, true, 0, 'Продукт 51', 5000);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (52, false, true, 0, 'Продукт 52', 5500);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (53, false, true, 0, 'Продукт 53', 5700);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (54, false, true, 0, 'Продукт 54', 6000);
INSERT INTO products (id, deleted, published, sort_index, title, price)
VALUES (55, false, true, 0, 'Продукт 55', 9000);
ALTER TABLE products
    ALTER COLUMN id RESTART WITH 57;

insert into public.products_categories (category_id, product_id)
values  (1, 1),
        (1, 3),
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
        (27, 55),
        (7, 1),
        (10, 1),
        (6, 1),
        (9, 1),
        (11, 1),
        (8, 1),
        (5, 1),
        (8, 2),
        (5, 2),
        (1, 2),
        (2, 2),
        (12, 2),
        (16, 2),
        (8, 3),
        (10, 3),
        (5, 3),
        (11, 3),
        (7, 3),
        (6, 3),
        (9, 3),
        (6, 2),
        (15, 2),
        (7, 2),
        (9, 2),
        (13, 2),
        (14, 2),
        (17, 2),
        (10, 2),
        (11, 2),
        (20, 1),
        (18, 1),
        (26, 1),
        (24, 1),
        (19, 1),
        (22, 1),
        (25, 1),
        (27, 1),
        (21, 1),
        (4, 1),
        (23, 1),
        (9, 4),
        (5, 4),
        (1, 4),
        (8, 4),
        (5, 5),
        (1, 5),
        (8, 5),
        (9, 5),
        (18, 9),
        (19, 9),
        (23, 9),
        (25, 9),
        (22, 9),
        (20, 9),
        (21, 9),
        (27, 9),
        (26, 9),
        (24, 9),
        (1, 11),
        (8, 11),
        (8, 12),
        (1, 12),
        (1, 13),
        (10, 13),
        (6, 14),
        (10, 14),
        (1, 14),
        (1, 15),
        (2, 15),
        (17, 15),
        (12, 15),
        (16, 15),
        (15, 15),
        (5, 16),
        (1, 16),
        (5, 17),
        (1, 17),
        (1, 18),
        (18, 18),
        (22, 18),
        (4, 18),
        (6, 18),
        (4, 19),
        (24, 19),
        (1, 19),
        (6, 19),
        (18, 19),
        (23, 19),
        (27, 20),
        (4, 20),
        (23, 20),
        (1, 20),
        (18, 20),
        (6, 20),
        (23, 21),
        (26, 21),
        (18, 21),
        (4, 21),
        (9, 22),
        (10, 22),
        (7, 22),
        (2, 22),
        (8, 22),
        (11, 22),
        (6, 22),
        (1, 22),
        (5, 22),
        (16, 22),
        (14, 23),
        (2, 23),
        (26, 24),
        (4, 24),
        (18, 24),
        (23, 24),
        (27, 25),
        (23, 25),
        (2, 25),
        (18, 25),
        (4, 25),
        (2, 26),
        (23, 27),
        (2, 27),
        (4, 27),
        (24, 27),
        (18, 27),
        (26, 28),
        (2, 28),
        (18, 28),
        (23, 28),
        (24, 28),
        (27, 28),
        (25, 28),
        (4, 28),
        (12, 29),
        (2, 29),
        (2, 30),
        (12, 30),
        (2, 31),
        (12, 31),
        (2, 32),
        (12, 32),
        (4, 33),
        (26, 33),
        (24, 33),
        (27, 33),
        (23, 33),
        (25, 33),
        (4, 34),
        (4, 35),
        (22, 35),
        (18, 35),
        (4, 36),
        (4, 37),
        (18, 38),
        (4, 38),
        (18, 39),
        (25, 39),
        (27, 39),
        (4, 39),
        (24, 39),
        (26, 39),
        (4, 40),
        (18, 40),
        (23, 40),
        (23, 41),
        (4, 41),
        (18, 41),
        (23, 42),
        (18, 42),
        (4, 42),
        (4, 43),
        (23, 43),
        (18, 43),
        (18, 44),
        (23, 44),
        (4, 44),
        (4, 45),
        (23, 45),
        (18, 45),
        (23, 46),
        (4, 46),
        (18, 46),
        (23, 47),
        (4, 47),
        (18, 47),
        (18, 48),
        (4, 48),
        (23, 48),
        (18, 49),
        (4, 49),
        (23, 49),
        (18, 50),
        (23, 50),
        (4, 50),
        (18, 51),
        (4, 51),
        (23, 51),
        (4, 52),
        (23, 52),
        (18, 52),
        (23, 53),
        (4, 53),
        (18, 53),
        (4, 54),
        (23, 54),
        (18, 54),
        (23, 55),
        (4, 55),
        (18, 55);
