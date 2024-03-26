# spring-boot-native

Native apps with Spring Boot

## Topics of interest

- How AOT works
- Serialization (e.g. produce JSON logs)
- Profiles
- Fast compilation `-Ob`
- Native tests
- CPU architecture matters
- Incompatible libraries
  - Tracing agent
  - Suggested: apache POI
- SPeL
- Thymeleaf templates
- Java agents

## Open questions

- Dynamic profiles?
- Does serialization work for RestClient?
- Does libc matter?

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


## Testing

```
./gradlew :native:nativeTest
```
