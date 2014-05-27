/** @jsx React.DOM */
/*global React */
var HeatmapTableHeader = React.createClass({

  render: function() {
    var experimentAccession = this.props.experimentAccession;
    var factorNames = this.props.assayGroupFactors.map(function (assayGroupFactor) {
      var factor = assayGroupFactor.factor;
      return <HeatmapTableHeaderFactorNames factorName={factor.value} svgPathId={factor.valueOntologyTerm} assayGroupId={assayGroupFactor.assayGroupId} experimentAccession={experimentAccession}/>;
    });

    return (
      <thead>
        <HeatmapTableHeaderTopLeftCorner />
        {factorNames}
        <tr id="injected-header"><td className="horizontal-header-cell">Gene</td></tr>
      </thead>
    );
  }
});

var HeatmapTableHeaderFactorNames = (function (factorInfoTooltipModule, contextRoot, accessKey) {
  return React.createClass({
    restrictLabelSize:  function (label, maxSize) {
          var result = label;
          if (result.length > maxSize) {
              result = result.substring(0, maxSize);
              if (result.lastIndexOf(" ") > maxSize - 5) {
                  result = result.substring(0, result.lastIndexOf(" "));
              }
              result = result + "...";
          }
          return result;
    },

    componentDidMount: function() {
      factorInfoTooltipModule.init(contextRoot, accessKey, this.getDOMNode());
    },

    render: function() {
      var truncatedFactorName = this.restrictLabelSize(this.props.factorName, 17);
      return (
          <th className="rotated_cell vertical-header-cell factorNameCell" rowSpan="2">
            <div data-organism-part={this.props.factorName} data-svg-path-id={this.props.svgPathId} data-assay-group-id={this.props.assayGroupId} data-experiment-accession={this.props.experimentAccession} className="factor-header rotate_text">{truncatedFactorName}</div>
          </th>
      );
    }
  });
})(factorInfoTooltipModule, '/gxa', '');

var HeatmapTableHeaderTopLeftCorner = (function (helpTooltipsModule, contextRoot) {
  return React.createClass({
      render: function () {
          return (
              <th className="horizontal-header-cell">
                  <div className="heatmap-matrix-top-left-corner">
                      <span id='tooltip-span' data-help-loc='#heatMapTableCellInfo' ref='tooltipSpan'></span>
                      <DisplayLevelsButton />
                  </div>
              </th>
          );
      },

      componentDidMount: function () {
          helpTooltipsModule.init('experiment', contextRoot, this.refs.tooltipSpan.getDOMNode());
      }
  });
})(helpTooltipsModule, '/gxa/', '');

var DisplayLevelsButton = React.createClass({
  render: function() {

    return (
          <button id='display-levels' className='display-levels-button' />
    );
  }
});


var HeatmapTableBody = React.createClass({
  render: function() {
    var toolTipHighlightedWords = this.props.toolTipHighlightedWords;
    var geneProfilesRows = this.props.profiles.map(function (profile) {
      return <GeneProfileRow geneId={profile.geneId} geneName={profile.geneName} expressions={profile.expressions} toolTipHighlightedWords={toolTipHighlightedWords}/>;
    });

    return (
      <tbody>
        {geneProfilesRows}
      </tbody>
    );
  }
});

var GeneProfileRow = (function (genePropertiesTooltipModule, contextRoot) {
    return React.createClass({
        render: function() {

            var heatMapCells = this.props.expressions.map(function (expression) {
                return <HeatmapCell factorName={expression.factorName} color={expression.color} value={expression.value} showValue="false" svgPathId={expression.svgPathId}/>
            });

            // NB: empty title tag below is required for tooltip to work
            return (
                <tr>
                    <td className="horizontal-header-cell">
                        <a className="genename" ref="geneName" title="" id={this.props.geneId} href={"http://localhost:8080/gxa/genes/" + this.props.geneId}>{this.props.geneName}</a>
                    </td>
                    {heatMapCells}
                </tr>
                );
        },

        componentDidMount: function() {
            genePropertiesTooltipModule.init(contextRoot, this.props.toolTipHighlightedWords, this.refs.geneName.getDOMNode());
        }
    });
})(genePropertiesTooltipModule, '/gxa');

var HeatmapCell = React.createClass({
  render: function() {
    return (
      <td style={{"background-color" : this.props.color}}>
        <div 
          className={this.props.showValue == "true" ? "show_cell" : "hide_cell"} 
          data-organism-part={this.props.factorName}
          data-color={this.props.color} 
          data-svg-path-id={this.props.svgPathId}>
            {this.props.value}
        </div>
      </td>
    );
  }
});
