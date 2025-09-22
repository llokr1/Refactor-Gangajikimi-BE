#!/bin/bash

echo "--------------- 서버 배포 시작 -----------------"

# 기존 애플리케이션 컨테이너 중지 및 삭제
docker stop gangajikimi-server || true
docker rm gangajikimi-server || true

# 기존 Redis 컨테이너 중지 및 삭제
docker stop redis || true
docker rm redis || true

# Redis 컨테이너 실행 (백그라운드, 6379 포트)
echo "Redis 컨테이너 실행 중..."
docker run -d --name redis -p 6379:6379 redis:7

# 애플리케이션 컨테이너 실행
echo "애플리케이션 컨테이너 실행 중..."
docker pull 339462084118.dkr.ecr.ap-northeast-2.amazonaws.com/gangajikimi-server:latest
docker run -d --name gangajikimi-server -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=dev \
  339462084118.dkr.ecr.ap-northeast-2.amazonaws.com/gangajikimi-server:latest

echo "--------------- 서버 배포 끝 -----------------"
