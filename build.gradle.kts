import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {

    val kotlinVersion = "1.8.20"

    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("kapt") version kotlinVersion

    idea
}

group = "app"
version = "0.0.2"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

val springCloudVersion = "2021.0.3"

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.0")

    // jackson-module-kotlin 은 매개변수가 없는 생성자가 없더라도 직렬화와 역직렬화를 지원
    // 코틀린은 매개변수가 없는 생성자를 만들기 위해 생성자의 모든 매개변수에 기본 인자가 필요
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // cache
    implementation("org.springframework.boot:spring-boot-starter-cache")

    // 화면 필요시
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    // swagger
    implementation("io.springfox:springfox-boot-starter:3.0.0")

    // lucy filter & util 추가
    implementation("org.apache.commons:commons-text:1.10.0")
    implementation("com.navercorp.lucy:lucy-xss-servlet:2.0.1")

    // jwt
    implementation("io.jsonwebtoken:jjwt:0.9.1")

    // jasypt
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5")

    // db
    runtimeOnly("com.h2database:h2")
    runtimeOnly("mysql:mysql-connector-java")

    // jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // querydsl
    implementation("com.querydsl:querydsl-jpa:5.0.0")
    kapt("com.querydsl:querydsl-apt:5.0.0:jpa")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // open ai
    implementation("com.theokanning.openai-gpt3-java:api:0.11.1")
    implementation("com.theokanning.openai-gpt3-java:service:0.11.1")

    // pdf box
    implementation("org.apache.pdfbox:pdfbox:2.0.27")

    // emoji java
    implementation("com.vdurmont:emoji-java:5.1.1")

    // JSOUP
    implementation("org.jsoup:jsoup:1.15.4")

    // WebClient : Spring5 이후 RestTemplate을 대체하기 위해 나온 비동기 & 논블로킹 네트워크 요청 라이브러리
    implementation("org.springframework.boot:spring-boot-starter-webflux")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
    }
}

tasks {
    test {
        useJUnitPlatform()
    }

    withType<Test> {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }
}

// QueryDsl 설정
allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}
noArg {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

idea {
    module {
        val kaptMain = file("build/generated/source/kapt/main")
        sourceDirs.add(kaptMain)
        generatedSourceDirs.add(kaptMain)
    }
}