package com.hendraanggrian.pages

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.mapProperty
import org.w3c.dom.Document
import java.io.FileWriter
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/** Task to run when `deployPages` command is executed. */
open class DeployPagesTask : DefaultTask(), DeployPagesSpec {
    @Input
    final override val resourcesMap: MapProperty<Pair<String, String>, String> = project.objects.mapProperty()

    @Input
    final override val webpagesMap: MapProperty<String, Document> = project.objects.mapProperty()

    @OutputDirectory
    final override val outputDirectory: DirectoryProperty = project.objects.directoryProperty()

    private val transformer: Transformer = TransformerFactory.newInstance().newTransformer()

    @TaskAction
    fun deploy() {
        check(resourcesMap.get().isNotEmpty() && webpagesMap.get().isNotEmpty()) { "Nothing to write" }
        val outputDir = outputDirectory.asFile.get()

        logger.info("Writing resources:")
        resourcesMap.get().forEach { (file, content) ->
            val dirname = file.first
            val filename = file.second
            logger.info("  $dirname/$filename")
            val targetDir = outputDir.resolve(dirname)
            if (!targetDir.exists()) {
                targetDir.mkdir()
            }
            targetDir.resolve(filename).writeText(content)
        }

        logger.info("Writing pages:")
        webpagesMap.get().forEach { (filename, document) ->
            logger.info("  $filename")
            val file = outputDir.resolve(filename)
            transformer.transform(DOMSource(document), StreamResult(FileWriter(file)))
            file.writeText(file.readText().fixFencedCodeBlock())
        }
    }

    /**
     * kotlinx.html automatically add indent to commonmark's result.
     * This fix reverses that behavior.
     */
    private fun String.fixFencedCodeBlock(): String =
        replace("<pre>\n                    <code", "<pre><code")
            .replace("</code>\n                </pre>", "</code></pre>")
}
