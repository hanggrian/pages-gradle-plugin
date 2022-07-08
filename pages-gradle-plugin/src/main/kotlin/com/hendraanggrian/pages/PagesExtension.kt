package com.hendraanggrian.pages

import com.hendraanggrian.pages.minimal.MinimalPagesOptions
import org.gradle.api.Action
import org.gradle.api.file.CopySpec
import org.gradle.api.file.DirectoryProperty
import org.gradle.kotlin.dsl.invoke

/** Extension instance when configuring `pages` in Gradle scripts. */
@PagesConfigurationDsl
interface PagesExtension {
    /** Custom resources that will be included in output directory. */
    val resources: CopySpec

    /** Configures resources. */
    fun resources(action: Action<in CopySpec>): Unit = action(resources)

    /** Webpages to generate from markdown path. */
    val contents: ContentBuilder

    /** Configures contents. */
    fun contents(action: Action<in ContentBuilder>): Unit = action(contents)

    /**
     * Output directory of webpages and resources.
     * Default is `$projectDir/build/pages`.
     */
    val outputDirectory: DirectoryProperty

    /** Configure `minimal` feature. */
    fun minimal(action: Action<in MinimalPagesOptions>)
}
