# Use uma imagem base
FROM ubuntu:latest

# Atualize o sistema e instale o Java
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk 

# Verifique se o Java foi instalado corretamente
RUN java -version

RUN mkdir /app
# Defina o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copie o conteúdo local para o contêiner no diretório de trabalho
COPY ./Aplicacao-java-individual/jar-com-banco /app

# Especifique o comando padrão a ser executado quando o contêiner for iniciado
CMD ["java", "-jar", "login-mind-core-1.0-SNAPSHOT-jar-with-dependencies.jar"]