import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val developerId: String by project
val releaseArtifact: String by project
val releaseGroup: String by project
val releaseDescription: String by project
val releaseUrl: String by project

val jdkVersion = JavaLanguageVersion.of(libs.versions.jdk.get())
val jreVersion = JavaLanguageVersion.of(libs.versions.jre.get())

plugins {
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.dokka)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.gradle.publish)
}

kotlin {
    jvmToolchain(jdkVersion.asInt())
    explicitApi()
}

ktlint.version.set(libs.versions.ktlint.get())

gradlePlugin {
    website.set(releaseUrl)
    vcsUrl.set("https://github.com/$developerId/$releaseArtifact.git")
    plugins.register("pagesPlugin") {
        id = releaseGroup
        implementationClass = "$releaseGroup.PagesPlugin"
        displayName = "Pages Plugin"
        description = releaseDescription
        tags.set(listOf("website", "github-pages"))
    }
    testSourceSets(sourceSets.test.get())
}

dependencies {
    ktlintRuleset(libs.rulebook.ktlint)

    compileOnly(kotlin("gradle-plugin-api"))

    implementation(gradleKotlinDsl())
    implementation(libs.kotlinx.html.jvm)
    implementation(libs.commonmark.ext.gfm.tables)

    testImplementation(gradleTestKit())
    testImplementation(kotlin("test-junit", libs.versions.kotlin.get()))
    testImplementation(libs.truth)
}

tasks {
    withType<JavaCompile> {
        options.release = jreVersion.asInt()
    }
    withType<KotlinCompile> {
        compilerOptions.jvmTarget
            .set(JvmTarget.fromTarget(JavaVersion.toVersion(jreVersion).toString()))
    }

    dokkaHtml {
        outputDirectory.set(layout.buildDirectory.dir("dokka/dokka/"))
    }
}
