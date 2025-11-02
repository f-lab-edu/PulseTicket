# ============================================================================
# Stage 1: Build Stage
# Gradle을 사용하여 Spring Boot 애플리케이션 JAR 파일을 빌드하는 단계
# ============================================================================
FROM gradle:8.11.1-jdk21 AS build

# 작업 디렉토리 설정
WORKDIR /app

# 빌드 캐시 최적화를 위해 Gradle 설정 파일을 먼저 복사
# 의존성이 변경되지 않으면 이 단계가 캐시되어 빌드 속도 향상
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle ./gradle

# Gradle 래퍼 실행 권한 부여
RUN chmod +x ./gradlew

# 의존성 다운로드 (소스 코드 변경 시에도 의존성 재설치 방지)
RUN ./gradlew dependencies --no-daemon --stacktrace || true

# 소스 코드 복사
COPY src ./src

# Spring Boot JAR 파일 빌드 (Layered JAR 생성)
RUN ./gradlew bootJar --no-daemon --build-cache --stacktrace

# Layered JAR 추출 (Docker 캐시 최적화를 위해 레이어별로 분리)
# dependencies, spring-boot-loader, snapshot-dependencies, application으로 구분
RUN java -Djarmode=layertools -jar build/libs/*.jar extract

# ============================================================================
# Stage 2: Runtime Stage
# 빌드된 JAR 파일의 레이어를 복사하여 실행하기 위한 최소한의 런타임 환경 구성
# ============================================================================
FROM eclipse-temurin:21-jre-alpine

# 보안을 위한 비root 사용자 생성 및 설정
RUN addgroup -S spring && adduser -S spring -G spring

# 작업 디렉토리 설정
WORKDIR /app

# Layered JAR의 각 레이어를 순서대로 복사 (Docker 캐시 활용)
# 변경 빈도가 낮은 순서대로 복사하여 캐시 효율성 극대화
COPY --from=build --chown=spring:spring /app/dependencies/ ./
COPY --from=build --chown=spring:spring /app/spring-boot-loader/ ./
COPY --from=build --chown=spring:spring /app/snapshot-dependencies/ ./
COPY --from=build --chown=spring:spring /app/application/ ./

# 비root 사용자로 전환
USER spring:spring

# 애플리케이션 포트 노출 (Spring Boot 기본 포트)
EXPOSE 8080

# JVM 옵션 설정 및 애플리케이션 실행
# -XX:+UseContainerSupport: 컨테이너 환경에 맞는 메모리 설정 사용
# -XX:MaxRAMPercentage: 사용 가능한 메모리의 75%를 힙 크기로 설정
# org.springframework.boot.loader.launch.JarLauncher: Layered JAR 실행
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "org.springframework.boot.loader.launch.JarLauncher"]

