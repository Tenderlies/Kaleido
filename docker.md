# Docker

## 配置

## 容器

### 创建 Redis

docker run --name redis -d -v /d/env/docker-volume/redis:/data -p 6379:6379 redis redis-server --appendonly yes

## MiniKube

```shell
minikube start --registry-mirror=https://registry.docker-cn.com --vm-driver="hyperv" --hyperv-virtual-switch="HyperV" --docker-env=HTTP_PROXY=http://192.168.199.120:1080 --docker-env HTTPS_PROXY=http://192.168.199.120:1080 --docker-env NO_PROXY=localhost,127.0.0.1,10.96.0.0/12,192.168.99.1/24,192.168.39.0/24
```

option: --memory=4096

## 用法

### 查看日志

```shell
docker logs -f containers-name
vi /var/lib/docker/containers/container-id/container-id-json.log
```
