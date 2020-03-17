cd src/actor-registry
docker build -t actor-registry .
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
cd ../..

#cd src/actor-control
#docker build -t actor-control .
#kubectl apply -f deployment.yaml
#kubectl apply -f service.yaml
#cd ../..

#cd src/actor-simulator
#docker build -t actor-simulator .
#kubectl apply -f deployment.yaml
#kubectl apply -f service.yaml
#cd ../..

#cd src/status-tracking
#docker build -t status-tracking .
#kubectl apply -f deployment.yaml
#kubectl apply -f service.yaml
#cd ../..

#cd src/web-gui
#docker build -t web-gui .
#kubectl apply -f deployment.yaml
#kubectl apply -f service.yaml
#cd ../..

