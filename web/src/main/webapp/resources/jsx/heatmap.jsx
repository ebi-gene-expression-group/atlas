/** @jsx React.DOM */


var experimentAccession = 'E-GEOD-26284';


var factors = [
  { factorName: "factor1", svgPathId: "testpath", assayGroupId: "g71"},
  { factorName: "factor2", assayGroupId: "g16"},
]

var profiles = [
  { geneId: "geneId1", geneName: "Gene 1", 
    expressions: [ {factorName: "factor1", color: "#BFBFC0", value: 1.03, svgPathId: "testpath"},
                   {factorName: "factor2", color: "#2F30CF", value: 2}
                 ]
  }
  ,{ geneId: "geneId2", geneName: "Gene 2", 
    expressions: [ {factorName: "factor1", color: "#BFBFC0", value: 3, svgPathId: "testpath"},
                   {factorName: "factor2", color: "#2F30CF", value: 4}
                 ]
  }
];


var HeatmapTableHeader = React.createClass({

  render: function() {
    var experimentAccession = this.props.experimentAccession;
    var factorNames = this.props.factors.map(function (factor) {
      return <HeatmapTableHeaderFactorNames factorName={factor.factorName} svgPathId={factor.svgPathId} assayGroupId={factor.assayGroupId} experimentAccession={experimentAccession}/>;
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

var HeatmapTableHeaderFactorNames = React.createClass({

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

  render: function() {
    return (
        <th className="rotated_cell vertical-header-cell factorNameCell" rowSpan="2">
          <div data-organism-part={this.props.factorName} data-svg-path-id={this.props.svgPathId} assay-group-id={this.assayGroupId} factor-name={this.props.factorName} data-experiment-accession={this.props.experimentAccession} className="factor-header rotate_text">{this.restrictLabelSize(this.props.factorName)}</div>
        </th>
    );
  }
});

var HeatmapTableHeaderTopLeftCorner = React.createClass({
  render: function() {

    return (
      <th className="horizontal-header-cell">
        <div className="heatmap-matrix-top-left-corner">
          <span id='tooltip-span' data-help-loc='#heatMapTableCellInfo'></span>
          <DisplayLevelsButton />
        </div>
      </th>
    );
  }
});

var DisplayLevelsButton = React.createClass({
  render: function() {

    return (
          <button id='display-levels' className='display-levels-button' />
    );
  }
});


var HeatmapTableBody = React.createClass({
  render: function() {
    var geneProfilesRows = this.props.profiles.map(function (profile) {
      return <GeneProfileRow geneId={profile.geneId} geneName={profile.geneName} expressions={profile.expressions}/>;
    });

    return (
      <tbody>
        {geneProfilesRows}
      </tbody>
    );
  }
});

var GeneProfileRow = React.createClass({
  render: function() {

    var heatMapCells = this.props.expressions.map(function (expression) {
      return <HeatmapCell factorName={expression.factorName} color={expression.color} value={expression.value} showValue="false" svgPathId={expression.svgPathId}/>
    });

    return (
      <tr>
        <td className="horizontal-header-cell">
          <a className="genename" id={this.props.geneId} href={"http://localhost:8080/gxa/genes/" + this.props.geneId}>{this.props.geneName}</a>
        </td>
        {heatMapCells}
      </tr>
    );
  }
});


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


React.renderComponent(
  <table id="heatmap-table" className="table-grid">
    <HeatmapTableHeader factors={factors} experimentAccession={experimentAccession}/>
    <HeatmapTableBody profiles={profiles} />
  </table>,
  document.getElementById('example')
);