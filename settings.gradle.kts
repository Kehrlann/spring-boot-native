rootProject.name = "spring-boot-native-samples"
include("books", "examples", "buildpacks")

pluginManagement {
    repositories {
        maven { url = uri("https://repo.spring.io/milestone") }
        gradlePluginPortal()
    }
    plugins {
        id("org.springframework.boot") version "3.4.0-RC1"
        id("io.spring.dependency-management") version "1.1.6"
        id("io.spring.javaformat") version "0.0.43"
        id("org.graalvm.buildtools.native") version "0.10.3"
        id("com.dorongold.task-tree") version "3.0.0"
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven { url = uri("https://repo.spring.io/milestone") }
    }
}