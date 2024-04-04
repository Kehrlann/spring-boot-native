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

# Migrer des applis Spring vers Native

<br>
<br>

### Daniel Garnier-Moiroux

Spring Dev Meetup, 2024-04-04


---
layout: image-right
image: /daniel-intro.jpg
hideInToc: true
class: smaller
---

#### Daniel
### Garnier-Moiroux
<br>

Software Engineer

- <logos-spring-icon /> Broadcom+Tanzu+Spring
- <logos-mastodon-icon /> @Kehrlann@hachyderm.io
- <logos-twitter /> @Kehrlann
- <logos-firefox /> https://garnier.wf/
- <logos-github-icon /> github.com/Kehrlann/
- <fluent-emoji-flat-envelope-with-arrow /> daniel.garnier-moiroux@broadcom.com


---

# Migration vers Spring Native

1. 📚 Rappels "Native Images" en Java
1. 🏺️ _"Closed-world" assumption:_ en "vase clos"
1. 🤔️ Réflexion
1. 🧑‍🔬 Tests
1. 📄 Resources et fichiers
1. 💻 Distribution: CPU et libc
1. 🏃 Performance
1. 🕵️ Librairies non supportées

---

# Migration vers Spring Native

1. **📚 Rappels "Native Images" en Java**
1. 🏺️ _"Closed-world" assumption:_ en "vase clos"
1. 🤔️ Réflexion
1. 🧑‍🔬 Tests
1. 📄 Resources et fichiers
1. 💻 Distribution: CPU et libc
1. 🏃 Performance
1. 🕵️ Librairies non supportées

---

# Rappels "Native Image"

<br />

> Native Image is a technology to compile Java code ahead-of-time to a binary – a **native executable**. A native executable includes only the code required at run time, that is the application classes, standard-library classes, the language runtime, and statically-linked native code from the JDK.

Source: https://www.graalvm.org/latest/reference-manual/native-image/

---

# Rappels "Native Image"

<br />

### 🧑‍💻 Native image demo

---

# Rappels "Native Image"

<br />

- Utiliser la dernière version
- Flag `-Ob` pour moins d'optimisation au build

---

# Migration vers Spring Native

1. 📚 Rappels "Native Images" en Java
1. **🏺️ _"Closed-world" assumption:_ en "vase clos"**
1. 🤔️ Réflexion
1. 🧑‍🔬 Tests
1. 📄 Resources et fichiers
1. 💻 Distribution: CPU et libc
1. 🏃 Performance
1. 🕵️ Librairies non supportées

---

# En "vase clos"

<br />

> Building a standalone binary with the native-image tool takes place under a “closed world assumption”. The `native-image` tool performs an analysis to see which classes, methods, and fields within your application are reachable and must be included in the native image. The analysis is static: it does not run your application. This means that all the bytecode in your application that can be called at run time must be known (observed and analyzed) at build time.

Source: https://www.graalvm.org/latest/reference-manual/native-image/

---

# En "vase clos": profils et conditions

Quelques limites

🚨️ Attention aux annotations:

- `@Profile`
- `@ConditionalOn...`

✅ Annotations sans problème:

- `@Value`
- `@Configuration`
- A peu près toutes les annotations Spring

---

# En "vase clos"

<br />

### 🧑‍💻 `@Profile` demo

---

# En "vase clos": profils et conditions

Quelques limites

🚨️ Attention aux annotations:

- `@Profile`
- `@ConditionalOn...`

✅ Annotations sans problème:

- `@Value`
- `@Configuration`
- A peu près toutes les annotations Spring

---

# En "vase clos": autres limites

<br />

> The analysis can determine some cases of dynamic class loading, but it cannot always exhaustively predict all usages of
> - the Java Native Interface (JNI)
> - Java Reflection
> - Dynamic Proxy objects
> - class path resource

Source: https://www.graalvm.org/latest/reference-manual/native-image/

---

# En "vase clos": Java Agents

<br />

Supporté, mais surtout build-time: `-J-javaagent:agent.jar`

Support experimental: https://www.graalvm.org/latest/reference-manual/native-image/metadata/ExperimentalAgentOptions/

---

# Migration vers Spring Native

1. 📚 Rappels "Native Images" en Java
1. 🏺️ _"Closed-world" assumption:_ en "vase clos"
1. **🤔️ Réflexion**
1. 🧑‍🔬 Tests
1. 📄 Resources et fichiers
1. 💻 Distribution: CPU et libc
1. 🏃 Performance
1. 🕵️ Librairies non supportées

---

# Réflexion

<br />

GraalVM supporte la réflexion, mais uniquement "pré-déclarée":

- AOT processing avec Spring
- GraalVM Metadata repository
- `reflect-config.json`

---

# Réflexion

<br />

### 🧑‍💻 Demo réflexion

---

# Réflexion

<br />

Points d'attention:

- Sérialisation
- Libs non compatibles
- Chaînes de caractères
- Class path scanning

Chez Spring: `@ImportRuntimeHints` + `@RegisterReflectionForBinding`

---

# Migration vers Spring Native

1. 📚 Rappels "Native Images" en Java
1. 🏺️ _"Closed-world" assumption:_ en "vase clos"
1. 🤔️ Réflexion
1. **🧑‍🔬 Tests**
1. 📄 Resources et fichiers
1. 💻 Distribution: CPU et libc
1. 🏃 Performance
1. 🕵️ Librairies non supportées

---

# Tests en natif

<br />

```
./gradlew nativeTest
```

Limitations:
- Mockito
- Testcontainers
- ...

---

# Tests en natif

<br />

### 🧑‍💻 Démo tests

---

# Migration vers Spring Native

1. 📚 Rappels "Native Images" en Java
1. 🏺️ _"Closed-world" assumption:_ en "vase clos"
1. 🤔️ Réflexion
1. 🧑‍🔬 Tests
1. **📄 Resources et fichiers**
1. 💻 Distribution: CPU et libc
1. 🏃 Performance
1. 🕵️ Librairies non supportées

---

# Migration vers Spring Native

1. 📚 Rappels "Native Images" en Java
1. 🏺️ _"Closed-world" assumption:_ en "vase clos"
1. 🤔️ Réflexion
1. 🧑‍🔬 Tests
1. 📄 Resources et fichiers
1. **💻 Distribution: CPU et libc**
1. 🏃 Performance
1. 🕵️ Librairies non supportées

---

# Distribution: architecture CPU

<br />

Pas de cross-compilation. Compiler sur l'architecutre cible.

Dépendances sur `libc`: différence entre `glibc` (e.g. Ubuntu) et `musl` (e.g. Alpine Linux)

Buildpacks à la rescousse!

---

# Distribution: architecture CPU

<br />

### 🧑‍💻 Démo distribution

---

# Distribution: architecture CPU

<br />

tl;dr:

- Compiler sur l'architecture cible
- Buildpacks!

---

# Migration vers Spring Native

1. 📚 Rappels "Native Images" en Java
1. 🏺️ _"Closed-world" assumption:_ en "vase clos"
1. 🤔️ Réflexion
1. 🧑‍🔬 Tests
1. 📄 Resources et fichiers
1. 💻 Distribution: CPU et libc
1. **🏃 Performance**
1. 🕵️ Librairies non supportées

---

# Performance

<br />

### 🧑‍💻 Démo performance

---

# Performance

<br />

- Démarrage rapide
- Peak performance instantané
- Moins gourmand en resources
- Scaling inférieur à JVM

---

# Migration vers Spring Native

1. 📚 Rappels "Native Images" en Java
1. 🏺️ _"Closed-world" assumption:_ en "vase clos"
1. 🤔️ Réflexion
1. 🧑‍🔬 Tests
1. 📄 Resources et fichiers
1. 💻 Distribution: CPU et libc
1. 🏃 Performance
1. **🕵️ Librairies non supportées**

---

Repo:
- <logos-github-icon /> https://github.com/Kehrlann/spring-boot-native

Reach out:
- <logos-mastodon-icon /> @Kehrlann@hachyderm.io
- <logos-twitter /> @Kehrlann
- <fluent-emoji-flat-envelope-with-arrow /> daniel.garnier-moiroux@broadcom.com

---
layout: image-right
image: /qr-code.png
---

# 🤔 Questions?

<br>

Feedback 👉👉

Ça m'aide beaucoup! 🙇

