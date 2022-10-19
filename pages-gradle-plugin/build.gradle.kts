plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.dokka)
    alias(libs.plugins.spotless)
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

kotlin.jvmToolchain {
    languageVersion.set(JavaLanguageVersion.of(libs.versions.jdk.get()))
}

spotless.kotlin {
    ktlint()
}

pluginBundle {
    website = RELEASE_URL
    vcsUrl = "$RELEASE_URL.git"
    description = RELEASE_DESCRIPTION
    tags = listOf("website", "github-pages")
}

dependencies {
    implementation(libs.kotlinx.html.jvm)
    implementation(libs.commonmark.ext.gfm.tables)
    testImplementation(gradleTestKit())
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.truth)
}

tasks.dokkaHtml {
    outputDirectory.set(buildDir.resolve("dokka/dokka"))
}
