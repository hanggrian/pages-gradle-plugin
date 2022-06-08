package com.hendraanggrian.pages

import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import java.io.File
import java.io.IOException
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFails

class PagesFunctionalTest {
    @Rule @JvmField val testProjectDir = TemporaryFolder()
    private lateinit var buildFile: File
    private lateinit var runner: GradleRunner

    @BeforeTest
    @Throws(IOException::class)
    fun setup() {
        testProjectDir.newFile("settings.gradle.kts")
            .writeText("rootProject.name = \"pages-functional-test\"")
        buildFile = testProjectDir.newFile("build.gradle.kts")
        runner = GradleRunner.create()
            .withPluginClasspath()
            .withProjectDir(testProjectDir.root)
            .withTestKitDir(testProjectDir.newFolder())
    }

    @Test
    fun `Nothing to write`() {
        buildFile.writeText(
            """
            plugins {
                id("com.hendraanggrian.pages")
            }
            """.trimIndent()
        )
        assertFails {
            runner.withArguments(PagesPlugin.TASK_DEPLOY_RESOURCES).build()
                .task(":${PagesPlugin.TASK_DEPLOY_RESOURCES}")
        }
        assertFails {
            runner.withArguments(PagesPlugin.TASK_DEPLOY_PAGES).build()
                .task(":${PagesPlugin.TASK_DEPLOY_PAGES}")
        }
    }
}
