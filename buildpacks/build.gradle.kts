plugins {
    java
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    id("io.spring.javaformat") version "0.0.41"
    id("org.graalvm.buildtools.native") version "0.10.1"
    id("com.dorongold.task-tree") version "3.0.0"
    // Note: this must match the "book" project's build script.
    // See https://github.com/gradle/gradle/issues/17559
    // We could put it all under the root settings, but I want the "books"
    // project to be somewhat self-contained.
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
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.bootBuildImage {
    imageName = "demo:native"
    publish = false
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

// Disable time-consuming tasks. This is a demo, we don't want those tasks to run from the root project.
project.afterEvaluate {
    val excludedTasks = listOf(
        tasks.bootRun,
        tasks.processAot,
        tasks.processTestAot,
        tasks.nativeCompile,
        tasks.nativeTest,
        tasks.nativeTestCompile,
        tasks.bootBuildImage
    )
    excludedTasks
        .map { it.get() }
        .forEach {
            it.enabled = false
        }
}

