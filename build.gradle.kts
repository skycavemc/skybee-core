import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "de.leonheuer.skycave"
version = "1.0.7-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
    jcenter()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("com.github.SkriptLang:Skript:2.5.3")
    compileOnly("net.luckperms:api:5.3")
}

tasks {
    test {
        useJUnit()
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveBaseName.set(project.name)
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "de.leonheuer.skycave.skybeecore.SkyBeeCore"))
        }
        dependencies {
            exclude(dependency("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT"))
            exclude(dependency("com.github.MilkBowl:VaultAPI:1.7"))
            exclude(dependency("net.luckperms:api:5.3"))
            exclude(dependency("com.github.SkriptLang:Skript:2.5.3"))
        }
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}