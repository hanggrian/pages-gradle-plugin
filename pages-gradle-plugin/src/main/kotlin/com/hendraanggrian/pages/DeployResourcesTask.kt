package com.hendraanggrian.pages

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.mapProperty

/** Task to run when `deployResources` command is executed. */
open class DeployResourcesTask : DefaultTask(), ResourcesDeployment {

    @Input
    override val resourcesMap: MapProperty<String, String> = project.objects.mapProperty<String, String>()
        .convention(mapOf())

    @OutputDirectory
    override val outputDirectory: DirectoryProperty = project.objects.directoryProperty()

    init {
        outputs.upToDateWhen { false } // always consider this task out of date
    }

    @TaskAction
    fun deploy() {
        check(resourcesMap.get().isNotEmpty()) { "No pages to write" }
        resourcesMap.get().forEach { (filepath, content) ->
            logger.info("Writing '$filepath'...")
            val dirname = filepath.substringBefore('/')
            val filename = filepath.substringAfter('/')
            val targetDir = outputDirectory.asFile.get().resolve(dirname)
            if (!targetDir.exists()) {
                targetDir.mkdir()
            }
            targetDir.resolve(filename).writeText(content)
        }
        logger.info("Done")
    }
}
