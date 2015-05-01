<%--
  ~ Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~ http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  ~
  ~
  ~ For further details of the Gene Expression Atlas project, including source code,
  ~ downloads and documentation, please see:
  ~
  ~ http://gxa.github.com/gxa
  --%>

<%@ attribute name="lowValueColour" required="true" %>
<%@ attribute name="highValueColour" required="true" %>

<%@ attribute name="highValueColorExpressionLevel" required="true" %>
<%@ attribute name="lowValueColorExpressionLevel" required="true" %>

<div style="display: table-row">
    <div style="visibility: hidden; display: table-cell; white-space: nowrap" class="gxaGradientLevelMin"> ${lowValueColorExpressionLevel}</div>
    <div style="display: table-cell">
        <span class="color-gradient"
              style="overflow: auto; vertical-align: middle;
                     background-image: -webkit-gradient(linear, left top, right top,color-stop(0, ${lowValueColour}), color-stop(1, ${highValueColour}));
                     background-image: -moz-linear-gradient(left, ${lowValueColour}, ${highValueColour});
                     background-image: -o-linear-gradient(left, ${lowValueColour}, ${highValueColour});
                     filter: progid:DXImageTransform.Microsoft.Gradient(GradientType =1, startColorstr=${lowValueColour},endColorstr=${highValueColour});
                     width: 200px; height: 15px; margin: 2px 6px 2px 6px; display: inline-block"></span>
    </div>
    <div style="visibility: hidden; display: none; white-space: nowrap" class="gxaGradientLevelMax">${highValueColorExpressionLevel}</div>
</div>