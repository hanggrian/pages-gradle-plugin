plugins {
    pages
    `git-publish`
}

pages {
    minimal {
        authorName = "Hendra Anggrian"
        authorUrl = "https://github.com/hendraanggrian"
        projectName = RELEASE_ARTIFACT
        projectDescription = RELEASE_DESCRIPTION
        projectUrl = RELEASE_GITHUB
        markdownFile = rootDir.resolve("docs/README.md")
        headerButtons {
            button("View", "Documentation", "dokka")
        }
    }
}

gitPublish {
    repoUri.set("git@github.com:hendraanggrian/$RELEASE_ARTIFACT.git")
    branch.set("gh-pages")
    contents.from(pages.outputDirectory, "$rootDir/$RELEASE_ARTIFACT/build/dokka")
}

tasks {
    register("clean") {
        delete(buildDir)
    }
    gitPublishCopy {
        dependsOn(":$RELEASE_ARTIFACT:dokkaHtml")
    }
}