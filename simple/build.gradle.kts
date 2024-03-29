import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    java
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    id("io.spring.javaformat") version "0.0.41"
    id("org.graalvm.buildtools.native") version "0.9.28"
}

group = "wf.garnier"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.compileJava {
    options.compilerArgs.add("-parameters")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

graalvmNative {
    binaries.all {
        buildArgs.add("-Ob")
    }
}

tasks.format {
    // Make sure we only format the Main and Test source sets, not the AOT-generated sources.
    dependsOn.clear()
    dependsOn(tasks.formatMain)
    dependsOn(tasks.formatTest)
}

tasks.checkFormat {
    // Make sure we only checkFormat the Main and Test source sets, not the AOT-generated sources.
    dependsOn.clear()
    dependsOn(tasks.checkFormatMain)
    dependsOn(tasks.checkFormatTest)
}

tasks.test {
    testLogging {
        events = setOf(TestLogEvent.STARTED, TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
    }
}