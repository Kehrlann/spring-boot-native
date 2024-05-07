---
theme: default
class: 'text-center'
highlighter: shiki
lineNumbers: true
transition: none
# use UnoCSS
css: unocss
aspectRatio: "16/9"
colorSchema: "light"
hideInToc: true
---

# Migrating Spring Boot apps to Native

<br>
<br>

### Daniel Garnier-Moiroux

Devoxx UK, 2024-05-09


---
layout: image-right
image: /daniel-intro.jpg
hideInToc: true
class: smaller
---

#### Daniel
### Garnier-Moiroux
<br>

Software Engineer @ Broadcom

- <logos-spring-icon /> Spring + Tanzu
- <logos-mastodon-icon /> @Kehrlann@hachyderm.io
- <logos-twitter /> @Kehrlann
- <logos-firefox /> https://garnier.wf/
- <logos-github-icon /> github.com/Kehrlann/
- <fluent-emoji-flat-envelope-with-arrow /> contact@garnier.wf


---

# Migrating Spring apps to Native

1. 📚 Java Native Images refresher
1. 🏺️ "Closed-world" assumption
1. 🪞️ Closed-world: Reflection
1. ✋ Closed-world: Everything else
1. 🧑‍🔬 Testing
1. 🍃️ Closed-world: Spring annotations
1. 🕵️ Unsupported libraries
1. 💻 Packaging and distribution: CPU, libc
1. 🏃 Performance

---

# Migrating Spring apps to Native

1. 📚 Java Native Images refresher
1. 🏺️ "Closed-world" assumption
1. 🪞️ Closed-world: Reflection
1. ✋ Closed-world: Everything else
1. 🧑‍🔬 Testing
1. 🍃️ Closed-world: Spring annotations
1. 🕵️ Unsupported libraries
1. 💻 Packaging and distribution: CPU, libc
1. 🏃 Performance

---

# "Native Image" Refresher

<br />

> Native Image is a technology to compile Java code ahead-of-time to a binary – a **native executable**. A native executable includes only the code required at run time, that is the application classes, standard-library classes, the language runtime, and statically-linked native code from the JDK.

Source: https://www.graalvm.org/latest/reference-manual/native-image/

---

# "Native Image" refresher

<br />

### 🧑‍💻 Native image demo

---

# "Native Image" refresher

<br />

### _Pro Tips™_

<br />

- Use the latest and greatest (currently Java 22)
- Use the `-Ob` flag for faster cycle time
  - But not in prod ...

---

# Migrating Spring apps to Native

1. 📚 Java Native Images refresher
1. 🏺️ "Closed-world" assumption
1. 🪞️ Closed-world: Reflection
1. ✋ Closed-world: Everything else
1. 🧑‍🔬 Testing
1. 🍃️ Closed-world: Spring annotations
1. 🕵️ Unsupported libraries
1. 💻 Packaging and distribution: CPU, libc
1. 🏃 Performance

---

# "Closed-world" assumption

<br />

> Building a standalone binary with the native-image tool takes place under a “closed world assumption”. The `native-image` tool performs an analysis to see which classes, methods, and fields within your application are reachable and must be included in the native image. The analysis is static: it does not run your application. This means that all the bytecode in your application that can be called at run time must be known (observed and analyzed) at build time.

Source: https://www.graalvm.org/latest/reference-manual/native-image/

---

# "Closed-world": other limitations

<br />

> The analysis can determine some cases of dynamic class loading, but it cannot always exhaustively predict all usages of
> - the Java Native Interface (JNI)
> - Java Reflection
> - Dynamic Proxy objects
> - class path resource

Source: https://www.graalvm.org/latest/reference-manual/native-image/

---

# "Closed-world": Java Agents

<br />

Supported when included at build time: `-J-javaagent:agent.jar`

---

# Migrating Spring apps to Native

1. 📚 Java Native Images refresher
1. 🏺️ "Closed-world" assumption
1. 🪞️ Closed-world: Reflection
1. ✋ Closed-world: Everything else
1. 🧑‍🔬 Testing
1. 🍃️ Closed-world: Spring annotations
1. 🕵️ Unsupported libraries
1. 💻 Packaging and distribution: CPU, libc
1. 🏃 Performance

---

# Reflection

<br />

Supported by GraalVM, but classes must be pre-registered:

- AOT processing with Spring
- [GraalVM Metadata repository](https://www.graalvm.org/native-image/libraries-and-frameworks/)
- `reflect-config.json`

---

# Reflection

<br />

### 🧑‍💻 Reflection demo

---

# Reflection, beware of:

&nbsp;

- Code inside strings (`SpEL`, templates)
- JSON (un)marshalling
  - Spring covers `@Controller` and `@Repository`
- Classpath scanning (`webjars-locator`)
- Libs that are NOT compatible

For Spring: `@ImportRuntimeHints` + `@RegisterReflectionForBinding`

---

# Migrating Spring apps to Native

1. 📚 Java Native Images refresher
1. 🏺️ "Closed-world" assumption
1. 🪞️ Closed-world: Reflection
1. ✋ Closed-world: Everything else
1. 🧑‍🔬 Testing
1. 🍃️ Closed-world: Spring annotations
1. 🕵️ Unsupported libraries
1. 💻 Packaging and distribution: CPU, libc
1. 🏃 Performance

---

# Other dynamic behavior

- Resources (files, e.g. `i18n`, ...)
  - Spring covers config files, DB schemas, templates, banner ...
- Java serialization (`implements Serializable`)
- JNI
- Other limitations: [Spring Boot Wiki](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-with-GraalVM)

---

# Migrating Spring apps to Native

1. 📚 Java Native Images refresher
1. 🏺️ "Closed-world" assumption
1. 🪞️ Closed-world: Reflection
1. ✋ Closed-world: Everything else
1. 🧑‍🔬 Testing
1. 🍃️ Closed-world: Spring annotations
1. 🕵️ Unsupported libraries
1. 💻 Packaging and distribution: CPU, libc
1. 🏃 Performance

---

# Native Testing

<br />

```
./gradlew nativeTest
```

Biggest limitations:

- Mockito
- Testcontainers

---

# Native testing

<br />

### 🧑‍💻 Native testing

---

# Native Testing: recap

&nbsp;

Tests can speed your migrations:

- Good coverage
- Dedicated end-to-end tests

Anti-goal:

- Thourough behavior verification
- Run on every build

---
layout: fact
---

# Please test responsibly

# 👷️ 🥽 🦺️

---

# Migrating Spring apps to Native

1. 📚 Java Native Images refresher
1. 🏺️ "Closed-world" assumption
1. 🪞️ Closed-world: Reflection
1. ✋ Closed-world: Everything else
1. 🧑‍🔬 Testing
1. 🍃️ Closed-world: Spring annotations
1. 🕵️ Unsupported libraries
1. 💻 Packaging and distribution: CPU, libc
1. 🏃 Performance

---

# "Closed-world" impact on Spring

&nbsp;

🚨️ Annotations that select classes dynamically to be avoided

- `@Profile`
- `@ConditionalOn...`

✅ Other annotations are fine

- Not related to classes - `@Value`
- Not conditional - `@Configuration`, `@Controller`, ...

---

# "Closed-world" impact on Spring

<br />

### 🧑‍💻 `@Profile` & `@Conditional` demo

---

# "Closed-world" impact on Spring

&nbsp;

🚨️ Annotations that select classes dynamically to be avoided

- `@Profile`
- `@ConditionalOn...`

✅ Other annotations are fine

- Not related to classes - `@Value`
- Not conditional - `@Configuration`, `@Controller`, ...

---

# Migrating Spring apps to Native

1. 📚 Java Native Images refresher
1. 🏺️ "Closed-world" assumption
1. 🪞️ Closed-world: Reflection
1. ✋ Closed-world: Everything else
1. 🧑‍🔬 Testing
1. 🍃️ Closed-world: Spring annotations
1. 🕵️ Unsupported libraries
1. 💻 Packaging and distribution: CPU, libc
1. 🏃 Performance

---

# Packaging and distribution

<br />

- CPU architecture matters
- By default, `libc` matters
- No cross-compilation
  - Compile on target architecture (think: Linux x86_84)
- By default, dynamically linked against `libc`
  - Can't compile on ubuntu and run on alpine
- Use buildpacks!

---

# Packaging and distribution

<br />

### 🧑‍💻 Distribution demo

---

# Packaging and distribution recap

<br />

tl;dr:

- Compile on target architecture (think: Linux x86_84)
- Buildpacks!

---

# Migrating Spring apps to Native

1. 📚 Java Native Images refresher
1. 🏺️ "Closed-world" assumption
1. 🪞️ Closed-world: Reflection
1. ✋ Closed-world: Everything else
1. 🧑‍🔬 Testing
1. 🍃️ Closed-world: Spring annotations
1. 🕵️ Unsupported libraries
1. 💻 Packaging and distribution: CPU, libc
1. 🏃 Performance

---

# Performance

<br />

### 🧑‍💻 Performance demo

---

# Performance

<br />

- Starts _really_ fast
- Instant peak performance
- Less-resource hungry
- JVM scales better over time*

---

# Migrating Spring apps to Native

1. 📚 Java Native Images refresher
1. 🏺️ "Closed-world" assumption
1. 🪞️ Closed-world: Reflection
1. ✋ Closed-world: Everything else
1. 🧑‍🔬 Testing
1. 🍃️ Closed-world: Spring annotations
1. 🕵️ Unsupported libraries
1. 💻 Packaging and distribution: CPU, libc
1. 🏃 Performance

---

Repo:
- <logos-github-icon /> https://github.com/Kehrlann/spring-boot-native

Reach out:
- <logos-mastodon-icon /> @Kehrlann@hachyderm.io
- <logos-twitter /> @Kehrlann
- <fluent-emoji-flat-envelope-with-arrow /> contact@garnier.wf

---
layout: image-right
image: /qr-code.png
---

# 🤔 Questions?

<br>

Feedback 👉👉

Feedback helps a lot! 🙇

