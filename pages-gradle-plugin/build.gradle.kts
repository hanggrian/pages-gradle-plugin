val RELEASE_GROUP: String by project
val RELEASE_DESCRIPTION: String by project
val RELEASE_URL: String by project

plugins {
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.dokka)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.gradle.publish)
}

kotlin.jvmToolchain(libs.versions.jdk.get().toInt())

ktlint.version.set(libs.versions.ktlint.get())

gradlePlugin {
    website.set(RELEASE_URL)
    vcsUrl.set("$RELEASE_URL.git")
    plugins.register("pagesPlugin") {
        id = RELEASE_GROUP
        displayName = "Pages Plugin"
        description = RELEASE_DESCRIPTION
        tags.set(listOf("website", "github-pages"))
        implementationClass = "$RELEASE_GROUP.PagesPlugin"
    }
    testSourceSets(sourceSets.test.get())
}

dependencies {
    ktlintRuleset(libs.ktlint)
    ktlintRuleset(libs.rulebook.ktlint)

    compileOnly(kotlin("gradle-plugin-api"))

    implementation(gradleKotlinDsl())
    implementation(libs.kotlinx.html.jvm)
    implementation(libs.commonmark.ext.gfm.tables)

    testImplementation(gradleTestKit())
    testImplementation(kotlin("test-junit", libs.versions.kotlin.get()))
    testImplementation(libs.truth)
}

tasks.dokkaHtml {
    outputDirectory.set(buildDir.resolve("dokka/dokka/"))
}
