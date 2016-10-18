<%--@elvariable id="exceptionMessage" type="java.lang.String"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section class="gxaSection">
    <div class="gxaError">
        <h4>The query is not well formed</h4>
        <c:if test="${not empty exceptionMessage}">
            <h5>${exceptionMessage}</h5>
        </c:if>
    </div>
</section>

<section class="gxaSection">
    <h4>Please fix your query and try again.</h4>
    <p><a style="font-weight: bold" href="/gxa">Go to Expression Atlas home page</a></p>
</section>
