# Docker部署

```shell
mkdir -p /home/nginx/oauth2.i5zhen.com/public
mkdir -p /home/nginx/oauth2.i5zhen.com/log

mkdir -p /home/docker/gemini-oauth2
cd /home/docker/gemini-oauth2
wget -O compose.yaml https://github.com/kuretru/Gemini-OAuth2/raw/main/scripts/docker/compose.yaml
wget -O environment https://github.com/kuretru/Gemini-OAuth2/raw/main/scripts/docker/environment

docker compose up -d
```
