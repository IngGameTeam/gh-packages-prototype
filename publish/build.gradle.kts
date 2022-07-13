plugins {
    id("maven-publish")
}

val OWNER = "IngGameTeam"
val REPO = rootProject.name
publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/$OWNER/$REPO")
            credentials {
                username = project.findProperty("gpr.user")?.toString() ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key")?.toString() ?: System.getenv("TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>(rootProject.name) {
            groupId = rootProject.properties["group"]?.toString()!!
            artifactId = rootProject.name
            version = rootProject.version.toString()
            artifact(tasks["shadowJar"])
        }
    }
}

tasks.register("sourceJar", Jar::class) {
        classifier = "sources"
        from(sourceSets.main)
    }
tasks.register("packageJavadoc", Jar::class) {
    from(tasks["javadoc"])
    classifier = "javadoc"
}
