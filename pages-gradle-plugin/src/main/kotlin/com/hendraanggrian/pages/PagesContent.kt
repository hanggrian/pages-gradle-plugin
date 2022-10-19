package com.hendraanggrian.pages

import org.gradle.api.Project
import java.io.File

/** Map markdown path to HTML filename with this interface. */
interface PagesContent {
    /**
     * Creates webpage file from markdown file. The destination is evaluated as
     * per [org.gradle.api.Project.file].
     */
    fun add(markdownPath: Any, htmlName: String)

    /**
     * Creates `index.html` from markdown file. The destination is evaluated as
     * per [org.gradle.api.Project.file]. Default is `README.md` on root, `docs` or `.github`
     * directory.
     */
    fun index(markdownPath: Any): Unit = add(markdownPath, "index.html")
}

internal class PagesContentImpl(private val project: Project) : PagesContent {
    internal val contents = mutableMapOf<String, File>()

    init {
        var readme = project.rootDir.resolve("README.md")
        if (readme.exists()) {
            index(readme)
        } else {
            readme = project.rootDir.resolve("docs/README.md")
            if (readme.exists()) {
                index(readme)
            } else {
                readme = project.rootDir.resolve(".github/README.md")
                if (readme.exists()) {
                    index(readme)
                }
            }
        }
    }

    override fun add(markdownPath: Any, htmlName: String) {
        contents[htmlName] = project.file(markdownPath)
    }
}
