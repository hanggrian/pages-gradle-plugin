package com.hendraanggrian.pages

import com.hendraanggrian.pages.minimal.MinimalPagesOptions
import org.gradle.api.Action
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.MapProperty
import org.w3c.dom.Document

/** Extension instance when configuring `pages` in Gradle scripts. */
interface PagesExtension : DeployPagesSpec {
    /** Use minimal feature. */
    fun minimal(action: Action<MinimalPagesOptions>)
}

/** A specification for generating website HTMLs. */
interface DeployPagesSpec {
    /** Resources mapping with filepath as key and content as value. */
    val resourcesMap: MapProperty<String, String>

    /** Webpages mapping with filename as key and content as value. */
    val webpagesMap: MapProperty<String, Document>

    /**
     * Output directory of this task.
     * Default is `$projectDir/build/pages`.
     */
    val outputDirectory: DirectoryProperty
}
