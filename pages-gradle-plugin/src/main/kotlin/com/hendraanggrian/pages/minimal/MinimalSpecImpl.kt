package com.hendraanggrian.pages.minimal

import org.gradle.api.Action
import org.gradle.kotlin.dsl.invoke
import java.io.File

internal class MinimalSpecImpl(override var projectName: String) : MinimalSpec, MinimalButtonsScope {

    override var accentColor: String = "#448aff"

    override var accentLightHoverColor: String = "#005ecb"

    override var accentDarkHoverColor: String = "#83b9ff"

    override var icon: String? = null

    override var authorName: String? = null

    override var authorUrl: String? = null

    override var projectDescription: String? = null

    override var projectUrl: String? = null

    override var markdownFile: File? = null

    override fun headerButtons(action: Action<MinimalButtonsScope>) = action(this)

    override var footerCredit: Boolean = true

    override fun button(line1: String, line2: String, url: String) {
        if (headerButtons.size >= 3) {
            error("Header buttons are capped at 3")
        }
        headerButtons += MinimalButton(line1, line2, url)
    }

    internal val headerButtons: MutableCollection<MinimalButton> = mutableListOf()
}
