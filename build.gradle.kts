plugins {
    java
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "personnel.jupitorSendsme"
version = "0.0.1-SNAPSHOT"
description = "PulseTicket"
// Mockito agent 정적 로딩을 위한 변수
val mockitoAgent = configurations.create("mockitoAgent")

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.session:spring-session-data-redis")

    // lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")

    // dev
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.assertj:assertj-core")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
    testRuntimeOnly("com.h2database:h2")
    testImplementation("com.github.codemonstur:embedded-redis:1.4.3")

    // Mockito agent (Java 21+ 지원)
    mockitoAgent("org.mockito:mockito-core") { isTransitive = false }

    // Argon2id 비밀번호 해싱
    implementation("org.springframework.security:spring-security-crypto")
    implementation("org.bouncycastle:bcprov-jdk18on:1.81")
}

// ============================================================================
// UTF-8 인코딩 설정
// 모든 컴파일 작업에서 UTF-8 인코딩 사용
// ============================================================================
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Test> {
    systemProperty("file.encoding", "UTF-8")
    useJUnitPlatform()

    // gradle.properties 값을 시스템 프로퍼티로 전달
    systemProperty("TEST_POSTGRES_PORT", findProperty("TEST_POSTGRES_PORT") ?: "")
    systemProperty("TEST_POSTGRES_DB", findProperty("TEST_POSTGRES_DB") ?: "")
    systemProperty("TEST_POSTGRES_USER", findProperty("TEST_POSTGRES_USER") ?: "")
    systemProperty("TEST_POSTGRES_PASSWORD", findProperty("TEST_POSTGRES_PASSWORD") ?: "")
    systemProperty("TEST_REDIS_PORT", findProperty("TEST_REDIS_PORT") ?: "")
    systemProperty("TEST_REDIS_PASSWORD", findProperty("TEST_REDIS_PASSWORD") ?: "")

    // 클래스 데이터 공유 관련 경고 방지
    jvmArgs("-Xshare:off")

    // Mockito agent 정적 로딩 (Java 21+ 동적 agent 로딩 경고 방지)
    jvmArgs("-javaagent:${mockitoAgent.asPath}")
}

// ============================================================================
// Spring Boot JAR 설정
// Layered JAR를 활성화하여 Docker 이미지 빌드 시 캐시 효율성 향상
// ============================================================================
tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    layered {
        enabled = true
    }
}
