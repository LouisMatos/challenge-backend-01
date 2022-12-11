# challenge-backend

### Comandos
**Para inicializar a API**

```sh
mvn clean package -Dmaven.test.skip=true -Dspring.profiles.active=prod
mvn clean package -Dmaven.test.skip=true -Dspring.profiles.active=test
mvn clean package -Dmaven.test.skip=true -Dspring.profiles.active=dev
docker-compose build --no-cache && docker-compose up -d

-Xms512m -Xmx1024m -XX:MaxDirectMemorySize=64M  -XX:MaxMetaspaceSize=128m -XX:ReservedCodeCacheSize=240M -Xss64M

```