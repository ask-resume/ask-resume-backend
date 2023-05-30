import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.utils.extendsFrom

val queryDslVersion = "5.0.0"

plugins {
    val kotlinVersion = "1.8.20"

    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"

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

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.0")

    // cache
    implementation("org.springframework.boot:spring-boot-starter-cache")

    // 화면 필요시
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    // swagger
    implementation("io.springfox:springfox-boot-starter:3.0.0")

    // lucy filter & util 추가
    implementation("org.apache.commons:commons-text:1.10.0")
    implementation("com.navercorp.lucy:lucy-xss-servlet:2.0.0")

    // jwt
    implementation("io.jsonwebtoken:jjwt:0.9.1")

    // jasypt
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.4")

    // db
    runtimeOnly("com.h2database:h2")
    runtimeOnly("mysql:mysql-connector-java")

    // jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // querydsl
    implementation("com.querydsl:querydsl-jpa:${queryDslVersion}")
    implementation("com.querydsl:querydsl-apt:${queryDslVersion}")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.rest-assured:rest-assured:4.4.0")

    implementation("com.google.guava:guava:31.1-jre")

    // open ai
    implementation("com.theokanning.openai-gpt3-java:api:0.11.1")
    //implementation("com.theokanning.openai-gpt3-java:client:0.11.1")
    implementation("com.theokanning.openai-gpt3-java:service:0.11.1")

    // pdf box
    implementation("org.apache.pdfbox:pdfbox:2.0.27")

    // emoji java
    implementation("com.vdurmont:emoji-java:5.1.1")

    // JSOUP
    implementation("org.jsoup:jsoup:1.15.4")
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