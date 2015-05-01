<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="error-content" class="gxaBlock">
    <span>The query is not well formed: </span>
    <div class="gxaError">
        ${exceptionMessage}
    </div>
    <br/>
    <div>Please try again.</div>
</div>
<div id="content" class="gxaBlock">
    <a href="/gxa">Go to Expression Atlas home page</a>
</div>