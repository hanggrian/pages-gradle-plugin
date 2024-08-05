package com.hanggrian.pages.minimal

import com.hanggrian.pages.PageOptions
import com.hanggrian.pages.PagesConfigurationDsl

/**
 * Theme configuration for [minimal-dark-theme](https://github.com/hanggrian/minimal-dark-theme/).
 */
@PagesConfigurationDsl
public interface MinimalOptions : PageOptions {
    /** Primary color, default is #448aff. */
    public var colorPrimary: String

    /** Primary container color for light mode, default is #005ecb. */
    public var colorPrimaryContainer: String

    /** Primary container color for dark mode, default is #83b9ff. */
    public var colorPrimaryContainer2: String
}

internal class MinimalOptionsImpl(override var projectName: String) : MinimalOptions {
    override var colorPrimary = "#448aff"
    override var colorPrimaryContainer = "#005ecb"
    override var colorPrimaryContainer2 = "#83b9ff"

    override var authorName: String? = null
    override var authorUrl: String? = null
    override var projectDescription: String? = null
    override var projectUrl: String? = null
    override var isFooterCredit: Boolean = true

    internal val buttons = mutableMapOf<String, String>()

    override fun button(text: String, url: String) {
        if (buttons.size == 3) {
            error("Theme can only handle 3 buttons.")
        }
        buttons += text to url
    }
}
