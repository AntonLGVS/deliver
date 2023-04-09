dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }
}
pluginManagement {
    plugins {
        java
        groovy
        id("org.springframework.boot") version "3.0.5"
        id("io.spring.dependency-management") version "1.1.0"
        kotlin("jvm") version "1.8.10"
        kotlin("plugin.spring") version "1.8.10"
        idea
    }
}

// ROOT PROPERTIES
rootProject.name = "deliver"

// MODULES OF PROJECT
include("gateway")
include("users")
include("order")
include("dictionary")
include("contract")
