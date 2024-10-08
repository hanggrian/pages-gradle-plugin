package com.hanggrian.pages.cayman

import com.hanggrian.pages.PagesExtension
import com.hanggrian.pages.WebsiteFactory
import kotlinx.html.BODY
import kotlinx.html.HEAD
import kotlinx.html.a
import kotlinx.html.footer
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.script
import kotlinx.html.section
import kotlinx.html.span
import kotlinx.html.title
import kotlinx.html.unsafe

internal class CaymanThemeFactory(
    extension: PagesExtension,
    private val options: CaymanOptionsImpl,
) : WebsiteFactory(extension),
    CaymanOptions by options {
    override fun HEAD.onCreateHead() {
        title(projectName)
        if (favicon.isPresent) {
            link(rel = "icon", href = favicon.get())
        }

        meta(name = "viewport", content = "width=device-width, initial-scale=1")
        meta(name = "theme-color", content = colorPrimary)

        link(rel = "stylesheet", href = "styles/main.css")
        if (darkMode) {
            link(rel = "stylesheet", href = "styles/dark.css")
        }
        link(rel = "stylesheet", href = "styles/normalize.css")
        if (styles.isPresent) {
            styles.get().forEach { link(rel = "stylesheet", href = it) }
        }
        if (scripts.isPresent) {
            scripts.get().forEach { script(src = it) { } }
        }

        comment("Primary meta tags")
        meta(name = "title", content = projectName)
        projectDescription?.let { meta(name = "description", content = it) }
    }

    override fun BODY.onCreateBody(content: String) {
        section(classes = "page-header") {
            h1(classes = "project-name") { text(projectName) }
            projectDescription?.let { h2(classes = "project-tagline") { text(it) } }
            options.buttons.forEach { (text, url) ->
                a(classes = "btn", href = url) { text(text) }
            }
        }
        section(classes = "main-content") {
            var current = content
            languageAliases.get().forEach { (original, replacement) ->
                current = current.replace("\"language-$original\"", "\"language-$replacement\"")
            }
            unsafe { +current }

            footer(classes = "site-footer") {
                span(classes = "site-footer-owner") {
                    authorName ?: return@span
                    when (projectUrl) {
                        null -> text("This project")
                        else -> a(href = projectUrl) { text(projectName) }
                    }
                    text(" is maintained by ")
                    when (authorUrl) {
                        null -> text(authorName!!)
                        else -> a(href = authorUrl) { text(authorName!!) }
                    }
                }
                if (!isFooterCredit) {
                    return@footer
                }
                span(classes = "site-footer-credits") {
                    text("Hosted on GitHub Pages — Theme by ")
                    a(href = "https://github.com/jasonlong/cayman-theme/") {
                        text("jasonlong")
                    }
                }
            }
        }
    }

    val mainCss: String
        get() =
            """
            @import url(https://fonts.googleapis.com/css?family=Open+Sans:400italic,700italic,400,700);

            /* Modification */

            * {
              --primary: $colorPrimary;
              --secondary: $colorSecondary;
              --secondary-container: $colorSecondaryContainer;

              --surface: #fafafa; /*Gray 50*/
              --surface-container: #eceff1; /*Blue Gray 50*/

              --on-surface: #455a64; /*Blue Gray 700*/
              --on-surface-variant: #607d8b; /*Blue Gray 500*/
              --outline: #cfd8dc; /*Blue Gray 100*/
              --outline-variant: #fafafa; /*Gray 50*/

              --button-text: rgba(255, 255, 255, 0.7);
              --button-text-hover: rgba(255, 255, 255, 0.8);
              --button-background: rgba(255, 255, 255, 0.08);
              --button-background-hover: rgba(255, 255, 255, 0.2);
              --button-border: rgba(255, 255, 255, 0.2);
              --button-border-hover: rgba(255, 255, 255, 0.3);
            }

            body {
              background-color: var(--surface);
            }

            /* Original */

            * {
              box-sizing: border-box; }

            body {
              padding: 0;
              margin: 0;
              font-family: 'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif;
              font-size: 16px;
              line-height: 1.5;
              color: /*#606c71;*/ var(--on-surface); }

            a {
              color: /*1e6bb8;*/ var(--secondary);
              text-decoration: none; }
              a:hover {
                text-decoration: underline; }

            .btn {
              display: inline-block;
              margin-bottom: 1rem;
              color: /*rgba(255, 255, 255, 0.7)*/ var(--button-text);
              background-color: /*rgba(255, 255, 255, 0.08)*/ var(--button-background);
              border-color: /*rgba(255, 255, 255, 0.2)*/ var(--button-border);
              border-style: solid;
              border-width: 1px;
              border-radius: 0.3rem;
              transition: color 0.2s, background-color 0.2s, border-color 0.2s; }
              .btn:hover {
                color: /*rgba(255, 255, 255, 0.8)*/ var(--button-text-hover);
                text-decoration: none;
                background-color: /*rgba(255, 255, 255, 0.2)*/ var(--button-background-hover);
                border-color: /*rgba(255, 255, 255, 0.3)*/ var(--button-border-hover); }
              .btn + .btn {
                margin-left: 1rem; }
              @media screen and (min-width: 64em) {
                .btn {
                  padding: 0.75rem 1rem; } }
              @media screen and (min-width: 42em) and (max-width: 64em) {
                .btn {
                  padding: 0.6rem 0.9rem;
                  font-size: 0.9rem; } }
              @media screen and (max-width: 42em) {
                .btn {
                  display: block;
                  width: 100%;
                  padding: 0.75rem;
                  font-size: 0.9rem; }
                  .btn + .btn {
                    margin-top: 1rem;
                    margin-left: 0; } }

            .page-header {
              color: /*fff;*/ var(--outline-variant);
              text-align: center;
              background-color: /*#159957;*/ var(--primary);
              background-image: linear-gradient(120deg, var(--secondary-container), var(--primary)); }
              @media screen and (min-width: 64em) {
                .page-header {
                  padding: 5rem 6rem; } }
              @media screen and (min-width: 42em) and (max-width: 64em) {
                .page-header {
                  padding: 3rem 4rem; } }
              @media screen and (max-width: 42em) {
                .page-header {
                  padding: 2rem 1rem; } }

            .project-name {
              margin-top: 0;
              margin-bottom: 0.1rem; }
              @media screen and (min-width: 64em) {
                .project-name {
                  font-size: 3.25rem; } }
              @media screen and (min-width: 42em) and (max-width: 64em) {
                .project-name {
                  font-size: 2.25rem; } }
              @media screen and (max-width: 42em) {
                .project-name {
                  font-size: 1.75rem; } }

            .project-tagline {
              margin-bottom: 2rem;
              font-weight: normal;
              opacity: 0.7; }
              @media screen and (min-width: 64em) {
                .project-tagline {
                  font-size: 1.25rem; } }
              @media screen and (min-width: 42em) and (max-width: 64em) {
                .project-tagline {
                  font-size: 1.15rem; } }
              @media screen and (max-width: 42em) {
                .project-tagline {
                  font-size: 1rem; } }

            .main-content {
              word-wrap: break-word; }
              .main-content :first-child {
                margin-top: 0; }
              @media screen and (min-width: 64em) {
                .main-content {
                  max-width: 64rem;
                  padding: 2rem 6rem;
                  margin: 0 auto;
                  font-size: 1.1rem; } }
              @media screen and (min-width: 42em) and (max-width: 64em) {
                .main-content {
                  padding: 2rem 4rem;
                  font-size: 1.1rem; } }
              @media screen and (max-width: 42em) {
                .main-content {
                  padding: 2rem 1rem;
                  font-size: 1rem; } }
              .main-content img {
                max-width: 100%; }
              .main-content h1,
              .main-content h2,
              .main-content h3,
              .main-content h4,
              .main-content h5,
              .main-content h6 {
                margin-top: 2rem;
                margin-bottom: 1rem;
                font-weight: normal;
                color: /*#159957;*/ var(--primary); }
              .main-content p {
                margin-bottom: 1em; }
              .main-content code {
                padding: 2px 4px;
                font-family: Consolas, 'Liberation Mono', Menlo, Courier, monospace;
                font-size: 0.9rem;
                color: /*#567482;*/ var(--on-surface);
                background-color: /*#f3f6fa*/ var(--surface-container);
                border-radius: 0.3rem; }
              .main-content pre {
                padding: 0.8rem;
                margin-top: 0;
                margin-bottom: 1rem;
                font: 1rem Consolas, 'Liberation Mono', Menlo, Courier, monospace;
                color: /*#567482*/ var(--on-surface);
                word-wrap: normal;
                background-color: /*#f3f6fa*/ var(--surface-container);
                border: solid 1px /*#dce6f0*/ var(--outline);
                border-radius: 0.3rem; }
                .main-content pre > code {
                  padding: 0;
                  margin: 0;
                  font-size: 0.9rem;
                  color: /*#567482*/ var(--on-surface);
                  word-break: normal;
                  white-space: pre;
                  background: transparent;
                  border: 0; }
              .main-content .highlight {
                margin-bottom: 1rem; }
                .main-content .highlight pre {
                  margin-bottom: 0;
                  word-break: normal; }
              .main-content .highlight pre,
              .main-content pre {
                padding: 0.8rem;
                overflow: auto;
                font-size: 0.9rem;
                line-height: 1.45;
                border-radius: 0.3rem;
                -webkit-overflow-scrolling: touch; }
              .main-content pre code,
              .main-content pre tt {
                display: inline;
                max-width: initial;
                padding: 0;
                margin: 0;
                overflow: initial;
                line-height: inherit;
                word-wrap: normal;
                background-color: transparent;
                border: 0; }
                .main-content pre code:before, .main-content pre code:after,
                .main-content pre tt:before,
                .main-content pre tt:after {
                  content: normal; }
              .main-content ul,
              .main-content ol {
                margin-top: 0; }
              .main-content blockquote {
                padding: 0 1rem;
                margin-left: 0;
                color: /*#819198*/ var(--on-surface-variant);
                border-left: 0.3rem solid /*#dce6f0*/ var(--outline); }
                .main-content blockquote > :first-child {
                  margin-top: 0; }
                .main-content blockquote > :last-child {
                  margin-bottom: 0; }
              .main-content table {
                display: block;
                width: 100%;
                overflow: auto;
                word-break: normal;
                word-break: keep-all;
                -webkit-overflow-scrolling: touch; }
                .main-content table th {
                  font-weight: bold; }
                .main-content table th,
                .main-content table td {
                  padding: 0.5rem 1rem;
                  border: 1px solid /*#e9ebec*/ var(--outline); }
              .main-content dl {
                padding: 0; }
                .main-content dl dt {
                  padding: 0;
                  margin-top: 1rem;
                  font-size: 1rem;
                  font-weight: bold; }
                .main-content dl dd {
                  padding: 0;
                  margin-bottom: 1rem; }
              .main-content hr {
                height: 2px;
                padding: 0;
                margin: 1rem 0;
                background-color: /*#eff0f1*/ var(--outline);
                border: 0; }

            .site-footer {
              padding-top: 2rem;
              margin-top: 2rem;
              border-top: solid 1px /*#eff0f1*/ var(--outline); }
              @media screen and (min-width: 64em) {
                .site-footer {
                  font-size: 1rem; } }
              @media screen and (min-width: 42em) and (max-width: 64em) {
                .site-footer {
                  font-size: 1rem; } }
              @media screen and (max-width: 42em) {
                .site-footer {
                  font-size: 0.9rem; } }

            .site-footer-owner {
              display: block;
              font-weight: bold; }

            .site-footer-credits {
              color: /*#819198*/ var(--on-surface-variant); }

            """.trimIndent()
}
