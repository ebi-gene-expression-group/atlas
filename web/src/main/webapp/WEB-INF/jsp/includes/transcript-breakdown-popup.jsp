<!-- transcript uses fancybox, which is included in parent layout -->

<!--[if lte IE 8]>
<script language="JavaScript" type="text/javascript"
src="${pageContext.request.contextPath}/resources/js/flot/excanvas.min.js"></script>
<![endif]-->

<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/flot/jquery.flot.js"></script>
<script language="JavaScript" type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/flot/jquery.flot.pie.js"></script>

<div id="transcript-breakdown" style="display:none;height: 320px;width: 500px; padding-top:10px">
    <p style="text-align: center">
        <span id="transcript-breakdown-title"></span>
            <span id="transcript-breakdown-title-help">
                <a class="help-icon" href="#"
                   title="Transcripts with zero expression are excluded from the pie chart. Transcripts shown in white colour have been reported with low confidence.">?</a>
            </span>
    </p>

    <div>
        <div id="transcripts-pie" style="width: 500px;height:250px;">
        </div>
    </div>
</div>