const React = require('react');
require("./BioentityInformation.css");

const PropertyLinkShape = {
  text: React.PropTypes.string.isRequired,
  url: React.PropTypes.string.isRequired,
  relevance: React.PropTypes.number.isRequired
};

const BioentityPropertyShape = {
  type: React.PropTypes.string.isRequired,
  name: React.PropTypes.string.isRequired,
  values: React.PropTypes.arrayOf(React.PropTypes.shape(PropertyLinkShape)).isRequired
};

const BioentityPropertiesShape = {
  bioentityProperties: React.PropTypes.arrayOf(React.PropTypes.shape(BioentityPropertyShape))
};

const BioentityProperty = React.createClass({
  propTypes: BioentityPropertyShape,

  getInitialState() {
    return {
      showAll: false
    }
  },

  // take three most relevant links and then all of the same relevance
  _pickMostRelevant(properties) {
    const relevanceThreshold =
      properties
      .map(p => p.relevance)
      .sort((l,r) => r-l)
      .concat([0,0,0])
      [properties.size < 3 ? properties.size-1 : 2];
    return properties.filter(p => p.relevance>=relevanceThreshold);
  },

  _renderProperty(property, ix) {
    return (
      property.url
      ? <a key={property.url+" "+ix} className={"bioEntityCardLink"} href={property.url} target="_blank">
        {property.text}
        </a>
      : <span key={property.text + " "+ix}>
          {property.text}
        </span>
    )
  },

  _zipWithCommaSpans(elts) {
    return (
      [].concat.apply([],
        elts.map((e, ix)=>[e, <span key={"comma "+ix}>, </span>])
      )
      .slice(0,-1)
    )
  },

  render() {
    const numUnshownLinks =
      this.props.values.length - this._pickMostRelevant(this.props.values).length;
    const hasOptionalLinks =
      ["go","po"].indexOf(this.props.type)>-1
      && numUnshownLinks >0;


    return (
      <tr>
        <td className={"gxaBioentityInformationCardPropertyType"}>
          {this.props.name}
        </td>
        <td>
          <div>
          {hasOptionalLinks
            ?
              <span>
              {
                this._zipWithCommaSpans(
                  (this.state.showAll
                    ? this.props.values
                    : this._pickMostRelevant(this.props.values)
                  )
                  .sort((l,r)=>(
                      r.relevance === l.relevance
                      ? r.text.toLowerCase() < l.text.toLowerCase()
                        ? 1
                        : -1
                      : r.relevance - l.relevance

                    )
                  ).map(this._renderProperty)
                )
              }
              <a role="button" style={{cursor:"pointer"}}
               onClick={function(){
                 this.setState((previousState)=>({showAll: !previousState.showAll}))
               }.bind(this)}>
                {this.state.showAll
                  ? " (show less)"
                  : " … and "+numUnshownLinks+" more"}
              </a>
              </span>
            : this._zipWithCommaSpans(
              this.props.values.map(this._renderProperty)
            )
          }
          </div>
        </td>
      </tr>
    )
  }
});

const BioentityInformation = React.createClass({
  propTypes: BioentityPropertiesShape,

  render() {
    return (
      <div className={"gxaBioentityInformationCard"}>
        <table>
          <tbody>
          {this.props.bioentityProperties.map(function(bioentityProperty){
            return (
              <BioentityProperty
                key={bioentityProperty.type}
                {...bioentityProperty} />
            )
          })}
          </tbody>
        </table>
      </div>
    )
  }
});

module.exports = BioentityInformation;
