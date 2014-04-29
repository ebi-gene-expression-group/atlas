<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="error-content" class="block">
    <span>The query is not well formed: </span>
    <div class="error">
        ${exceptionMessage}
    </div>
    <br/>
    <div>Please try again.</div>
</div>
<div id="content" class="block">
    <a href="/gxa">Go to Expression Atlas home page</a>
</div>