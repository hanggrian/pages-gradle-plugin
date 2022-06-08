package com.hendraanggrian.pages

import com.hendraanggrian.pages.internal.DefaultPages
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.register

/** Gradle plugin to create minimalistic webpage. */
open class PagesPlugin : Plugin<Project> {
    companion object {
        const val GROUP = "publishing"
        const val TASK_DEPLOY_RESOURCES = "deployResources"
        const val TASK_DEPLOY_PAGES = "deployPages"
    }

    override fun apply(project: Project) {
        val pages = project.extensions.create(Pages::class, "pages", DefaultPages::class, project)

        val deployResources = project.tasks.register<DeployResourcesTask>(TASK_DEPLOY_RESOURCES) {
            group = GROUP
            description = "Write images, styles and scripts directories for minimal website."
        }
        val deployWebpages = project.tasks.register<DeployPagesTask>(TASK_DEPLOY_PAGES) {
            group = GROUP
            description = "Write HTML files for minimal website."
            dependsOn(deployResources)
        }

        project.afterEvaluate {
            deployResources {
                resourcesMap.convention(pages.resourcesMap)
                outputDirectory.convention(pages.outputDirectory)
            }
            deployWebpages {
                webpagesMap.convention(pages.webpagesMap)
                outputDirectory.convention(pages.outputDirectory)
            }
        }
    }
}
