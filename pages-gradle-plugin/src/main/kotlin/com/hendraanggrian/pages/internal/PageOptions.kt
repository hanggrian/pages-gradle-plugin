package com.hendraanggrian.pages.internal

interface PageOptions {
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

    /** Small theme credit in footer. Enabled by default. */
    var footerCredit: Boolean

    /**
     * Add header button.
     *
     * @param text button text.
     * @param url to redirect on button click.
     */
    fun button(text: String, url: String)
}
