package com.hendraanggrian.pages.minimal

import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.footer
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.header
import kotlinx.html.html
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
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.w3c.dom.Document

internal object MinimalWriter {
    private const val HEADER_WIDTH = 270
    private const val HEADER_WRAPPED_WIDTH_PERCENTAGE = 99

    fun index(
        options: MinimalPagesOptionsImpl,
        htmlRenderer: HtmlRenderer,
        parser: Parser
    ): Document {
        val projectTitle = options.authorName?.let { "${options.projectName} by $it" } ?: options.projectName
        return createHTMLDocument().html {
            head {
                meta(charset = "utf-8")
                meta(content = "chrome=1") { httpEquiv = "X-UA-Compatible" }
                title(projectTitle)
                options.icon?.let { link(rel = "icon", href = it) }
                link(rel = "stylesheet", href = "styles/main.css")
                link(rel = "stylesheet", href = "styles/pygment_trac.css")
                link(
                    rel = "stylesheet",
                    href = "https://fonts.googleapis.com/css2?family=Material+Symbols+Sharp:opsz,wght,FILL,GRAD@48,400,1,0"
                )
                meta(name = "viewport", content = "width=device-width")
                script(src = "scripts/theme.js") { }
                meta(name = "title", content = projectTitle)
                options.projectDescription?.let { meta(name = "description", content = it) }
            }
            body {
                div(classes = "wrapper") {
                    header {
                        h1 { text(options.projectName) }
                        options.projectDescription?.let { p { text(it) } }
                        options.projectUrl?.let { url ->
                            p(classes = "view") {
                                a(href = url) {
                                    if ("github.com" in url) {
                                        text("View the Project on GitHub ")
                                        val parts = when {
                                            !url.endsWith('/') -> url
                                            else -> url.substring(0, url.lastIndex - 1)
                                        }.split('/').reversed()
                                        small { text("${parts[1]}/${parts[0]}") }
                                    } else {
                                        text("View the Project")
                                    }
                                }
                            }
                        }
                        if (options.headerButtons.isNotEmpty()) {
                            ul {
                                options.headerButtons.forEach { (line1, line2, url) ->
                                    li { a(href = url) { text(line1); strong { text(line2) } } }
                                }
                            }
                        }
                    }
                    section { unsafe { +htmlRenderer.render(parser.parse(options.markdownFile!!.readText())) } }
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
                            if (options.authorName != null && options.authorUrl != null) {
                                text("This project is maintained by ")
                                a(href = options.authorUrl) { text(options.authorName!!) }
                            } else if (options.authorName != null) {
                                text("This project is maintained by ${options.authorName}")
                            }
                        }
                        if (options.footerCredit) {
                            small {
                                text("Hosted on GitHub Pages — Theme by ")
                                a(href = "https://github.com/orderedlist/") { text("orderedlist") }
                            }
                        }
                    }
                }
                script(src = "scripts/scale.fix.js") { }
            }
        }
    }

    internal fun getMainCss(
        accent: String,
        accentLightHover: String,
        accentDarkHover: String,
        headerButtonSize: Int
    ): String = """
    :root {
      --background: #fafafa; /* grey_50 */

      --accent: $accent;
      --accent-hover: $accentLightHover;

      --text-heavy: #212121; /* grey_900 */
      --text-medium: #424242; /* grey_800 */
      --text-body: #616161; /* grey_700 */
      --text-caption: #757575; /* grey_600 */
      --text-caption-hover: #494949; /* grey_600_dark */
      --text-code: #eeeeee; /* grey_200 */

      --border-regular: #e0e0e0; /* grey_300 */
      --border-bold: #bdbdbd; /* grey_400 */

      --button-background: #eeeeee; /* grey_200 */
      --button-background-active: #bcbcbc; /* grey_200_dark */
      --button-border: #bdbdbd; /* grey_400 */
      --button-text1: #9e9e9e; /* grey_500 */
      --button-text1-hover: #707070; /* grey_500_dark */
      --button-text2: #212121; /* grey_900 */
      --button-text2-hover: #000000; /* grey_900 */

      --theme-toggle-image: 'dark_mode';
    }

    :root.theme-dark {
      --background: #212121; /* grey_900*/

      --accent-hover: $accentDarkHover;

      --text-heavy: #fafafa; /* grey_50 */
      --text-medium: #f5f5f5; /* grey_100 */
      --text-body: #bdbdbd; /* grey_400 */
      --text-caption: #9e9e9e; /* grey_500 */
      --text-caption-hover: #cfcfcf; /* grey_500_light */
      --text-code: #424242; /* grey_800 */

      --border-regular: #424242; /* grey_800 */
      --border-bold: #616161; /* grey_700 */

      --table-border: #616161; /* grey_700 */

      --theme-toggle-image: 'light_mode';
    }

    body {
      background-color: var(--background);
      padding: 50px;
      font: 14px/1.5 -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Fira Sans', 'Droid Sans', 'Helvetica Neue', Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol';
      color: var(--text-body);
      font-weight: 400;
    }

    h1, h2, h3, h4, h5, h6 {
      color: var(--text-heavy);
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
      color: var(--text-heavy);
      font-weight: 500;
    }

    h3, h4, h5, h6 {
      color: var(--text-heavy);
      font-weight: 500;
    }

    a {
      color: var(--accent);
      text-decoration: none;
    }

    a:hover {
      color: var(--accent-hover);
    }

    a small {
      font-size: 11px;
      color: var(--text-caption);
      margin-top: -0.3em;
      display: block;
    }

    a:hover small {
      color: var(--text-caption-hover);
    }

    p small {
      color: var(--text-caption);
    }

    .wrapper {
      width: 860px;
      margin: 0 auto;
    }

    blockquote {
      border-left: 4px solid var(--border-regular);
      margin: 0;
      padding: 0 0 0 20px;
      font-style: italic;
    }

    blockquote p {
      color: var(--text-caption);
    }

    code, pre {
      font-family: Monaco, Bitstream Vera Sans Mono, Lucida Console, Terminal, Consolas, Liberation Mono, DejaVu Sans Mono, Courier New, monospace;
      background-color: var(--text-code);
    }

    pre {
      padding: 8px 15px;
      background: var(--text-code);
      border-radius: 5px;
      overflow-x: auto;
    }

    table {
      width: 100%;
      border-collapse: collapse;
    }

    th, td {
      text-align: left;
      padding: 5px 10px;
      border-bottom: 1px solid var(--border-bold);
    }

    dt {
      color: var(--text-medium);
      font-weight: 500;
    }

    th {
      color: var(--text-medium);
    }

    img {
      max-width: 100%;
    }

    header {
      width: ${HEADER_WIDTH}px;
      float: left;
      position: fixed;
      -webkit-font-smoothing: subpixel-antialiased;
    }

    header ul {
      list-style: none;
      height: 40px;
      padding: 0;
      background: var(--button-background);
      border-radius: 5px;
      border: 1px solid var(--button-border);
      width: 270px;
    }

    header li {
      width: ${if (headerButtonSize == 0) HEADER_WIDTH else HEADER_WIDTH / headerButtonSize - 1}px;
      float: left;
      border-right: 1px solid var(--button-border);
      height: 40px;
    }

    header li:last-child {
      border-right: 0px;
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
      color: var(--button-text1);
      display: block;
      text-align: center;
      padding-top: 6px;
      height: 34px;
    }

    header ul a:hover {
      color: var(--button-text1-hover);
    }

    header ul a:active {
      background-color: var(--button-background-active);
    }

    strong {
      color: var(--text-heavy);
      font-weight: 500;
    }

    header ul li + li + li {
      border-right: none;
      width: 89px;
    }

    header ul a strong {
      font-size: 14px;
      display: block;
      color: var(--button-text2);
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
      background: var(--border-bold);
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

    footer button {
      height: 40px;
      padding: 0;
      background: var(--button-background);
      border-radius: 5px;
      border: 1px solid var(--button-border);
      width: 40px;
      color: var(--button-text2);
      justify-content: center;
    }

    footer button:hover {
      color: var(--button-text2-hover);
    }

    footer button:active {
      background-color: var(--button-background-active);
    }

    #theme-toggle:after {
      content: var(--theme-toggle-image);
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
        border: 1px solid #e0e0e0;
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
        width: $HEADER_WRAPPED_WIDTH_PERCENTAGE%;
      }

      header li, header ul li + li + li {
        width: ${if (headerButtonSize == 0) HEADER_WRAPPED_WIDTH_PERCENTAGE else HEADER_WRAPPED_WIDTH_PERCENTAGE / headerButtonSize}%;
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
        background-color: white;
        color: black;
      }
    }
    
    """.trimIndent()
}
