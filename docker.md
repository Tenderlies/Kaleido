# Docker

## 安装

### 一键安装

```shell
curl -fsSL https://get.docker.com | bash -s docker --mirror Aliyun
```

```shell
curl -sSL https://get.daocloud.io/docker | sh
```


## 配置

### 镜像

```
{
  "registry-mirrors": [
    "https://pz9ytxkf.mirror.aliyuncs.com",
    "http://f1361db2.m.daocloud.io"
  ],
  "insecure-registries": [],
  "debug": false,
  "experimental": false,
  "features": {
    "buildkit": true
  }
}

```

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

## Kubernetes

### 代码补全

```bash
vi ~/.zshrc
    source <(kubectl completion zsh)
```

### WebUI

Repo: `https://github.com/kubernetes/dashboard`

Cmd: 

```bash
kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.3.1/aio/deploy/recommended.yaml

K8S_DASHBOARD_TOKEN=`kubectl -n kube-system describe secret default | awk '$1=="token:"{print $2}'`
kubectl config set-credentials docker-desktop --token="${K8S_DASHBOARD_TOKEN}"

kubectl proxy
```

Url: `http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/`
