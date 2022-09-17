# 2022-05-otus-spring-belov
Проектная работа

Белов Д.П.

---
### TODO

### Экспорт настроенной конфигурации keycloak
- Прописать volume: `"./keycloak/export.json:/tmp/export.json"`
- Запустить контейнер
- Подключиться к контейнеру
- Выполнить /opt/jboss/keycloak/bin/standalone.sh -Djboss.socket.binding.port-offset=100 -Dkeycloak.migration.action=export -Dkeycloak.migration.provider=singleFile -Dkeycloak.migration.realmName=shop -Dkeycloak.migration.usersExportStrategy=REALM_FILE -Dkeycloak.migration.file=/tmp/export.json