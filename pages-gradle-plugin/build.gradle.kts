plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    alias(plugs.plugins.kotlin.jvm)
    alias(plugs.plugins.dokka)
    alias(plugs.plugins.spotless)
    alias(plugs.plugins.gradle.publish)
}

java.registerFeature("minimal") {
    usingSourceSet(sourceSets.main.get())
    capability(RELEASE_GROUP, "pages-minimal", RELEASE_VERSION)
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
    (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(sdk.versions.jdk.get()))
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

val minimalImplementation by configurations.getting

dependencies {
    minimalImplementation(libs.kotlinx.html.jvm)
    minimalImplementation(libs.commonmark.ext.gfm.tables)
    testImplementation(gradleTestKit())
    testImplementation(testLibs.kotlin.junit)
    testImplementation(testLibs.truth)
}

tasks.dokkaHtml {
    outputDirectory.set(buildDir.resolve("dokka/dokka"))
}
