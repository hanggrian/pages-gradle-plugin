plugins {
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.dokka)
    alias(libs.plugins.gradle.publish)
}

kotlin.jvmToolchain(libs.versions.jdk.get().toInt())

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
    ktlint(libs.ktlint, ::configureKtlint)
    ktlint(libs.rulebook.ktlint)
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
