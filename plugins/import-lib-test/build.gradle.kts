



repositories {
    maven {
        val prop = org.jetbrains.kotlin.konan.properties.Properties()
        val file = File(rootDir, "env.properties")
        if (file.exists()) prop.load(file.inputStream())

        val OWNER = prop["owner"]?.toString()?: System.getenv("OWNER")
        val REPO = rootProject.name
        url = uri("https://maven.pkg.github.com/$OWNER/$REPO")
        credentials {

            username = prop["gpr.user"]?.toString()?: System.getenv("USERNAME")
            password = prop["gpr.key"]?.toString()?: System.getenv("TOKEN")
        }
    }
}

dependencies {
    compileOnly("io.github.inggameteam:gh-packages-prototype:1.0.0")
}