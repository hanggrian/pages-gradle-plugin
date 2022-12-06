plugins {
    `java-gradle-plugin`
    `kotlin-dsl` version libs.versions.gradle.kotlin.dsl
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.dokka)
    alias(libs.plugins.gradle.publish)
}

gradlePlugin {
    plugins.register("pagesPlugin") {
        id = "$RELEASE_GROUP.pages"
        implementationClass = "$id.PagesPlugin"
        displayName = "Pages Plugin"
        description = RELEASE_DESCRIPTION
    }
    testSourceSets(sourceSets.test.get())
}

kotlin.jvmToolchain(libs.versions.jdk.get().toInt())

pluginBundle {
    website = RELEASE_URL
    vcsUrl = "$RELEASE_URL.git"
    description = RELEASE_DESCRIPTION
    tags = listOf("website", "github-pages")
}

dependencies {
    ktlint(libs.ktlint, ::ktlintConfig)
    ktlint(libs.rulebook.ktlint)
    implementation(libs.kotlinx.html.jvm)
    implementation(libs.commonmark.ext.gfm.tables)
    testImplementation(gradleTestKit())
    testImplementation(kotlin("test-junit", libs.versions.kotlin.get()))
    testImplementation(libs.truth)
}

tasks.dokkaHtml {
    outputDirectory.set(buildDir.resolve("dokka/dokka/"))
}
