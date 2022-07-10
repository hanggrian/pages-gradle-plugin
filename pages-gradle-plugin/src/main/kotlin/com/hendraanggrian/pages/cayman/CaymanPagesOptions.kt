package com.hendraanggrian.pages.cayman

import com.hendraanggrian.pages.PageButton
import com.hendraanggrian.pages.PagesConfigurationDsl

/**
 * Cayman theme configuration
 * See [cayman-theme](https://github.com/hendraanggrian/cayman-theme/) for more information/.
 */
@PagesConfigurationDsl
interface CaymanPagesOptions {
    /**
     * Use dark theme.
     * Default is light.
     */
    fun darkTheme()

    /**
     * Accent color of the webpage.
     * Default is material color `Blue A200`.
     */
    var accentColor: String

    /**
     * Primary color of the webpage, used as banner's gradient end and heading color.
     * Default is `#159957`.
     */
    var primaryColor: String

    /**
     * Secondary color of the webpage, used as banner's gradient end and heading color.
     * Default is `#155799`.
     */
    var secondaryColor: String

    /**
     * Author full name in title and footer.
     * If left empty, corresponding tag in footer is removed but title will still show project name.
     */
    var authorName: String?

    /**
     * Author website url in footer.
     * If left empty, author information in footer will not be clickable.
     */
    var authorUrl: String?

    /**
     * Project full name in header.
     * If left empty, module name will be used.
     */
    var projectName: String

    /**
     * Project description in header.
     * If left empty, corresponding tag in header is removed.
     */
    var projectDescription: String?

    /**
     * Project website url in header.
     * If left empty, corresponding tag in header is removed.
     */
    var projectUrl: String?

    /**
     * Add header button.
     * @param text button text.
     * @param url to redirect on button click.
     */
    fun button(text: String, url: String)

    /**
     * Small theme credit in footer.
     * Enabled by default.
     */
    var footerCredit: Boolean
}

internal class CaymanPagesOptionsImpl(override var projectName: String) : CaymanPagesOptions {
    override var accentColor: String = "#448aff"
    override var primaryColor: String = "#159957"
    override var secondaryColor: String = "#155799"
    override var authorName: String? = null
    override var authorUrl: String? = null
    override var projectDescription: String? = null
    override var projectUrl: String? = null
    override var footerCredit: Boolean = true

    internal var dark: Boolean = false
    override fun darkTheme() {
        dark = true
    }

    internal val buttons = mutableListOf<PageButton>()
    override fun button(text: String, url: String) {
        buttons += PageButton(text, url)
    }
}
