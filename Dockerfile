# ---------- Etapa 1: build ----------
# Compila o projeto usando Maven + JDK 17. Essa imagem e maior, mas
# fica so nessa etapa intermediaria e nao vai para a imagem final.
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copia primeiro so o pom.xml para aproveitar o cache de dependencias
# do Docker (se o pom nao mudar, o "mvn dependency:go-offline" nao
# precisa baixar tudo de novo nas proximas builds).
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Agora copia o codigo fonte (inclui o front-end em src/main/resources/static)
COPY src ./src

# Gera o JAR executavel, sem rodar os testes
RUN mvn -B clean package -DskipTests

# ---------- Etapa 2: runtime ----------
# Imagem final, bem mais leve: so o JRE, sem Maven nem codigo fonte.
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copia apenas o JAR gerado na etapa de build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]