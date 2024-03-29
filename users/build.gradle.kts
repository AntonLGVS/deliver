import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	kotlin("jvm")
	kotlin("plugin.spring")
	kotlin("kapt")
}

group = "com.deliver"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

//configurations {
//	compileOnly {
//		extendsFrom(configurations.kapt.get())
//	}
//}

dependencies {
	// CONTRACT
	implementation(project(":contract"))

	// SPRING
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	// JAKARTA
	implementation("org.hibernate.validator:hibernate-validator:8.0.0.Final")
	implementation("jakarta.validation:jakarta.validation-api:${project.properties["versions.jakarta"]}")

	// KEYCLOAK
//	implementation("org.keycloak:keycloak-spring-boot-starter:21.0.1")
//	implementation("org.keycloak:keycloak-spring-boot-starter:21.0.1")
//	implementation("org.keycloak:keycloak-admin-client:21.0.2")
//
//	implementation("org.springframework.security:spring-security-config")
//	implementation("org.springframework.security:spring-security-oauth2-jose")
//	implementation("org.springframework.security:spring-security-oauth2-resource-server")

	// SWAGGER
	implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.0.4")

	// KOTLIN MODULES
	implementation("org.jetbrains.kotlin:kotlin-reflect")
//	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
//	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

	// MIGRATION
	implementation("org.flywaydb:flyway-core")

	// MAPSTRUCT
	implementation("org.mapstruct:mapstruct:${project.properties["versions.mapstruct"]}")
	kapt("org.mapstruct:mapstruct-processor:${project.properties["versions.mapstruct"]}")

	// UTILITIES AND DEV TOOLS
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// POSTGRES
	runtimeOnly("org.postgresql:postgresql")
//	runtimeOnly("org.postgresql:r2dbc-postgresql")

	// TESTING
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	testImplementation("org.springframework.security:spring-security-test")
//	testImplementation("io.projectreactor:reactor-test")
}

dependencyManagement {
	imports {
		mavenBom("org.keycloak.bom:keycloak-adapter-bom:12.0.3")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = java.sourceCompatibility.majorVersion
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
