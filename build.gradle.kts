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
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // Kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // cache
    implementation("org.springframework.boot:spring-boot-starter-cache")

    // 화면 필요시
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    // swagger
    //implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.4")
    implementation("org.springdoc:springdoc-openapi-ui:1.7.0")

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
    testImplementation("io.github.autoparams:autoparams-kotlin:3.1.0")

    // pdf box
    implementation("org.apache.pdfbox:pdfbox:2.0.27")

    // json -> jpa
    implementation("com.vladmihalcea:hibernate-types-52:2.21.1")

    // Actuator
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    // Prometheus
    implementation("io.micrometer:micrometer-registry-prometheus")

    // Feign
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("io.github.openfeign:feign-core:12.4")
    implementation("io.github.openfeign:feign-slf4j:12.4")
    implementation("io.github.openfeign:feign-jackson:12.4")


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