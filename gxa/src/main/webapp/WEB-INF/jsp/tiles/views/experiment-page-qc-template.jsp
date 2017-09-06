<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="uk.ac.ebi.atlas.experimentpage.qc.QCReportUtil" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/libraries/foundation-6/css/foundation.css" type="text/css" media="all">
<link rel="stylesheet" href="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/css/ebi-global.css" type="text/css" media="all">
<link rel="stylesheet" href="https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.1/fonts.css" type="text/css" media="all">

<!-- JavaScript -->
<script src="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/js/cookiebanner.js"></script>
<script src="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/js/foot.js"></script>
<script src="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/js/script.js"></script>

<!-- The Foundation theme JavaScript -->
<script src="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/libraries/foundation-6/js/foundation.js"></script>
<script src="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/js/foundationExtendEBI.js"></script>
<script>$(document).foundation();</script>
<script>$(document).foundationExtendEBI();</script>

<style>
    h1 {
        color:cadetblue;
    }
</style>

<div id="qc-content" class="row expanded">
    <div class="small-12 columns">
        <%
            // manually load file contents instead of using c:import to avoid javax.servlet.jsp.JspTagException: 304 errors
            // see http://stackoverflow.com/questions/17218609/jsp-exception-when-i-try-to-import-static-file
            QCReportUtil.printContent(request, pageContext.getOut());
        %>
    </div>
</div>

