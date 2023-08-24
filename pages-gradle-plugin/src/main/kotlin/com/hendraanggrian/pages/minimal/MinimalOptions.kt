package com.hendraanggrian.pages.minimal

import com.hendraanggrian.pages.PagesConfigurationDsl
import com.hendraanggrian.pages.internal.PageOptions

/**
 * Theme configuration for [minimal-dark-theme](https://github.com/hendraanggrian/minimal-dark-theme/).
 */
@PagesConfigurationDsl
interface MinimalOptions : PageOptions {
    /** Accent color of the webpage, used as button color. Default is material color `Blue A200`. */
    var accentColor: String

    /**
     * Accent color of the webpage, used as button color when hovered in light theme. Default is
     * material color `Blue A200 Dark`.
     */
    var accentLightHoverColor: String

    /**
     * Accent color of the webpage, used as button color when hovered in dark theme. Default is
     * material color `Blue A200 Light`.
     */
    var accentDarkHoverColor: String
}

internal class MinimalOptionsImpl(override var projectName: String) : MinimalOptions {
    internal val buttons = mutableMapOf<String, String>()

    override var accentColor: String = "#448aff"
    override var accentLightHoverColor: String = "#005ecb"
    override var accentDarkHoverColor: String = "#83b9ff"
    override var authorName: String? = null
    override var authorUrl: String? = null
    override var projectDescription: String? = null
    override var projectUrl: String? = null
    override var footerCredit: Boolean = true
    override fun button(text: String, url: String) {
        buttons += text to url
    }
}
