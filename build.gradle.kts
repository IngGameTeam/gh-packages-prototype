buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
        val kotlinVersion = "1.6.10"
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(kotlin("serialization", version = kotlinVersion))
    }
}
val kotlin_version = "1.6.10"
plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
}

version = rootProject.properties["version"].toString()

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "com.github.johnrengelman.shadow")

    tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        archiveFileName.set("${project.name}.jar")
    }

    repositories {
        mavenCentral()
        maven("https://repo.dmulloy2.net/repository/public/")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/public/")
        maven("https://jitpack.io")
        maven("https://libraries.minecraft.net/")
        maven("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
        maven("https://raw.githubusercontent.com/TheBlackEntity/PlugMan/repository/")

    }

    dependencies {

//        testImplementation(kotlin("test"))
//        testImplementation("com.github.seeseemelk:MockBukkit-v1.18:2.22.2")
//        testImplementation("org.slf4j:slf4j-api:1.7.36")
//        testImplementation("org.slf4j:slf4j-simple:1.7.36")
//        compileOnly(kotlin("test"))
//        implementation("io.github.brucefreedy:mccommand:1.0.3")
//        compileOnly("io.github.monun:invfx-api:3.1.0")
        compileOnly("net.kyori:adventure-api:4.10.1")
//        compileOnly("com.mojang:authlib:1.5.21")
        compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")
//        compileOnly("org.spigotmc:spigot:1.18.2-R0.1-SNAPSHOT")
//        compileOnly("com.eatthepath:fast-uuid:0.2.0")
//        compileOnly("net.jafama:jafama:2.3.2")
        compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
//        compileOnly("io.github.inggameteam:inggame-modules:1.1.7")
//        compileOnly("com.github.MilkBowl:VaultAPI:1.7")
//        compileOnly("com.rylinaux:PlugMan:2.2.9")
//        compileOnly("org.mongodb:mongodb-driver-sync:4.6.0")
//        compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")

    }

}

fun childTree(p: Project) {
    p.childProjects.values.forEach {
        it.apply {
            if (version == "unspecified") {
                version = rootProject.version
            }
        }
        var parentProj = it.parent
        while (parentProj !== null && parentProj !== rootProject) {
            parentProj.dependencies.apply {
                api(it)
                testApi(it)
            }
            parentProj = parentProj.parent
        }
        childTree(it)
    }
}
childTree(rootProject)