<%--@elvariable id="propertyNames" type="java.util.Map"--%>
<%--@elvariable id="relevantGoPoLinks" type="java.util.List"--%>
<%--@elvariable id="allGoPoLinks" type="java.util.List"--%>

<div class="row expanded">
    <div class="small-12 columns">
        <div id="bioentityInformationTab"></div>
    </div>
</div>

<script>
    $(function() {
        expressionAtlasBioentityInformation.render({
            target: 'bioentityInformationTab',
            payload: ${bioentityProperties}
        })
    })
</script>
