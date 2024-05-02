import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    java
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    id("io.spring.javaformat") version "0.0.41"
    id("org.graalvm.buildtools.native") version "0.10.1"
}

group = "wf.garnier"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-security")

    developmentOnly("org.springframework.boot:spring-boot-docker-compose")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
}

tasks.compileJava {
    options.compilerArgs.add("-parameters")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

graalvmNative {
    binaries.named("test") {
        buildArgs.add("-Ob")
    }
    if (System.getenv("NATIVE_DEBUG") != null) {
        graalvmNative.binaries.named("main") {
            buildArgs.add("-Ob")
        }
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

tasks.register("composeUp", type = Exec::class) {
    doFirst {
        println("\n\n\t🐳🛫 Starting up docker compose...\n\n")
    }
    commandLine("docker", "compose", "up", "-d", "--wait")
}

tasks.register("composeDown", type = Exec::class) {
    doFirst {
        println("\n\n\t\t🐳🛬 Shutting down docker compose...\n\n")
    }
    commandLine("docker", "compose", "down")
}

tasks.nativeTest {
    dependsOn("composeUp").mustRunAfter("nativeTestCompile")
    finalizedBy("composeDown")
}

tasks.bootBuildImage {
    imageName = "native:buildpack"
    publish = false

    // Beta: build native images on ARM arch
    // Lightweight builder, avoid pulling all buildpacks
    builder.set("paketobuildpacks/builder-jammy-buildpackless-tiny")

    // A single buildpack, for java+native, that supports arm64
    buildpacks.add("gcr.io/paketo-buildpacks/java-native-image:beta")

    // Not required because we pull the single builder java-native-image
    // which defaults to building native images.
    // When using default buidpacks (e.g. on x86), or when dual-arch is GA,
    // this flag will be required.
    environment.put("BP_NATIVE_IMAGE", "true")
}