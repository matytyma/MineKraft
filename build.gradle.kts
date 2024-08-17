plugins {
    kotlin("jvm") version "2.0.0"
    `maven-publish`
}

group = "dev.matytyma.minekraft"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://libraries.minecraft.net")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")
    compileOnly("com.mojang:brigadier:1.0.18")
}

kotlin {
    jvmToolchain(21)
}
