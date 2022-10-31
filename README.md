# 2022-05-otus-spring-belov
Проектная работа: Система продаж на микросервисной архитектуре

Белов Д.П.

## Как запускать
- собираем через maven
- запускаем docker-compose.yml
- Подождать минуты две-три при первом запуске. Realm для keycloak будет проливаться продолжительное время

---
### [Админка](http://localhost:8085/#/)
#### Пользователи и пароли
Администратор: admin admin\
Редактор: editor editor\
Менеджер без права редактирования: viewer viewer

---
#### [Keycloak админка](http://localhost:8080/auth/)
Креды: admin password

---
#### [Сервис пользователей swagger](http://localhost:8081/swagger-ui/index.html)
#### [Сервис продуктов swagger](http://localhost:8082/swagger-ui/index.html)
#### [Сервис заказов swagger](http://localhost:8087/swagger-ui/index.html)
#### [Сервис файлов swagger](http://localhost:8088/swagger-ui/index.html)
#### [Eureka](http://localhost:8084/)

---
## Приложение для продаж
#### Состоять будет из следующих сервисов:
1. [x] Сервис продуктов
2. [x] Сервис заказов
3. [x] Сервис работы с пользователями
4. [x] Сервис по работе с фалами
5. [x] Eureka
5. [x] Zuul
6. [x] Сервис отдающий SPA для админа
6. [ ] Сервис отдающий SPA для обычных смертых

Всё это в docker-compose\
Пользователи хранятся в keycloak 16.1.1\
Взаимодействие между сервисами осуществлятся через jwt token\

---
### Сервис работы с пользователями
Сервис для авторизации и манипуляции с пользователями. \
Все пользователи будут храниться в keycloak 16.1.1, но токен получать и регистрироваться будет через этот сервис.
Доступные роли:
- ADMIN - Роль для работы с админкой. Максимально доступные права
- EDITOR - Роль для работы с админкой. Всё кроме изменения пользователей
- VIEWER - Роль для работы с админкой. Всё только на просмотр
- USER - Роль для пользователей магазина

Возможности:
- Получить токен
- Refresh токена
- Регистрация
- Получить список пользователей (ADMIN, EDITOR, VIEWER)
- Изменить роль пользователей (ADMIN)

---
### Сервис продуктов
Сервис для управления категориями и продуктами. Имеет свою БД pg. Продукты будут простыми, как и категории.

Возможности:
- Получить дерево категорий
- Получить категорию
- Создать категорию (ADMIN, EDITOR)
- Изменить категорию (ADMIN, EDITOR)
- Получить список продуктов
- Получить продукт
- Добавить продукт (ADMIN, EDITOR)
- Изменить продукт (ADMIN, EDITOR)
- Другие фичи в виде корзины удалённых категорий/продуктов. Скрытия из меню, публикации

---
### Сервис заказов
Сервис для оформления заказов. Имеет свою БД pg.\
При оформлении заказа будет обращаться к сервису продуктов, что бы зафиксировать текущую цену и прочее.\
Возможно будет обращаться к сервису пользователей.

Возможности:
- Вывести все заказы (ADMIN, EDITOR, VIEWER)
- Вывести заказы авторизованного клиента (USER)
- Вывести заказы клиента нужного (ADMIN, EDITOR, VIEWER)
- Создать заказ (USER)

---
### Клиентское приложение на Vue
Два варианта: админка и для обычных пользователей

Возможности: все что описано в возможностях бэковых частей

---
### Сервис работы с файлами
Сервис для добавления картинок к продукту. Имеет свою БД pg.

Возможности:
- Сохранить картинку
- Получить картинку

---
### Вспомогательная информация:
#### Экспорт настроенной конфигурации keycloak
- Прописать volume: `"./keycloak/export.json:/tmp/export.json"`
- Запустить контейнер
- Подключиться к контейнеру
- Выполнить /opt/jboss/keycloak/bin/standalone.sh -Djboss.socket.binding.port-offset=100 -Dkeycloak.migration.action=export -Dkeycloak.migration.provider=singleFile -Dkeycloak.migration.realmName=shop -Dkeycloak.migration.usersExportStrategy=REALM_FILE -Dkeycloak.migration.file=/tmp/export.json
