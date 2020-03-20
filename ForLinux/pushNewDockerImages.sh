#!/bin/sh
cd ../K8s-yamls
kubectl delete -f .
cd db
kubectl delete -f .
cd ../..
mvn clean package 
cd ForLinux
echo $PWD
docker-compose down

docker system prune

echo deleting existing images...
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

echo Starting Kubernetes Pods...
cd K8s-yamls/db
kubectl create -f db.yaml
echo Starting WebStore Pods...
cd ..
exec kubectl create -f .
