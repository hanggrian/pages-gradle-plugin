package com.hendraanggrian.pages.minimal

import com.hendraanggrian.pages.PagesConfigurationDsl
import com.hendraanggrian.pages.internal.PageOptions

/**
 * Theme configuration for [minimal-dark-theme](https://github.com/hendraanggrian/minimal-dark-theme/).
 */
@PagesConfigurationDsl
interface MinimalOptions : PageOptions {
    /** Primary color, default is #448aff. */
    var colorPrimary: String

    /** Primary container color for light mode, default is #005ecb. */
    var colorPrimaryContainer: String

    /** Primary container color for dark mode, default is #83b9ff. */
    var colorPrimaryContainer2: String
}

internal class MinimalOptionsImpl(override var projectName: String) : MinimalOptions {
    override var colorPrimary = "#448aff"
    override var colorPrimaryContainer = "#005ecb"
    override var colorPrimaryContainer2 = "#83b9ff"

    override var authorName: String? = null
    override var authorUrl: String? = null
    override var projectDescription: String? = null
    override var projectUrl: String? = null
    override var footerCredit: Boolean = true

    internal val buttons = mutableMapOf<String, String>()
    override fun button(text: String, url: String) {
        buttons += text to url
    }
}
