import React from 'react'
import ReactTable from 'react-table'
import {uniq, curry} from 'lodash'
import toPlural from 'pluralize'
import '!style-loader!css-loader!react-table/react-table.css'
import '!style-loader!css-loader!./experiment-table.css'

const aggregateText = (name, vals) => {
  const xs = uniq(vals)
  return (
    xs.length === 1 || (xs.length < 5 && xs.join(', ').length < 30)
      ? xs.join(', ')
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
              width: 95
            }))
        }
      ))
    }
    className='-striped'
    style={{
      fontSize: 'small'
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
          values: [[properties.analysed? 'Yes' : 'No']].concat(values)
        })),
    headers:
      [{name:"", values: ['Analysed']}].concat(headers)
  })
)

export default BaselineExperimentDesign