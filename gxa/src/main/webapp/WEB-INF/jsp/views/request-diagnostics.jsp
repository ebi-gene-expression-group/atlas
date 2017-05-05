<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="small-10 small-centered columns">

        <div class="margin-bottom-xxlarge">
            <h3><samp>pageContext.request.{property}</samp></h3>
            <table>
                <thead>
                <th>Field</th>
                <th>Value</th>
                </thead>

                <tbody>
                <tr>
                    <td>pageContext.request.protocol</td>
                    <td>${pageContext.request.protocol}</td>
                </tr>
                <tr>
                    <td>pageContext.request.method</td>
                    <td>${pageContext.request.method}</td>
                </tr>
                <tr>
                    <td>pageContext.request.characterEncoding</td>
                    <td>${pageContext.request.characterEncoding}</td>
                </tr>

                <tr>
                    <td>pageContext.request.authType</td>
                    <td>${pageContext.request.authType}</td>
                </tr>
                <tr>
                    <td>pageContext.request.scheme</td>
                    <td>${pageContext.request.scheme}</td>
                </tr>
                <tr>
                    <td>pageContext.request.serverName</td>
                    <td>${pageContext.request.serverName}</td>
                </tr>
                <tr>
                    <td>pageContext.request.serverPort</td>
                    <td>${pageContext.request.serverPort}</td>
                </tr>
                <tr>
                    <td>pageContext.request.contextPath</td>
                    <td>${pageContext.request.contextPath}</td>
                </tr>
                <tr>
                    <td>pageContext.request.queryString</td>
                    <td>${pageContext.request.queryString}</td>
                </tr>
                <tr>
                    <td>pageContext.request.pathTranslated</td>
                    <td>${pageContext.request.pathTranslated}</td>
                </tr>

                <tr>
                    <td>pageContext.request.servletPath</td>
                    <td>${pageContext.request.servletPath}</td>
                </tr>
                <tr>
                    <td>pageContext.request.scheme</td>
                    <td>${pageContext.request.scheme}</td>
                </tr>
                <tr>
                    <td>pageContext.request.secure</td>
                    <td>${pageContext.request.secure}</td>
                </tr>

                <tr>
                    <td>pageContext.request.requestURI</td>
                    <td>${pageContext.request.requestURI}</td>
                </tr>
                <tr>
                    <td>pageContext.request.requestURL</td>
                    <td>${pageContext.request.requestURL}</td>
                </tr>
                <tr>
                    <td>pageContext.request.remoteHost</td>
                    <td>${pageContext.request.remoteHost}</td>
                </tr>
                <tr>
                    <td>pageContext.request.localPort</td>
                    <td>${pageContext.request.localPort}</td>
                </tr>
                <tr>
                    <td>pageContext.request.remotePort</td>
                    <td>${pageContext.request.remotePort}</td>
                </tr>
                </tbody>
            </table>
        </div>

        <div>
            <h3><samp>pageContext.getAttribute("javax.servlet.forward.{property}")</samp></h3>
            <table>
                <thead>
                <th>Field</th>
                <th>Value</th>
                </thead>

                <tbody>
                <tr>
                    <td>pageContext.request.getAttribute("javax.servlet.forward.context_path")</td>
                    <td>${pageContext.request.getAttribute("javax.servlet.forward.context_path")}</td>
                </tr>
                <tr>
                    <td>pageContext.request.getAttribute("javax.servlet.forward.path_info")</td>
                    <td>${pageContext.request.getAttribute("javax.servlet.forward.path_info")}</td>
                </tr>
                <tr>
                    <td>pageContext.request.getAttribute("javax.servlet.forward.query_string")</td>
                    <td>${pageContext.request.getAttribute("javax.servlet.forward.query_string")}</td>
                </tr>
                <tr>
                    <td>pageContext.request.getAttribute("javax.servlet.forward.request_uri")</td>
                    <td>${pageContext.request.getAttribute("javax.servlet.forward.request_uri")}</td>
                </tr>
                <tr>
                    <td>pageContext.request.getAttribute("javax.servlet.forward.servlet_path")</td>
                    <td>${pageContext.request.getAttribute("javax.servlet.forward.servlet_path")}</td>
                </tr>

                </tbody>
            </table>
        </div>

    </div>
</div>