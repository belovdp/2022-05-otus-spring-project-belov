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

INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (3, false, true, 0, 'Продукт 1', 1);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (4, false, true, 0, 'Продукт 2', 1);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (5, false, true, 0, 'Продукт 3', 1);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (6, false, true, 0, 'Продукт 4', 2);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (7, false, true, 0, 'Продукт 5', 2);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (8, false, true, 0, 'Продукт 6', 2);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (9, false, true, 0, 'Продукт 7', 3);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (10, false, true, 0, 'Продукт 8', 3);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (11, false, true, 0, 'Продукт 9', 3);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (12, false, true, 0, 'Продукт 10', 4);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (13, false, true, 0, 'Продукт 11', 4);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (14, false, true, 0, 'Продукт 12', 4);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (15, false, true, 0, 'Продукт 13', 5);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (16, false, true, 0, 'Продукт 14', 5);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (17, false, true, 0, 'Продукт 15', 5);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (18, false, true, 0, 'Продукт 16', 6);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (19, false, true, 0, 'Продукт 17', 6);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (20, false, true, 0, 'Продукт 18', 6);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (21, false, true, 0, 'Продукт 19', 7);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (22, false, true, 0, 'Продукт 20', 7);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (23, false, true, 0, 'Продукт 21', 7);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (24, false, true, 0, 'Продукт 22', 8);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (25, false, true, 0, 'Продукт 23', 8);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (26, false, true, 0, 'Продукт 24', 8);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (27, false, true, 0, 'Продукт 25', 9);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (28, false, true, 0, 'Продукт 26', 9);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (29, false, true, 0, 'Продукт 27', 9);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (30, false, true, 0, 'Продукт 28', 10);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (31, false, true, 0, 'Продукт 30', 10);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (32, false, true, 0, 'Продукт 31', 10);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (33, false, true, 0, 'Продукт 32', 11);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (34, false, true, 0, 'Продукт 33', 11);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (35, false, true, 0, 'Продукт 34', 11);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (36, false, true, 0, 'Продукт 35', 12);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (37, false, true, 0, 'Продукт 36', 12);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (38, false, true, 0, 'Продукт 37', 12);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (39, false, true, 0, 'Продукт 38', 13);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (40, false, true, 0, 'Продукт 39', 13);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (41, false, true, 0, 'Продукт 40', 13);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (42, false, true, 0, 'Продукт 41', 14);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (43, false, true, 0, 'Продукт 42', 14);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (44, false, true, 0, 'Продукт 43', 15);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (45, false, true, 0, 'Продукт 44', 15);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (46, false, true, 0, 'Продукт 45', 16);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (47, false, true, 0, 'Продукт 46', 16);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (48, false, true, 0, 'Продукт 47', 17);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (49, false, true, 0, 'Продукт 48', 17);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (50, false, true, 0, 'Продукт 49', 18);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (51, false, true, 0, 'Продукт 50', 18);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (52, false, true, 0, 'Продукт 51', 26);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (53, false, true, 0, 'Продукт 52', 26);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (54, false, true, 0, 'Продукт 53', 26);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (55, false, true, 0, 'Продукт 54', 27);
INSERT INTO products (id, deleted, published, sort_index, title, category_id)
VALUES (56, false, true, 0, 'Продукт 55', 25);

