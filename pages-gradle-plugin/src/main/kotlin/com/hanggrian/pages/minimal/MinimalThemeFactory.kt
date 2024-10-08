package com.hanggrian.pages.minimal

import com.hanggrian.pages.PagesExtension
import com.hanggrian.pages.WebsiteFactory
import kotlinx.html.BODY
import kotlinx.html.HEAD
import kotlinx.html.a
import kotlinx.html.button
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.footer
import kotlinx.html.h1
import kotlinx.html.header
import kotlinx.html.id
import kotlinx.html.li
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.onClick
import kotlinx.html.p
import kotlinx.html.script
import kotlinx.html.section
import kotlinx.html.small
import kotlinx.html.strong
import kotlinx.html.title
import kotlinx.html.ul
import kotlinx.html.unsafe

internal class MinimalThemeFactory(
    extension: PagesExtension,
    private val options: MinimalOptionsImpl,
) : WebsiteFactory(extension),
    MinimalOptions by options {
    override fun HEAD.onCreateHead() {
        title(projectName)
        if (favicon.isPresent) {
            link(rel = "icon", href = favicon.get())
        }

        meta(name = "viewport", content = "width=device-width")
        meta(content = "chrome=1") { httpEquiv = "X-UA-Compatible" }

        link(rel = "stylesheet", href = "styles/main.css")
        link(
            rel = "stylesheet",
            href =
                "https://fonts.googleapis.com/" +
                    "css2?family=Material+Symbols+Sharp:opsz,wght,FILL,GRAD@48,400,1,0",
        )
        styles.orNull?.forEach {
            link(rel = "stylesheet", href = it)
        }
        script(src = "scripts/theme.js") { }
        scripts.orNull?.forEach {
            script(src = it) { }
        }

        comment("Primary meta tags")
        meta(name = "title", content = projectName)
        projectDescription?.let { meta(name = "description", content = it) }
    }

    override fun BODY.onCreateBody(content: String) {
        div(classes = "wrapper") {
            header {
                h1 { text(projectName) }
                projectDescription?.let { p { text(it) } }
                projectUrl?.let { url ->
                    p(classes = "view") {
                        a(href = url) {
                            when {
                                "github.com" in url -> {
                                    text("View the Project on GitHub ")
                                    val parts =
                                        when {
                                            !url.endsWith('/') -> url
                                            else -> url.substringBeforeLast('/')
                                        }.split('/')
                                            .reversed()
                                    small { text("${parts[1]}/${parts[0]}") }
                                }
                                else -> text("View the Project")
                            }
                        }
                    }
                }
                if (options.buttons.isEmpty()) {
                    return@header
                }
                ul {
                    options.buttons.forEach { (text, url) ->
                        li {
                            a(href = url) {
                                text(text.substringBefore('\n'))
                                strong { text(text.substringAfter('\n')) }
                            }
                        }
                    }
                }
            }
            section {
                var current = content
                languageAliases.get().forEach { (original, replacement) ->
                    current = current.replace("\"language-$original\"", "\"language-$replacement\"")
                }
                unsafe { +current }
            }
            footer {
                p {
                    button {
                        classes = setOf("material-symbols-sharp")
                        id = "theme-toggle"
                        title = "Toggle dark mode"
                        onClick = "toggleDarkMode()"
                    }
                }
                p {
                    authorName ?: return@p
                    text("This project is maintained by ")
                    when (authorUrl) {
                        null -> text(authorName!!)
                        else -> a(href = authorUrl) { text(authorName!!) }
                    }
                }
                if (!isFooterCredit) {
                    return@footer
                }
                small {
                    text("Hosted on GitHub Pages — Theme by ")
                    a(href = "https://github.com/orderedlist/") { text("orderedlist") }
                }
            }
        }
        script(src = "scripts/scale.fix.js") { }
    }

    val mainCss: String
        get() {
            val buttonSize =
                when {
                    options.buttons.isEmpty() -> 0
                    else -> 270 / options.buttons.size - (options.buttons.size * 1)
                }
            val miniButtonPct =
                when {
                    options.buttons.isEmpty() -> 99
                    else -> 99 / options.buttons.size
                }
            return """
                /* Modification */

                * {
                  --primary: $colorPrimary; /*Blue A200*/
                }

                :root {
                  --primary-container: $colorPrimaryContainer; /*Blue A200 Dark*/

                  --surface: #fafafa; /*Gray 50*/
                  --surface-container: #f5f5f5; /*Gray 100*/
                  --surface-container-high: #c2c2c2; /*Gray 100 Dark*/

                  --on-surface: #212121; /*Gray 900*/
                  --on-surface-variant: #424242; /*Gray 800*/
                  --outline: #616161; /*Gray 700*/
                  --outline-variant: #757575; /*Gray 600*/

                  --border: #e0e0e0; /*Gray 300*/
                  --button-text1: #9e9e9e; /*Gray 500*/
                  --button-text1-hover: #707070; /*Gray 500 Dark*/
                  --button-text2: #212121; /*Gray 900*/
                  --button-text2-hover: #000000; /*Gray 900 Dark*/

                  --theme-toggle-image: 'dark_mode';
                }

                :root.theme-dark {
                  --primary-container: $colorPrimaryContainer2; /*Blue A200 Light*/

                  --surface: #212121; /*Gray 900*/
                  --surface-container: #424242; /*Gray 800*/
                  --surface-container-high: #6d6d6d; /*Gray 800 Light*/

                  --on-surface: #fafafa; /*Gray 50*/
                  --on-surface-variant: #f5f5f5; /*Gray 100*/
                  --outline: #bdbdbd; /*Gray 400*/
                  --outline-variant: #9e9e9e; /*Gray 500*/

                  --border: #616161; /*Gray 700*/
                  --button-text1: #bdbdbd; /*Gray 400*/
                  --button-text1-hover: #8d8d8d; /*Gray 400 Light*/
                  --button-text2: #fafafa; /*Gray 50*/
                  --button-text2-hover: #ffffff; /*Gray 50 Light*/

                  --theme-toggle-image: 'light_mode';
                }

                p small {
                  color: var(--outline-variant);
                } blockquote p {
                  color: var(--outline-variant);
                }

                code {
                  padding: 2px 4px;
                  border-radius: 0.3rem;
                } code, pre {
                  background-color: var(--surface-container);
                } pre code {
                  padding: 0px 0px;
                }

                header li:last-child {
                  border-right: 0px;
                }

                footer button {
                  height: 40px;
                  padding: 0;
                  background: var(--surface-container);
                  border-radius: 5px;
                  border: 1px solid var(--border);
                  width: 40px;
                  color: var(--button-text2);
                  justify-content: center;
                  cursor: pointer;
                } footer button:hover {
                  color: var(--button-text2-hover);
                } footer button:active {
                  background-color: var(--surface-container-high);
                }

                #theme-toggle:after {
                  content: var(--theme-toggle-image);
                }

                /* Original */

                body {
                  background-color: /*#fff*/ var(--surface);
                  padding: 50px;
                  font: 14px/1.5 -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Fira Sans', 'Droid Sans', 'Helvetica Neue', Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol';
                  color: /*#595959*/ var(--outline);
                  font-weight: 400;
                }

                h1, h2, h3, h4, h5, h6 {
                  color: /*#222*/ var(--on-surface);
                  margin: 0 0 20px;
                }

                p, ul, ol, table, pre, dl {
                  margin: 0 0 20px;
                }

                h1, h2, h3 {
                  line-height: 1.1;
                }

                h1 {
                  font-size: 28px;
                  font-weight: 500;
                }

                h2 {
                  color: /*#393939*/ var(--on-surface);
                  font-weight: 500;
                }

                h3, h4, h5, h6 {
                  color: /*#494949*/ var(--on-surface);
                  font-weight: 500;
                }

                a {
                  color: /*#39c*/ var(--primary);
                  text-decoration: none;
                }

                a:hover {
                  color: /*#069*/ var(--primary-container);
                }

                a small {
                  font-size: 11px;
                  color: /*#777*/ var(--outline-variant);
                  margin-top: -0.3em;
                  display: block;
                }

                a:hover small {
                  color: /*#777*/ var(--on-surface-variant);
                }

                .wrapper {
                  width: 860px;
                  margin: 0 auto;
                }

                blockquote {
                  border-left: /*1px*/ 4px solid /*#e5e5e5*/ var(--border);
                  margin: 0;
                  padding: 0 0 0 20px;
                  font-style: italic;
                }

                code, pre {
                  font-family: Monaco, Bitstream Vera Sans Mono, Lucida Console, Terminal, Consolas, Liberation Mono, DejaVu Sans Mono, Courier New, monospace;
                  /* color: #333; */
                }

                pre {
                  padding: 8px 15px;
                  background: /*#f8f8f8*/ var(--surface-container);
                  border-radius: 5px;
                  border: 1px solid /*#e5e5e5*/ var(--border);
                  overflow-x: auto;
                }

                table {
                  width: 100%;
                  border-collapse: collapse;
                }

                th, td {
                  text-align: left;
                  padding: 5px 10px;
                  border-bottom: 1px solid /*#e5e5e5*/ var(--border);
                }

                dt {
                  color: /*#444*/ var(--on-surface-variant);
                  font-weight: 500;
                }

                th {
                  color: /*#444*/ var(--on-surface-variant);
                }

                img {
                  max-width: 100%;
                }

                header {
                  width: 270px;
                  float: left;
                  position: fixed;
                  -webkit-font-smoothing: subpixel-antialiased;
                }

                header ul {
                  list-style: none;
                  height: 40px;
                  padding: 0;
                  background: /*#f4f4f4*/ var(--surface-container);
                  border-radius: 5px;
                  border: 1px solid /*#e0e0e0*/ var(--border);
                  width: 270px;
                }

                header li {
                  width: ${buttonSize}px;
                  float: left;
                  border-right: 1px solid var(--border);
                  height: 40px;
                }

                header li:first-child a {
                  border-radius: 5px 0 0 5px;
                }

                header li:last-child a {
                  border-radius: 0 5px 5px 0;
                }

                header ul a {
                  line-height: 1;
                  font-size: 11px;
                  color: /*#999*/ var(--button-text1);
                  display: block;
                  text-align: center;
                  padding-top: 6px;
                  height: 34px;
                }

                header ul a:hover {
                  color: /*#999*/ var(--button-text1-hover);
                }

                header ul a:active {
                  background-color: /*#f0f0f0*/ var(--surface-container-high);
                }

                strong {
                  color: /*#222*/ var(--on-surface);
                  font-weight: 500;
                }

                header ul li + li + li {
                  border-right: none;
                  width: 89px;
                }

                header ul a strong {
                  font-size: 14px;
                  display: block;
                  color: /*222*/ var(--button-text2);
                }

                section {
                  width: 500px;
                  float: right;
                  padding-bottom: 50px;
                }

                small {
                  font-size: 11px;
                }

                hr {
                  border: 0;
                  background: /*#e5e5e5*/ var(--border);
                  height: 1px;
                  margin: 0 0 20px;
                }

                footer {
                  width: 270px;
                  float: left;
                  position: fixed;
                  bottom: 50px;
                  -webkit-font-smoothing: subpixel-antialiased;
                }

                @media print, screen and (max-width: 960px) {
                  div.wrapper {
                    width: auto;
                    margin: 0;
                  }

                  header, section, footer {
                    float: none;
                    position: static;
                    width: auto;
                  }

                  header {
                    padding-right: 320px;
                  }

                  footer {
                    padding-right: 60px;
                  }

                  section {
                    border: 1px solid /*#e5e5e5*/ var(--border);
                    border-width: 1px 0;
                    padding: 20px 0;
                    margin: 0 0 20px;
                  }

                  header a small {
                    display: inline;
                  }

                  header ul {
                    position: absolute;
                    right: 50px;
                    top: 52px;
                  }

                  footer button {
                    position: fixed;
                    right: 50px;
                    bottom: 52px;
                  }
                }

                @media print, screen and (max-width: 720px) {
                  body {
                    word-wrap: break-word;
                  }

                  header {
                    padding: 0;
                  }

                  header ul, header p.view {
                    position: static;
                  }

                  pre, code {
                    word-wrap: normal;
                  }
                }

                @media print, screen and (max-width: 480px) {
                  body {
                    padding: 15px;
                  }

                  header ul {
                    width: 99%;
                  }

                  header li, header ul li + li + li {
                    width: $miniButtonPct%;
                  }

                  footer button {
                    position: fixed;
                    right: 25px;
                    bottom: 26px;
                  }
                }

                @media print {
                  body {
                    padding: 0.4in;
                    font-size: 12pt;
                    /* color: #444; */
                  }
                }
                """.trimIndent()
        }
}
