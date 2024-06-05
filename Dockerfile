# Use a imagem oficial do MySQL
FROM mysql:latest

# Define as variáveis de ambiente para a senha do root do MySQL
ENV MYSQL_ROOT_PASSWORD=appindividual123

# Copie o script SQL para o contêiner
COPY ./banco-mindcore.sql /docker-entrypoint-initdb.d/banco-mindcore.sql

EXPOSE 3306