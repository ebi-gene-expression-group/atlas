import React from 'react'
import ReactTable from 'react-table'
import PropTypes from 'prop-types'
import URI from 'urijs'
import {uniq, curry} from 'lodash'
import toPlural from 'pluralize'
import 'react-table/react-table.css'
import TSnePlotViewRoute from './TSnePlotViewRoute'

//http://stackoverflow.com/questions/196972/convert-string-to-title-case-with-javascript
const toTitleCase = (str) => str.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});

const aggregateText = (name, vals) => {
  const xs = uniq(vals)
  return (
    xs.length === 1 || (xs.length < 5 && xs.join(", ").length < 30)
      ? xs.join(", ")
      : toPlural(name.toLowerCase() , xs.length, true)
  )
}

const ExperimentDesign = ({
                            data,
                            headers,
                            options={}
                          }) => (
  <ReactTable
    columns={
      headers.map((headerGroup,ix)=> (
        {
          Header: headerGroup.name,
          columns:
            headerGroup.values.map((header, jx) => ({
              aggregate: curry(aggregateText, 2)(header),
              Header: header,
              id: ix*1000 +jx +1,
              accessor: r => r.values[ix][jx],
            }))
        }
      ))
    }
    className="-striped"
    style={{
      fontSize: "small"
    }}
    data={data}
    {...options}
  />
)

const BaselineExperimentDesign = ({
                                    data,
                                    headers
                                  }) => (
  ExperimentDesign({
    data:
      data
        .map(({properties, values}) => ({
          values: [[properties.analysed? "Yes" : "No"]].concat(values)
        })),
    headers:
      [{name:"", values: ["Analysed"]}].concat(headers)
  })
)


const ExperimentDesignRoute = (props) => {

  return (
    <div>
      <div>
        <div className="row expanded column margin-top-large">
          <a className="button float-right margin-bottom-none" href={URI(props.downloadUrl, props.atlasUrl).toString()}>
            <span className="icon icon-functional" data-icon="="/>
            Download
          </a>
        </div>
        <div className="row expanded column margin-top-large">
          <BaselineExperimentDesign data={props.table.data} headers={props.table.headers}/>
        </div>
      </div>

    </div>


  )
}

ExperimentDesignRoute.propTypes = {
  match: PropTypes.object.isRequired,
  location: PropTypes.object.isRequired,
  history: PropTypes.object.isRequired,
  atlasUrl: PropTypes.string.isRequired,
  resourcesUrl: PropTypes.string,
  experimentAccession: PropTypes.string.isRequired,
  downloadUrl: PropTypes.string.isRequired,
  table: PropTypes.arrayOf(PropTypes.shape({
    properties: PropTypes.oneOfType([
      PropTypes.shape({
        analysed: PropTypes.bool.isRequired
      }).isRequired
    ]),
    values: PropTypes.arrayOf(PropTypes.arrayOf(PropTypes.string.isRequired).isRequired).isRequired
  }).isRequired).isRequired,
  headers: PropTypes.arrayOf(PropTypes.shape({
    name: PropTypes.string.isRequired,
    values: PropTypes.arrayOf(PropTypes.string.isRequired).isRequired
  }).isRequired).isRequired
}


export default ExperimentDesignRoute