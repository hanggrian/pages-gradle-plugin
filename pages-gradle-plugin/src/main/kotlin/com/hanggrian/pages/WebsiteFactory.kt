package com.hanggrian.pages

import kotlinx.html.BODY
import kotlinx.html.HEAD
import kotlinx.html.body
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.meta
import java.io.StringWriter
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

internal abstract class WebsiteFactory(extension: PagesExtension) : PagesExtension by extension {
    abstract fun HEAD.onCreateHead()

    abstract fun BODY.onCreateBody(content: String)

    fun getDocument(content: String): String {
        val document =
            createHTMLDocument().html {
                head {
                    meta(charset = "UTF-8")
                    onCreateHead()
                }
                body {
                    onCreateBody(content)
                }
            }
        val writer = StringWriter()
        TransformerFactory
            .newInstance()
            .newTransformer()
            .transform(DOMSource(document), StreamResult(writer))
        return writer.toString()
    }
}
