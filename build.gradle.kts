plugins {
    kotlin("jvm") version "2.0.0"
}

group = "dev.matytyma.minekraft"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")
}

kotlin {
    jvmToolchain(21)
}
