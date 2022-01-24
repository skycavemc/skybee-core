import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    id("com.github.johnrengelman.shadow") version "4.0.4"
}

group = "de.leonheuer.skycave"
version = "1.0.3-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
    jcenter()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.6.0")
    compileOnly("org.spigotmc:spigot-api:1.17-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("com.github.SkriptLang:Skript:2.5.3")
    compileOnly("net.luckperms:api:5.3")
}

tasks {
    test {
        useJUnit()
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveBaseName.set(project.name)
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "de.leonheuer.skycave.skybeecore.SkyBeeCore"))
        }
        dependencies {
            exclude(dependency("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT"))
            exclude(dependency("com.github.MilkBowl:VaultAPI:1.7"))
            exclude(dependency("net.luckperms:api:5.3"))
            exclude(dependency("com.github.SkriptLang:Skript:2.5.3"))
        }
    }

    build {
        dependsOn(shadowJar)
    }
}