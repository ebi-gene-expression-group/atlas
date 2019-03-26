import React from 'react'
import styled from 'styled-components'
import PropTypes from "prop-types"

const CardContainerDiv = styled.div`
  height: 100%;
  width: 100%;
  display: flex !important;
  flex-wrap: nowrap;
  align-items: center;
  padding: 1rem;
  font-weight: bolder;
  font-size: 0.9rem;
`

const IconDiv = styled.div`
  width: 15%;
  text-align: center;
  cursor: pointer;
  opacity: 0.6;
  transition: 0.3s;
  :hover {opacity: 1};
`

const MarkerDiv = styled.div`
  width: 15%;
  text-align: center;
  cursor: pointer;
  opacity: 0.6;
  transition: 0.3s;
  :hover {opacity: 1};
`

const TitleDiv = styled.div`
  width: 40%;
  text-align: center;
  cursor: pointer;
  opacity: 0.6;
  transition: 0.3s;
  :hover {opacity: 1};
`

const VariableDiv = styled.div`
  width: 20%;
  text-align: center;
  opacity: 0.6;
`

const CountDiv = styled.div`
  width: 10%;
  text-align: center;
  cursor: pointer;
  opacity: 0.6;
  transition: 0.3s;
  :hover {opacity: 1};
`

const ExperimentTableHeaderBasic = () =>
  ({
    'titles': [`Species`, `Marker genes`, `Title`, `Experimental variables`, `Number of assays`],
    'styles': [IconDiv, MarkerDiv, TitleDiv, VariableDiv, CountDiv],
    'attributes': [`species`, `markerGenes`, `experimentDescription`, null, `numberOfAssays`]
  })


class ExperimentsHeader extends  React.Component {
  constructor(props) {
    super(props)

    this.state = {
      sortTitle: `markerGenes`,
      ascending: false
    }

    this.onClick = this.onClick.bind(this)
  }

  onClick(attribute){
    this.props.onClick(attribute)
    this.setState({
      sortTitle: attribute,
      ascending: !this.state.ascending
    })
  }

  render() {
    const tableTitles = ExperimentTableHeaderBasic().titles
    const tableTitleDivs = ExperimentTableHeaderBasic().styles
    const jsonAttributes = ExperimentTableHeaderBasic().attributes

    return(
      <CardContainerDiv>
        {
          tableTitles.map((title, index) => {
            const TitleDiv = tableTitleDivs[index]
            const attribute = jsonAttributes[index]
            return attribute ?
              attribute === this.state.sortTitle ?
                <TitleDiv key={title} style={{opacity: 1}}><span id={`selected`} onClick={() => this.onClick(attribute)}>{`${title} `}
                  {this.state.ascending ? <i className="icon icon-common icon-sort-up"/> : <i className="icon icon-common icon-sort-down"/>}</span></TitleDiv>
                : <TitleDiv key={title}><span id={`title`} onClick={() => this.onClick(attribute, this.state.ascending)}>{title} <i className={`icon icon-common icon-sort`}/></span></TitleDiv>
              : <TitleDiv key={title}><span id={`title`}>{title}</span></TitleDiv>
          })
        }
      </CardContainerDiv>
    )
  }
}

ExperimentsHeader.propTypes = {
  onClick: PropTypes.func.isRequired
}

export default ExperimentsHeader
