import com.hendraanggrian.pages.PagesExtension

buildscript {
    repositories.gradlePluginPortal()
    dependencies.classpath(plugs.pages) {
        capability("pages-minimal")
    }
}

plugins.apply("com.hendraanggrian.pages")

plugins {
    alias(plugs.plugins.git.publish)
}

extensions.configure<PagesExtension> {
    minimal {
        authorName = DEVELOPER_NAME
        authorUrl = DEVELOPER_URL
        projectName = RELEASE_ARTIFACT
        projectDescription = RELEASE_DESCRIPTION
        projectUrl = RELEASE_URL
        markdownFile = rootDir.resolve("docs/README.md")
        headerButtons {
            button("View", "Documentation", "dokka")
        }
    }
}

gitPublish {
    repoUri.set("git@github.com:$DEVELOPER_ID/$RELEASE_ARTIFACT.git")
    branch.set("gh-pages")
    contents.from(extensions.getByType<PagesExtension>().outputDirectory, "$rootDir/$RELEASE_ARTIFACT/build/dokka")
    contents.from("$rootDir/docs").exclude("README.md")
}

tasks {
    register(LifecycleBasePlugin.CLEAN_TASK_NAME) {
        delete(buildDir)
    }
    gitPublishCopy {
        dependsOn(":deployPages", ":$RELEASE_ARTIFACT:dokkaHtml")
    }
}
