echo %cd%
cd ../
echo %cd%
call mvn clean package

docker-compose down

docker system prune

echo deleting existing images...
cd ForWindows\
docker rmi egapm/docker-compose-kubernetes:webstore
docker rmi egapm/docker-compose-kubernetes:customer
docker rmi egapm/docker-compose-kubernetes:order
docker rmi egapm/docker-compose-kubernetes:product
docker rmi egapm/docker-compose-kubernetes:quote
docker rmi egapm/docker-compose-kubernetes:payment
docker rmi egapm/docker-compose-kubernetes:gateway

echo building new images...
docker-compose build

echo pushing to cloud repository...
docker-compose push