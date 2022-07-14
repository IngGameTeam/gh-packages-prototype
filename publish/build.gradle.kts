plugins {
    id("maven-publish")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            val prop = org.jetbrains.kotlin.konan.properties.Properties()
            val file = File(rootDir, "env.properties")
            if (file.exists()) prop.load(file.inputStream())

            val REPO = rootProject.name
            val OWNER = prop["owner"]?.toString()?: System.getenv("OWNER")

            url = uri("https://maven.pkg.github.com/$OWNER/$REPO")
            credentials {

                username = prop["gpr.user"]?.toString()?: System.getenv("USERNAME")
                password = prop["gpr.key"]?.toString()?: System.getenv("TOKEN")
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
