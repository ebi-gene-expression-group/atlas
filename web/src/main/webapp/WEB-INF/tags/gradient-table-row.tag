<%@ attribute name="lowValueColour" required="true" %>
<%@ attribute name="highValueColour" required="true" %>

<%@ attribute name="highValueColorExpressionLevel" required="true" %>
<%@ attribute name="lowValueColorExpressionLevel" required="true" %>

<div style="display: table-row">
    <div style="display: table-cell; white-space: nowrap" class="gxaGradientLevelMin">${lowValueColorExpressionLevel}</div>
    <div style="display: table-cell">
        <span class="gxaGradientColour"
              style="overflow: auto; vertical-align: middle;
                     background-image: -webkit-gradient(linear, left top, right top,color-stop(0, ${lowValueColour}), color-stop(1, ${highValueColour}));
                     background-image: -moz-linear-gradient(left, ${lowValueColour}, ${highValueColour});
                     background-image: -o-linear-gradient(left, ${lowValueColour}, ${highValueColour});
                     filter: progid:DXImageTransform.Microsoft.Gradient(GradientType =1, startColorstr=${lowValueColour},endColorstr=${highValueColour});
                     width: 200px; height: 15px; margin: 2px 6px 2px 6px; display: inline-block"></span>
    </div>
    <div style="display: table-cell; white-space: nowrap" class="gxaGradientLevelMax">${highValueColorExpressionLevel}</div>
</div>