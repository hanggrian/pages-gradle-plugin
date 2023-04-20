package com.hendraanggrian.pages.minimal

import com.hendraanggrian.pages.PageButton
import com.hendraanggrian.pages.PagesConfigurationDsl

/**
 * Minimal theme configuration See [minimal-theme](https://github.com/hendraanggrian/minimal-theme/)
 * for more information/.
 */
@PagesConfigurationDsl
interface MinimalPagesOptions {
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

    /**
     * Author full name in title and footer. If left empty, corresponding tag in footer is removed
     * but title will still show project name.
     */
    var authorName: String?

    /**
     * Author website url in footer. If left empty, author information in footer will not be
     * clickable.
     */
    var authorUrl: String?

    /** Project full name in header. If left empty, module name will be used. */
    var projectName: String

    /** Project description in header. If left empty, corresponding tag in header is removed. */
    var projectDescription: String?

    /** Project website url in header. If left empty, corresponding tag in header is removed. */
    var projectUrl: String?

    /**
     * Add header button, capped at 3.
     *
     * @param text button text.
     * @param url to redirect on button click.
     */
    fun button(text: String, url: String)

    /** Small theme credit in footer. Enabled by default. */
    var footerCredit: Boolean
}

internal class MinimalPagesOptionsImpl(override var projectName: String) : MinimalPagesOptions {
    override var accentColor: String = "#448aff"
    override var accentLightHoverColor: String = "#005ecb"
    override var accentDarkHoverColor: String = "#83b9ff"
    override var authorName: String? = null
    override var authorUrl: String? = null
    override var projectDescription: String? = null
    override var projectUrl: String? = null
    override var footerCredit: Boolean = true

    internal val buttons = mutableListOf<PageButton>()
    override fun button(text: String, url: String) {
        check(buttons.size < 3) { "Minimal buttons are capped at 3." }
        buttons += PageButton(text, url)
    }
}
