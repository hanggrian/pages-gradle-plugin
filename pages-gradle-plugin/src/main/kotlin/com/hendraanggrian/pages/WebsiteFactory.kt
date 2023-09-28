package com.hendraanggrian.pages

import kotlinx.html.BODY
import kotlinx.html.HEAD
import kotlinx.html.body
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.meta
import org.w3c.dom.Document

internal abstract class WebsiteFactory(extension: PagesExtension) : PagesExtension by extension {
    abstract fun HEAD.onCreateHead()
    abstract fun BODY.onCreateBody(content: String)

    fun getDocument(content: String): Document = createHTMLDocument().html {
        head {
            meta(charset = "UTF-8")
            onCreateHead()
        }
        body {
            onCreateBody(content)
        }
    }
}
