@ECHO OFF

kubectl delete ingress --all
kubectl delete pods --all
kubectl delete service --all

kubectl apply -f k8s-confs/microservices
kubectl apply -f k8s-confs/ingress.yaml

gcloud container clusters update loadbalancedcluster --enable-autoscaling --min-nodes 1 --max-nodes 3

pause