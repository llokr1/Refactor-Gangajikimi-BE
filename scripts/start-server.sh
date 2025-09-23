#!/bin/bash

echo "--------------- 서버 배포 시작 -----------------"

# 1. 기존 애플리케이션 컨테이너 중지 및 삭제
docker stop gangajikimi-server || true
docker rm gangajikimi-server || true

# 2. 기존 Redis 컨테이너 중지 및 삭제
docker stop redis || true
docker rm redis || true

# 3. 네트워크 생성 (없으면 생성)
docker network create gangajikimi-net || true

# 4. Redis 컨테이너 실행 (공유 네트워크 사용)
echo "Redis 컨테이너 실행 중..."
docker run -d --name redis --network gangajikimi-net -p 6379:6379 redis:7

# 5. dockerhub 에서 최신 애플리케이션 이미지 pull
echo "dockerhub 최신 이미지 Pull 중..."
docker pull dldnjsgml3054/gangajikimi-server:latest

# 6. 새 애플리케이션 컨테이너 실행 (Redis 호스트를 'redis'로 지정)
echo "새 애플리케이션 컨테이너 실행 중..."
docker run -d --name gangajikimi-server --network gangajikimi-net -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=dev \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://gangajikimi-db.cjsgei08gb4x.ap-northeast-2.rds.amazonaws.com:5432/postgres \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=gangajikimi1234 \
  -e SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver \
  -e SPRING_SQL_INIT_MODE=never \
  -e SPRING_JPA_HIBERNATE_DDL_AUTO=update \
  -e SPRING_JPA_SHOW_SQL=true \
  -e SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect \
  -e SPRING_DATA_REDIS_HOST=redis \
  -e SPRING_DATA_REDIS_PORT=6379 \
  -e SPRING_CACHE_TYPE=redis \
  -e JWT_ACCESS_SECRETKEY=gangajikimibimilkeyjironggaebalgeumanhagosibda \
  -e JWT_ACCESS_EXPIRATION=600000 \
  -e JWT_REFRESH_EXPIRATION=1209600000 \
  -e LOGGING_LEVEL_ORG_HIBERNATE_TYPE=trace \
  -e LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_SECURITY=debug \
  -e SERVER_SERVLET_ENCODING_CHARSET=UTF-8 \
  -e SERVER_SERVLET_ENCODING_FORCE=true \
  -e SERVER_SERVLET_ENCODING_ENABLED=true \
  dldnjsgml3054/gangajikimi-server:latest

echo "--------------- 서버 배포 끝 -----------------"
