@ECHO OFF
SET dir=%cd%

cd "%dir%"\k8s-confs

kubectl apply -f microservices
kubectl apply -f ingress.yaml