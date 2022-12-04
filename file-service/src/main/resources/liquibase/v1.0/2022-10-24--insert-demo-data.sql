--liquibase formatted sql

--changeset BelovDP:testDemoData context:demo
insert into files_info (id, entity_category, content_type, entity_id)
values  ('4386618c-0026-4eba-b448-7bc10eb53df6', 'PRODUCT', 'image/jpeg', 1),
        ('be66ff77-21f3-454c-8040-e6af2dc35d91', 'PRODUCT', 'image/jpeg', 1),
        ('75f29a72-ee65-4aff-b3ab-09c6162d816a', 'PRODUCT', 'image/jpeg', 16),
        ('df3e3c11-798c-45a5-a539-2fcf1fedfc87', 'PRODUCT', 'image/jpeg', 22),
        ('a94904ab-a36f-4c84-b3ad-7a08cf65739b', 'PRODUCT', 'image/jpeg', 2),
        ('66b85e33-dcfd-4feb-9752-6c5d8f24c6f1', 'PRODUCT', 'image/jpeg', 3),
        ('20a5ef23-e19b-4788-a250-1233d50ebfe0', 'PRODUCT', 'image/jpeg', 4),
        ('1551a3e4-bebd-4bec-ab1d-c63df30092be', 'PRODUCT', 'image/jpeg', 5),
        ('a585533e-769a-4c8d-aa96-31ffe365539d', 'PRODUCT', 'image/jpeg', 6),
        ('62a79e1a-236e-43da-8fc8-7d2c6ed0c200', 'PRODUCT', 'image/jpeg', 7),
        ('5d74a7b4-541e-4953-998e-408afd85e18a', 'PRODUCT', 'image/jpeg', 10),
        ('85c305ec-e026-4c4e-8e24-47843ca0ad61', 'PRODUCT', 'image/jpeg', 14),
        ('22538886-8af8-4b57-94bb-507d67a2f74c', 'PRODUCT', 'image/jpeg', 18),
        ('109fd903-44e5-44b3-b661-c18caf542fe5', 'PRODUCT', 'image/jpeg', 26),
        ('82ea8900-f6cb-45f7-a1bb-b53433acf120', 'PRODUCT', 'image/jpeg', 32),
        ('b65577ad-96eb-46ce-ac8e-e1df440d48fa', 'PRODUCT', 'image/jpeg', 36),
        ('b26cb331-a2a7-4eba-9a3e-cc33ce27213f', 'PRODUCT', 'image/jpeg', 40),
        ('5d9a746d-4e6c-4038-846e-2acf95835957', 'PRODUCT', 'image/jpeg', 44),
        ('69a4b269-724b-4057-89da-ea1cda12f78b', 'PRODUCT', 'image/jpeg', 48),
        ('cf628e7d-e72c-4773-a59a-105485a0745c', 'PRODUCT', 'image/jpeg', 52);