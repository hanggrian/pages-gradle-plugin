package com.hendraanggrian.pages

/** Map markdown path to HTML filename with this interface. */
interface ContentBuilder {

    /**
     * Creates webpage file from markdown file.
     * The destination is evaluated as per [org.gradle.api.Project.file].
     */
    fun add(markdownPath: Any, htmlName: String)

    /**
     * Creates `index.html` from markdown file.
     * The destination is evaluated as per [org.gradle.api.Project.file].
     */
    fun index(markdownPath: Any): Unit = add(markdownPath, "index.html")
}
