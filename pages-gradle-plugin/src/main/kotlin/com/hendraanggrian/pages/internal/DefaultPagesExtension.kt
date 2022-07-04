package com.hendraanggrian.pages.internal

import com.hendraanggrian.pages.PagesExtension
import com.hendraanggrian.pages.minimal.MinimalPagesOptions
import com.hendraanggrian.pages.minimal.MinimalPagesOptionsImpl
import com.hendraanggrian.pages.minimal.MinimalWriter
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.gradle.api.Action
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.ProjectLayout
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.SetProperty
import org.gradle.kotlin.dsl.mapProperty
import org.gradle.kotlin.dsl.setProperty
import org.w3c.dom.Document

open class DefaultPagesExtension(objects: ObjectFactory, layout: ProjectLayout, private val projectName: String) :
    PagesExtension {

    final override val staticResourcesSet: SetProperty<String> = objects.setProperty<String>()
        .convention(emptySet())

    final override val dynamicResourcesMap: MapProperty<Pair<String, String>, String> =
        objects.mapProperty<Pair<String, String>, String>().convention(emptyMap())

    final override val pagesMap: MapProperty<String, Document> = objects.mapProperty<String, Document>()
        .convention(emptyMap())

    final override val outputDirectory: DirectoryProperty = objects.directoryProperty()
        .convention(layout.buildDirectory.dir("pages"))

    private val extensions = listOf(TablesExtension.create())
    private val htmlRenderer = HtmlRenderer.builder().extensions(extensions).build()
    private val parser = Parser.builder().extensions(extensions).build()

    final override fun minimal(action: Action<in MinimalPagesOptions>) {
        val options = MinimalPagesOptionsImpl(projectName)
        action.execute(options)
        checkNotNull(options.markdownFile) { "markdownFile cannot be empty" }
        staticResourcesSet.add("minimal/scripts/scale.fix.js")
        staticResourcesSet.add("minimal/scripts/theme.js")
        staticResourcesSet.add("minimal/styles/pygment_trac.css")
        dynamicResourcesMap.put(
            "styles" to "main.css",
            MinimalWriter.getMainCss(
                options.accentColor,
                options.accentLightHoverColor,
                options.accentDarkHoverColor,
                options.headerButtons.size
            )
        )
        pagesMap.put("index.html", MinimalWriter.index(options, htmlRenderer, parser))
    }
}
