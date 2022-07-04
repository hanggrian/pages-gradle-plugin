package com.hendraanggrian.pages

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.mapProperty
import org.gradle.kotlin.dsl.setProperty
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

/** Task to run when `deployResources` command is executed. */
open class DeployResourcesTask : DefaultTask(), DeployResourcesSpec {
    @Input
    final override val staticResourcesSet: SetProperty<String> = project.objects.setProperty()

    @Input
    final override val dynamicResourcesMap: MapProperty<Pair<String, String>, String> = project.objects.mapProperty()

    @OutputDirectory
    final override val outputDirectory: DirectoryProperty = project.objects.directoryProperty()

    @TaskAction
    fun deploy() {
        check(staticResourcesSet.get().isNotEmpty() || dynamicResourcesMap.get().isNotEmpty()) {
            "No resources to write"
        }
        val outputDir = outputDirectory.asFile.get()

        logger.info("Copying resources:")
        staticResourcesSet.get().forEach { filepath ->
            val filepathWithoutRoot = filepath.substringAfter('/')
            logger.info("  $filepathWithoutRoot")
            val targetFile = outputDir.resolve(filepathWithoutRoot)
            targetFile.parentFile.prepare()
            Files.copy(
                javaClass.getResourceAsStream("/$filepath")!!,
                targetFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING
            )
        }

        logger.info("Writing resources:")
        dynamicResourcesMap.get().forEach { (file, content) ->
            val dirname = file.first
            val filename = file.second
            logger.info("  $dirname/$filename")
            val targetDir = outputDir.resolve(dirname)
            targetDir.prepare()
            targetDir.resolve(filename).writeText(content)
        }
    }

    private fun File.prepare() {
        if (!exists()) mkdir()
    }
}
