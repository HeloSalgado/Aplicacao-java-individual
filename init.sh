#!/bin/bash

CONTAINER_NAME="bd-individual"
IMAGE_NAME="helosalgado/app-individual:bd"

# Função para verificar se a imagem existe
image_exists() {
    docker images -q $1 > /dev/null 2>&1
}

# Verifica se a imagem existe
if image_exists $IMAGE_NAME; then
    echo "A imagem '$IMAGE_NAME' existe localmente."
else
    echo "A imagem '$IMAGE_NAME' não existe localmente. Instalando..."
    docker pull $IMAGE_NAME
fi

if [ "$(docker ps -a -q -f name=$CONTAINER_NAME)" ]; 
    then
        echo "Iniciando o contêiner '$CONTAINER_NAME'."
        docker start $CONTAINER_NAME
    else
        echo "Criando e iniciando o contêiner '$CONTAINER_NAME'."
        docker run -d -p 3306:3306 --name $CONTAINER_NAME -e MYSQL_PASSWORD=appindividual123 -p 3307:3306 $IMAGE_NAME
fi

sleep 10

java -jar jar-com-banco/login-mind-core-1.0-SNAPSHOT-jar-with-dependencies.jar