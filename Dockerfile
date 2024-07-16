# Usando uma imagem base do OpenJDK
FROM registry-docker.weg.net/openjdk:17-jdk-alpine

# Defina o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copie o arquivo JAR da aplicação para o contêiner
COPY target/observability-test-0.0.1-SNAPSHOT.jar /app/observability-test.jar

# Exponha a porta que a aplicação irá usar
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "/app/observability-test.jar"]