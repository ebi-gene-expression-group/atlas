<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="org.apache.commons.io.IOUtils" %>
<%@ page import="java.nio.file.Path" %>
<%@ page import="java.nio.file.Files" %>


<section class="extra-padding">

    <table>
        <tbody cellpadding="2" cellspacing="0" style="float:right">
        <tr>
            <td width="130px"></td>
            <td width="1000px"></td>
            <td width="160px" style="text-align: right">
                <a id="display-experiment" class="button-image"
                   title="Experiment Page" href="${pageContext.request.contextPath}/experiments/${experimentAccession}">
                    <img src="${pageContext.request.contextPath}/resources/images/experiment_page_small.png"/></a>
            </td>
        </tr>
        </tbody>
</table>

</section>

<div id="qc-content">
    <%
        // manually load file contents instead of using c:import to avoid javax.servlet.jsp.JspTagException: 304 errors
        // see http://stackoverflow.com/questions/17218609/jsp-exception-when-i-try-to-import-static-file
        Path filePath = (Path)request.getAttribute("contentPath");
        IOUtils.copy(Files.newInputStream(filePath), pageContext.getOut());
    %>
</div>