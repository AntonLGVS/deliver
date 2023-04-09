plugins {
    java
    groovy
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

group = "com.deliver"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    // CONTRACT
    implementation(project(":contract"))

    // SPRING
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.kafka:spring-kafka")

    // STATEMACHINE
    implementation("org.springframework.statemachine:spring-statemachine-starter")

    // JAKARTA
    implementation("org.hibernate.validator:hibernate-validator:8.0.0.Final")
    implementation("jakarta.validation:jakarta.validation-api:${project.properties["versions.jakarta"]}")

    // SWAGGER
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.4")

    // MIGRATION
    implementation("org.liquibase:liquibase-core")

    // POSTGRES
    runtimeOnly("org.postgresql:postgresql")

    // LOMBOK
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // MAPSTRUCT
    implementation("org.mapstruct:mapstruct:${project.properties["versions.mapstruct"]}")
    annotationProcessor("org.mapstruct:mapstruct-processor:${project.properties["versions.mapstruct"]}")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    // DEV TOOLS
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // TESTING
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.springframework.security:spring-security-test")

    //SPOCK
    testImplementation("org.spockframework:spock-core:2.4-M1-groovy-4.0")
    testImplementation("org.spockframework:spock-spring:2.4-M1-groovy-4.0")

    // TESTCONTAINER
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:kafka")
    testImplementation("org.testcontainers:postgresql")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.statemachine:spring-statemachine-bom:${project.properties["versions.stateMachine"]}")
        mavenBom("org.testcontainers:testcontainers-bom:${project.properties["versions.testContainer"]}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
