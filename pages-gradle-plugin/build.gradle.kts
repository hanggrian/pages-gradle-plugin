import org.gradle.plugin.compatibility.compatibility
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val developerId: String by project
val releaseArtifact: String by project
val releaseGroup: String by project
val releaseDescription: String by project
val releaseUrl: String by project

val javaCompileVersion = JavaLanguageVersion.of(libs.versions.java.compile.get())
val javaSupportVersion = JavaVersion.toVersion(libs.versions.java.support.get())

plugins {
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.dokka)
    alias(libs.plugins.dokka.javadoc)
    alias(libs.plugins.gradle.publish)
}

java {
    toolchain.languageVersion.set(javaCompileVersion)
    sourceCompatibility = javaSupportVersion
    targetCompatibility = javaSupportVersion
}

kotlin {
    compilerOptions.jvmTarget.set(JvmTarget.fromTarget(javaSupportVersion.toString()))
    jvmToolchain {
        languageVersion.set(javaCompileVersion)
    }
    explicitApi()
}

@Suppress("UnstableApiUsage")
gradlePlugin {
    website.set(releaseUrl)
    vcsUrl.set("https://github.com/$developerId/$releaseArtifact.git")
    plugins.register("pagesPlugin") {
        id = releaseGroup
        implementationClass = "$releaseGroup.PagesPlugin"
        displayName = "Pages Plugin"
        description = releaseDescription
        tags.set(listOf("website", "github-pages"))
        compatibility {
            features.configurationCache = true
        }
    }
    testSourceSets(sourceSets.test.get())
}

dependencies {
    compileOnly(kotlin("gradle-plugin-api"))

    implementation(gradleKotlinDsl())
    implementation(libs.kotlinx.html.jvm)
    implementation(libs.commonmark.ext.gfm.tables)

    testImplementation(gradleTestKit())
    testImplementation(kotlin("test-junit", libs.versions.kotlin.get()))
    testImplementation(libs.bundles.junit4)
}

tasks.register<Copy>("deployPlugin") {
    dependsOn("build")
    from(layout.buildDirectory.file("libs/$releaseArtifact-${project.version}.jar"))
    into(rootProject.layout.projectDirectory)
    rename("(.+)-$version\\.jar", "$1-SNAPSHOT.jar")
}
