<%--@elvariable id="exceptionMessage" type="java.lang.String"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section class="gxaSection">
    <div class="gxaError">
        <c:if test="${not empty exceptionMessage}">
            <p>${exceptionMessage}</p>
        </c:if>
    </div>
</section>

<section class="gxaSection">
    <p><a style="font-weight: bold" href="/gxa">Go to Expression Atlas home page</a></p>
</section>
