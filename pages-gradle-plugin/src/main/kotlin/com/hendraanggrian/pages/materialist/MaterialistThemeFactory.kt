package com.hendraanggrian.pages.materialist

import com.hendraanggrian.pages.PagesExtension
import com.hendraanggrian.pages.WebsiteFactory
import kotlinx.html.BODY
import kotlinx.html.HEAD
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.footer
import kotlinx.html.h1
import kotlinx.html.header
import kotlinx.html.li
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.p
import kotlinx.html.script
import kotlinx.html.section
import kotlinx.html.small
import kotlinx.html.strong
import kotlinx.html.title
import kotlinx.html.ul
import kotlinx.html.unsafe

internal class MaterialistThemeFactory(
    extension: PagesExtension,
    private val options: MaterialistOptionsImpl
) : WebsiteFactory(extension), MaterialistOptions by options {

    override fun HEAD.onCreateHead() {
        title(projectName)
        if (favicon.isPresent) {
            link(rel = "icon", href = favicon.get())
        }

        meta(name = "viewport", content = "width=device-width, initial-scale=1, user-scalable=no")
        meta(content = "chrome=1") { httpEquiv = "X-UA-Compatible" }

        link(rel = "stylesheet", href = "styles/main.css")
        styles.orNull?.forEach {
            link(rel = "stylesheet", href = it)
        }
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
                            if ("github.com" in url) {
                                text("View the Project on GitHub ")
                                val parts = when {
                                    !url.endsWith('/') -> url
                                    else -> url.substringBeforeLast('/')
                                }.split('/').reversed()
                                small { text("${parts[1]}/${parts[0]}") }
                            } else {
                                text("View the Project")
                            }
                        }
                    }
                }
                if (options.buttons.isNotEmpty()) {
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
            }
            section { unsafe { +content } }
        }
        footer {
            p {
                if (authorName != null) {
                    text("This project is maintained by ")
                    if (authorUrl != null) {
                        a(href = authorUrl) { text(authorName!!) }
                    } else {
                        text(authorName!!)
                    }
                }
            }
            if (footerCredit) {
                small {
                    text("Hosted on GitHub Pages — Theme by ")
                    a(href = "https://github.com/orderedlist/") { text("orderedlist") }
                }
            }
        }
        script(src = "scripts/scale.fix.js") { }
    }

    val mainCss: String
        get() =
            """
            @import url(https://fonts.googleapis.com/css?family=Lato:300italic,400italic,700italic,300,400,700);
            @import url(https://fonts.googleapis.com/css?family=Roboto:400italic,700italic,400,700);
            @import url(https://fonts.googleapis.com/css?family=Roboto+Mono:400);

            /* Modification */

            * {
              --primary: $colorPrimary;
              --secondary: $colorSecondary;

              --on-primary: $colorOnPrimary;

              --surface: $colorSurface;
              --surface-container: $colorSurfaceContainer;
              --surface-container-high: $colorSurfaceContainerHigh;
              --surface-container-highest: $colorSurfaceContainerHighest;

              --on-surface: $colorOnSurface;
              --on-surface-variant: $colorOnSurfaceVariant;
              --outline: $colorOutline;
            }

            header h1 {
              font-family: Roboto;
            } header, footer {
              font-family: Lato;
            } section {
              font-family: Roboto;
            }

            h1 {
              margin: 40px 0 80px;
              font-size: 72px;
              font-weight: 300;
            } h2 {
              margin: 60px 0 60px;
              font-size: 48px;
              font-weight: 300;
            } h3 {
              margin: 60px 0 60px;
              font-size: 36px;
              font-weight: 300;
            } h4 {
              margin: 20px 0 20px;
              font-size: 24px;
              font-weight: 700;
            } h5 {
              margin: 20px 0 20px;
              font-size: 21px;
              font-weight: 700;
            } h6 {
              margin: 20px 0 20px;
              font-size: 18px;
              font-weight: 700;
            }

            /* Original */

            html {
              background: /*#6c7989*/ var(--surface);
              /* background: #6c7989 -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #6c7989), color-stop(100%, #434b55)) fixed;
              background: #6c7989 -webkit-linear-gradient(#6c7989, #434b55) fixed;
              background: #6c7989 -moz-linear-gradient(#6c7989, #434b55) fixed;
              background: #6c7989 -o-linear-gradient(#6c7989, #434b55) fixed;
              background: #6c7989 -ms-linear-gradient(#6c7989, #434b55) fixed;
              background: #6c7989 linear-gradient(#6c7989, #434b55) fixed; */
            }

            body {
              padding: 50px 0;
              margin: 0;
              font: /*14px/1.5 Lato*/ Roboto, 'Helvetica Neue', Helvetica, Arial, sans-serif;
              color: var(--on-surface-variant);
              /* font-weight: 300; */
              /* background: url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAeCAYAAABNChwpAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAALEgAACxIB0t1+/AAAABx0RVh0U29mdHdhcmUAQWRvYmUgRmlyZXdvcmtzIENTNXG14zYAAAAUdEVYdENyZWF0aW9uIFRpbWUAMy82LzEygrTcTAAAAFRJREFUSIljfPDggZRf5RIGGNjUHsNATz6jXmSL1Kb2GLiAX+USBnrymRgGGDCORgFmoNAXjEbBaBSMRsFoFIxGwWgUjEbBaBSMRsFoFIxGwWgUAABYNujumib3wAAAAABJRU5ErkJggg==') fixed; */
            }

            .wrapper {
              width: 640px;
              margin: 0 auto;
              background: /*#dedede*/ var(--surface);
              -moz-border-radius: /*8px*/ 12px;
              -webkit-border-radius: /*8px*/ 12px;
              -o-border-radius: /*8px*/ 12px;
              -ms-border-radius: /*8px*/ 12px;
              -khtml-border-radius: /*8px*/ 12px;
              border-radius: /*8px*/ 12px;
              /* -moz-box-shadow: rgba(0, 0, 0, 0.2) 0 0 0 1px, rgba(0, 0, 0, 0.45) 0 3px 10px;
              -webkit-box-shadow: rgba(0, 0, 0, 0.2) 0 0 0 1px, rgba(0, 0, 0, 0.45) 0 3px 10px;
              -o-box-shadow: rgba(0, 0, 0, 0.2) 0 0 0 1px, rgba(0, 0, 0, 0.45) 0 3px 10px;
              box-shadow: rgba(0, 0, 0, 0.2) 0 0 0 1px, rgba(0, 0, 0, 0.45) 0 3px 10px; */
            }

            header, section, footer {
              display: block;
            }

            a {
              color: /*#069*/ var(--primary);
              text-decoration: none;
            }

            p {
              margin: 0 0 20px;
              padding: 0;
            }

            strong {
              color: /*#222*/ var(--on-surface);
              font-weight: 700;
            }

            header {
              -moz-border-radius: /*8px 8px*/ 10px 10px 0 0;
              -webkit-border-radius: /*8px 8px*/ 10px 10px 0 0;
              -o-border-radius: /*8px 8px*/ 10px 10px 0 0;
              -ms-border-radius: /*8px 8px*/ 10px 10px 0 0;
              -khtml-border-radius: /*8px 8px*/ 10px 10px 0 0;
              border-radius: /*8px 8px*/ 10px 10px 0 0;
              background: /*#c6eafa*/ var(--surface-container);
              /* background: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #ddfbfc), color-stop(100%, #c6eafa));
              background: -webkit-linear-gradient(#ddfbfc, #c6eafa);
              background: -moz-linear-gradient(#ddfbfc, #c6eafa);
              background: -o-linear-gradient(#ddfbfc, #c6eafa);
              background: -ms-linear-gradient(#ddfbfc, #c6eafa);
              background: linear-gradient(#ddfbfc, #c6eafa); */
              position: relative;
              padding: 15px 20px;
              /* border-bottom: 1px solid #B2D2E1; */
            }
            header h1 {
              margin: 0;
              padding: 0;
              font-size: /*24px*/ 17px;
              line-height: 1.2;
              color: /*#069*/ var(--on-surface);
              /* text-shadow: rgba(255, 255, 255, 0.9) 0 1px 0; */
            }
            header.without-description h1 {
              margin: 10px 0;
            }
            header p {
              margin: 0;
              color: /*#61778B*/ var(--secondary);
              width: 300px;
              font-size: /*13px*/ 13px;
            }
            header p.view {
              display: none;
              font-weight: 700;
              /* text-shadow: rgba(255, 255, 255, 0.9) 0 1px 0; */
              -webkit-font-smoothing: antialiased;
            }
            header p.view a {
              color: /*#06c*/ var(--primary);
            }
            header p.view small {
              font-weight: 400;
            }
            header ul {
              margin: 0;
              padding: 0;
              list-style: none;
              position: absolute;
              z-index: 1;
              right: 20px;
              top: 20px;
              height: 38px;
              padding: 1px 0;
              background: /*#5198df*/ var(--primary);
              /* background: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #77b9fb), color-stop(100%, #3782cd));
              background: -webkit-linear-gradient(#77b9fb, #3782cd);
              background: -moz-linear-gradient(#77b9fb, #3782cd);
              background: -o-linear-gradient(#77b9fb, #3782cd);
              background: -ms-linear-gradient(#77b9fb, #3782cd);
              background: linear-gradient(#77b9fb, #3782cd); */
              border-radius: 5px;
              /* -moz-box-shadow: inset rgba(255, 255, 255, 0.45) 0 1px 0, inset rgba(0, 0, 0, 0.2) 0 -1px 0;
              -webkit-box-shadow: inset rgba(255, 255, 255, 0.45) 0 1px 0, inset rgba(0, 0, 0, 0.2) 0 -1px 0;
              -o-box-shadow: inset rgba(255, 255, 255, 0.45) 0 1px 0, inset rgba(0, 0, 0, 0.2) 0 -1px 0;
              box-shadow: inset rgba(255, 255, 255, 0.45) 0 1px 0, inset rgba(0, 0, 0, 0.2) 0 -1px 0; */
              width: 240px;
            }
            header ul:before {
              content: '';
              position: absolute;
              z-index: -1;
              left: -5px;
              top: -4px;
              right: -5px;
              bottom: -6px;
              /* background: rgba(0, 0, 0, 0.1);
              -moz-border-radius: 8px;
              -webkit-border-radius: 8px;
              -o-border-radius: 8px;
              -ms-border-radius: 8px;
              -khtml-border-radius: 8px;
              border-radius: 8px;
              -moz-box-shadow: rgba(0, 0, 0, 0.2) 0 -1px 0, inset rgba(255, 255, 255, 0.7) 0 -1px 0;
              -webkit-box-shadow: rgba(0, 0, 0, 0.2) 0 -1px 0, inset rgba(255, 255, 255, 0.7) 0 -1px 0;
              -o-box-shadow: rgba(0, 0, 0, 0.2) 0 -1px 0, inset rgba(255, 255, 255, 0.7) 0 -1px 0;
              box-shadow: rgba(0, 0, 0, 0.2) 0 -1px 0, inset rgba(255, 255, 255, 0.7) 0 -1px 0; */
            }
            header ul li {
              width: 79px;
              float: left;
              border-right: 1px solid /*#3A7cbe*/ var(--secondary);
              height: 38px;
            }
            header ul li + li {
              width: 78px;
              border-left: 1px solid /*8bbef3*/ var(--secondary);
            }
            header ul li + li + li {
              border-right: none;
              width: 79px;
            }
            header ul a {
              line-height: 1;
              font-size: 11px;
              color: /*#fff*/ var(--on-primary);
              /* color: rgba(255, 255, 255, 0.8); */
              display: block;
              text-align: center;
              font-weight: 400;
              padding-top: 6px;
              height: 40px;
              /* text-shadow: rgba(0, 0, 0, 0.4) 0 -1px 0; */
            }
            header ul a strong {
              font-size: 14px;
              display: block;
              color: /*#fff*/ var(--on-primary);
              -webkit-font-smoothing: antialiased;
            }

            section {
              padding: 15px 20px;
              font-size: /*15px*/ 16px;
              /* border-top: 1px solid #fff; */
              background: /*-webkit-gradient(linear, 50% 0%, 50% 700, color-stop(0%, #fafafa), color-stop(100%, #dedede))*/ var(--surface-container);
              /* background: -webkit-linear-gradient(#fafafa, #dedede 700px);
              background: -moz-linear-gradient(#fafafa, #dedede 700px);
              background: -o-linear-gradient(#fafafa, #dedede 700px);
              background: -ms-linear-gradient(#fafafa, #dedede 700px);
              background: linear-gradient(#fafafa, #dedede 700px); */
              -moz-border-radius: 0 0 8px 8px;
              -webkit-border-radius: 0 0 8px 8px;
              -o-border-radius: 0 0 8px 8px;
              -ms-border-radius: 0 0 8px 8px;
              -khtml-border-radius: 0 0 8px 8px;
              border-radius: 0 0 8px 8px;
              position: relative;
            }

            h1, h2, h3, h4, h5, h6 {
              color: /*#222*/ var(--on-surface-variant);
              padding: 0;
              /* margin: 0 0 20px; */
              line-height: 1.2;
            }

            p, ul, ol, table, pre, dl {
              margin: 0 0 20px;
            }

            h1, h2, h3 {
              line-height: 1.1;
            }

            /* h1 {
              font-size: 28px;
            } */

            h2 {
              color: /*393939*/ var(--on-surface);
            }

            h3, h4, h5, h6 {
              color: /*#494949*/ var(--on-surface-variant);
            }

            blockquote {
              margin: 0 -20px 20px;
              padding: 15px 20px 1px 40px;
              font-style: italic;
              background: /*#ccc*/ var(--surface-container-high);
              /* background: rgba(0, 0, 0, 0.06); */
              color: /*#222*/ var(--outline);
            }

            img {
              max-width: 100%;
            }

            code, pre {
              font-family: /*Monaco, Bitstream Vera Sans Mono, Lucida Console, Terminal*/ 'Roboto Mono', monospace;
              color: /*#333*/ var(--on-surface-variant);
              font-size: 12px;
            }

            pre {
              padding: 20px;
              background: /*#3A3C42*/ var(--surface-container-highest);
              color: /*f8f8f2*/ var(--on-surface);
              margin: 0 -20px 20px;
              overflow-x: auto;
            }
            pre code {
              color: /*#f8f8f2*/ var(--on-surface);
            }
            li pre {
              margin-left: -60px;
              padding-left: 60px;
            }

            table {
              width: 100%;
              border-collapse: collapse;
            }

            th, td {
              text-align: left;
              padding: 5px 10px;
              border-bottom: 1px solid /*#aaa*/ var(--surface-container-high);
            }

            dt {
              color: /*#222*/ var(--on-surface-variant);
              font-weight: 700;
            }

            th {
              color: /*#222*/ var(--on-surface-variant);
            }

            small {
              font-size: 11px;
            }

            hr {
              border: 0;
              background: /*#aaa*/ var(--surface-container-high);
              height: 1px;
              margin: 0 0 20px;
            }

            footer {
              width: 640px;
              margin: 0 auto;
              padding: 20px 0 0;
              color: /*#ccc*/ var(--on-surface-variant);
              overflow: hidden;
            }
            footer a {
              color: /*#fff*/ var(--primary);
              font-weight: bold;
            }
            footer p {
              float: left;
            }
            footer p + p {
              float: right;
            }

            @media print, screen and (max-width: 740px) {
              body {
                padding: 0;
              }

              .wrapper {
                -moz-border-radius: 0;
                -webkit-border-radius: 0;
                -o-border-radius: 0;
                -ms-border-radius: 0;
                -khtml-border-radius: 0;
                border-radius: 0;
                /* -moz-box-shadow: none;
                -webkit-box-shadow: none;
                -o-box-shadow: none;
                box-shadow: none; */
                width: 100%;
              }

              footer {
                -moz-border-radius: 0;
                -webkit-border-radius: 0;
                -o-border-radius: 0;
                -ms-border-radius: 0;
                -khtml-border-radius: 0;
                border-radius: 0;
                padding: 20px;
                width: auto;
              }
              footer p {
                float: none;
                margin: 0;
              }
              footer p + p {
                float: none;
              }
            }
            @media print, screen and (max-width:580px) {
              header ul {
                display: none;
              }

              header p.view {
                display: block;
              }

              header p {
                width: 100%;
              }
            }
            @media print {
              header p.view a small:before {
                content: 'at http://github.com/';
              }
            }

            """.trimIndent()
}
