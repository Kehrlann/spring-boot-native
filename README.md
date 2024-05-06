# spring-boot-native

Native apps with Spring Boot


## TODO

- A word on buildpacks ✅
- Tracing agent demo (apache POI)
- Rework the demo as a single domain
- Local perf tests ✅
- GraalVM EE ✅

## Table of contents

- Closed-world assumptions, AOT, and conditionals
    - `@Profile`
    - `@Conditional...`
    - ⚠️ Tests don't catch those
    - Dynamic behavior MAY be achieved with "if" branches
        - properties
        - injecting `Environment` and getting `Environment#getActiveProfiles()`
- Reflection: mostly caught by tests!
    - Thymeleaf
    - xpath
- Serialization
    - RestClient
    - ObjectMapper

## Topics of interest

- How AOT works ✅
- Serialization (e.g. produce JSON logs) ✅
- Profiles ✅
- Fast compilation `-Ob` ✅
- Native tests ✅
- CPU architecture matters
- Incompatible libraries
    - Tracing agent
    - Suggested: apache POI
- SPeL
- Thymeleaf templates ✅
- Mockito
- Java agents

## Open questions

- Does serialization work for RestClient?
- Thymeleaf templates without the web layer?
    - See: https://stackoverflow.com/questions/17085410/can-i-render-thymeleaf-templates-manually-from-a-string
- Does libc matter?
- HTMLUnit? :|

## Learnings, gotchas, etc

Initial setup: `-parameters` in the Java Compiler, for reflection on param names.

### Compilation

Use correct Java distribution:

```
sdk use java 22-graalce
```

Newer versions are faster, for compilation!

Compile and run the binary:

```
./gradlew clean nativeCompile
./native/build/native/nativeCompile/native
```

Compile time ~1mn.

Add `-Ob` for faster compilation

### Testing

```
./gradlew :native:nativeTest
```

### Dynamic stuff

- `@Conditional` not working, it takes the bean configuration that was setup at compile time.
    - e.g. `GREETING_LANG=fr ./gradlew nativeCompile` will take the French language at compile time
    - tests do not reflect this as an AOT source is created per test context
- `@Profile` is a subset of `@Conditional`, not working!
- `@Value` works as expected
    - including the default value
    - including env var configuration
    - including embedded profiles file

### Using the tracing agent

```
java -Dspring.aot.enabled=true \
        -agentlib:native-image-agent=config-output-dir=books/src/main/resources/META-INF/native-image,access-filter-file=filter.json \
        -jar \
        books/build/libs/books-0.0.1-SNAPSHOT.jar
```
