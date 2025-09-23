#!/bin/bash

echo "--------------- 서버 배포 시작 -----------------"

# 1. 기존 애플리케이션 컨테이너 중지 및 삭제
docker stop gangajikimi-server || true
docker rm gangajikimi-server || true

# 2. 기존 Redis 컨테이너 중지 및 삭제
docker stop redis || true
docker rm redis || true

# 3. Redis 컨테이너 실행 (매번 새로 초기화)
echo "Redis 컨테이너 실행 중..."
docker run -d --name redis -p 6379:6379 redis:7

# ECR 로그인 추가 (★ 중요)
echo "ECR 로그인 중..."
aws ecr get-login-password --region ap-northeast-2 \
  | docker login --username AWS --password-stdin 339462084118.dkr.ecr.ap-northeast-2.amazonaws.com

# 4. ECR에서 최신 애플리케이션 이미지 pull
echo "ECR에서 최신 이미지 Pull 중..."
docker pull 339462084118.dkr.ecr.ap-northeast-2.amazonaws.com/gangajikimi-server:latest

# 5. 새 애플리케이션 컨테이너 실행 (DB 환경변수 직접 주입)
echo "새 애플리케이션 컨테이너 실행 중..."
docker run -d --name gangajikimi-server -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=dev \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://gangajikimi-db.cjsgei08gb4x.ap-northeast-2.rds.amazonaws.com:5432/postgres \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=gangajikimi1234 \
  -e SPRING_DATASOURCE_DRIVER-CLASS-NAME=org.postgresql.Driver \
  339462084118.dkr.ecr.ap-northeast-2.amazonaws.com/gangajikimi-server:latest

echo "--------------- 서버 배포 끝 -----------------"
