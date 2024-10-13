# Etapa de compilación
# Imagen base
FROM eclipse-temurin:17.0.7_7-jdk

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar los archivos del proyecto al contenedor
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

COPY . ./app

# Establecer el comando para ejecutar la aplicación
CMD if [ -x "$(command -v mvnw)" ]; then sh mvnw spring-boot:run; else ./mvnw spring-boot:run; fi
