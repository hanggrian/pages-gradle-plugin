package com.hendraanggrian.pages

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.MapProperty
import org.w3c.dom.Document

/** A specification for generating website HTMLs. */
interface DeployPagesSpec {
    /** Resources mapping with filepath as key and content as value. */
    val resourcesMap: MapProperty<Pair<String, String>, String>

    /** Webpages mapping with filename as key and content as value. */
    val webpagesMap: MapProperty<String, Document>

    /**
     * Output directory of webpages and resources.
     * Default is `$projectDir/build/pages`.
     */
    val outputDirectory: DirectoryProperty
}
