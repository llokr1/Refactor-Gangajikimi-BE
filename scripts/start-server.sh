#!/bin/bash

echo "--------------- 서버 배포 시작 -----------------"

# 기존 컨테이너/네트워크 정리
docker compose down || true

# 최신 이미지 pull
docker pull dldnjsgml3054/gangajikimi-server:latest

# 컨테이너 실행
docker compose up -d

echo "--------------- 서버 배포 끝 -----------------"
