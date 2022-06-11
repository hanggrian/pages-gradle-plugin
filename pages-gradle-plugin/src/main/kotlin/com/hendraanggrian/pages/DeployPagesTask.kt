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
    override val webpagesMap: MapProperty<String, Document> = project.objects.mapProperty()

    @OutputDirectory
    override val outputDirectory: DirectoryProperty = project.objects.directoryProperty()

    private val transformer: Transformer = TransformerFactory.newInstance().newTransformer()

    @TaskAction
    fun deploy() {
        check(webpagesMap.get().isNotEmpty()) { "No pages to write" }
        webpagesMap.get().forEach { (filename, document) ->
            logger.info("Writing '$filename'...")
            transformer.transform(
                DOMSource(document),
                StreamResult(FileWriter(outputDirectory.asFile.get().resolve(filename)))
            )
        }
        logger.info("Done")
    }
}
