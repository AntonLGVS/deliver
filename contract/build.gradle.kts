plugins {
    java
}

group = "org.deliver"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // UTILITY
    compileOnly("org.projectlombok:lombok:${project.properties["versions.lombok"]}")
    annotationProcessor("org.projectlombok:lombok:${project.properties["versions.lombok"]}")

    // TESTING
    testImplementation("org.junit.jupiter:junit-jupiter-api:${project.properties["versions.jupiter"]}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${project.properties["versions.jupiter"]}")
}

tasks.withType<Test> {
    useJUnitPlatform()
}