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
    override val resourcesMap: MapProperty<String, String> = project.objects.mapProperty<String, String>()
        .convention(mapOf())

    @Input
    override val webpagesMap: MapProperty<String, Document> = project.objects.mapProperty()

    @OutputDirectory
    override val outputDirectory: DirectoryProperty = project.objects.directoryProperty()

    private val transformer: Transformer = TransformerFactory.newInstance().newTransformer()

    @TaskAction
    fun deploy() {
        check(resourcesMap.get().isNotEmpty() && webpagesMap.get().isNotEmpty()) { "Nothing to write" }

        logger.info("Writing resources:")
        resourcesMap.get().forEach { (filepath, content) ->
            logger.info("  $filepath")
            val dirname = filepath.substringBefore('/')
            val filename = filepath.substringAfter('/')
            val targetDir = outputDirectory.asFile.get().resolve(dirname)
            if (!targetDir.exists()) {
                targetDir.mkdir()
            }
            targetDir.resolve(filename).writeText(content)
        }

        logger.info("Writing pages:")
        webpagesMap.get().forEach { (filename, document) ->
            logger.info("  $filename")
            transformer.transform(
                DOMSource(document),
                StreamResult(FileWriter(outputDirectory.asFile.get().resolve(filename)))
            )
        }
    }
}
