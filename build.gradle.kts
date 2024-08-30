plugins {
    id("com.gradle.plugin-publish") version "1.2.2"
}

group = "dev.redstudio"
version = "1.0"

gradlePlugin {
    website = "https://github.com/Red-Studio-Ragnarok/Gradle-Embeder"
    vcsUrl = "https://github.com/Red-Studio-Ragnarok/Gradle-Embeder.git"

    plugins.create("gradleEmbeder") {
        id = "dev.redstudio.gradleembeder"
        implementationClass = "dev.redstudio.gradleembeder.GradleEmbeder"
        displayName = "Gradle Embeder"
        description = "A simple Gradle plugin that adds an embed configuration allowing you to embed a dependency in your jar."
        tags = listOf("embed", "dependency", "jar")
    }
}
