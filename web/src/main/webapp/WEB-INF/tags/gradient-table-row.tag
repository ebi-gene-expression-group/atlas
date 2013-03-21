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

<%@ attribute name="minExpressionLevel" required="true" %>
<%@ attribute name="maxExpressionLevel" required="true" %>

<tr>
    <td>
        <span style="display:none" class="gradient-level-min">
            ${maxExpressionLevel}
        </span>
    </td>
    <td width="200px">
        <div class="color-gradient" style="
                overflow:auto;
                background-image:
                -webkit-gradient(linear, left top, right top,color-stop(0, ${lowValueColour}), color-stop(1, ${highValueColour}));

                background-image: -moz-linear-gradient(left, ${lowValueColour}, ${highValueColour});

                background-image: -o-linear-gradient(left, ${lowValueColour}, ${highValueColour});

                filter:progid:DXImageTransform.Microsoft.Gradient(GradientType =1,
                startColorstr=${lowValueColour},endColorstr=${highValueColour});">
            &nbsp;
        </div>
    </td>
    <td>
        <span style="display:none" class="gradient-level-max">
            ${minExpressionLevel}
        </span>
    </td>
</tr>