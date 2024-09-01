# MineKraft
An idiomatic Kotlin wrapper for the Paper Minecraft server API

## Installation
No releases are published to Maven Central yet, so you'll have to build the project yourself and publish it to your local Maven repository.
To do so, run the following command on Linux/macOS:
```shell
./gradlew build publishToMavenLocal
```
or the following command on Windows:
```shell
gradlew.bat build publishToMavenLocal
```

### Gradle (Kotlin DSL)
```kotlin
repositories {
    mavenLocal()
}

dependencies {
    implementation("dev.matytyma.minekraft:minekraft-api:{version}")
}
```

### Gradle (Groovy DSL)
```groovy
repositories {
    mavenLocal()
}

dependencies {
    implementation("dev.matytyma.minekraft:minekraft-api:{version}")
}
```

### Maven
```xml
<dependency>
    <groupId>dev.matytyma.minekraft</groupId>
    <artifactId>minekraft-api</artifactId>
    <version>{version}</version>
</dependency>
```

## Screenshots
![](https://github.com/user-attachments/assets/dfb01a8d-9718-4b43-b20b-7f3b4e54c2d5)
![](https://github.com/user-attachments/assets/b4215954-94dd-4b83-be12-24f739c89ca3)
