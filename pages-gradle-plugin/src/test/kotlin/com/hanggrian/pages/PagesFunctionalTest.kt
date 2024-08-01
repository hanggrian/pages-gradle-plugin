package com.hanggrian.pages

import com.hanggrian.pages.PagesPlugin.Companion.TASK_DEPLOY_PAGES
import org.gradle.language.base.plugins.LifecycleBasePlugin.CHECK_TASK_NAME
import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import java.io.File
import java.io.IOException
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFails

class PagesFunctionalTest {
    @Rule @JvmField
    val testProjectDir = TemporaryFolder()
    private lateinit var buildFile: File
    private lateinit var runner: GradleRunner

    @BeforeTest
    @Throws(IOException::class)
    fun setup() {
        testProjectDir
            .newFile("settings.gradle.kts")
            .writeText("rootProject.name = \"pages-functional-test\"")
        buildFile = testProjectDir.newFile("build.gradle.kts")
        runner =
            GradleRunner
                .create()
                .withPluginClasspath()
                .withProjectDir(testProjectDir.root)
                .withTestKitDir(testProjectDir.newFolder())
    }

    @Test
    fun nothingToWrite() {
        buildFile
            .writeText(
                """
                plugins {
                    id("com.hanggrian.pages")
                }
                """.trimIndent(),
            )
        assertFails {
            runner
                .withArguments(TASK_DEPLOY_PAGES)
                .build()
                .task(":$TASK_DEPLOY_PAGES")
        }
    }

    @Test
    fun multipleThemes() {
        buildFile
            .writeText(
                """
                plugins {
                    id("com.hanggrian.pages")
                    pages {
                        minimal { }
                        cayman { }
                    }
                }
                """.trimIndent(),
            )
        assertFails {
            runner
                .withArguments(CHECK_TASK_NAME)
                .build()
                .task(":$CHECK_TASK_NAME")
        }
    }
}
