import React from 'react'
import PropTypes from 'prop-types'

import ReactTable from 'react-table'
import { uniq, curry } from 'lodash'
import toPlural from 'pluralize'

import ReactTableStyle from './ReactTableStyle'

const aggregateText = (name, vals) => {
  const xs = uniq(vals)
  return (
    xs.length === 1 || (xs.length < 5 && xs.join(`, `).length < 30)
      ? xs.join(`, `)
      : toPlural(name.toLowerCase() , xs.length, true)
  )
}

const ExperimentDesignTable = ({data, headers}) =>
  <div>
    <ReactTableStyle/>
    <ReactTable
      columns={
        headers.map((headerGroup, ix)=> (
          {
            Header: headerGroup.name,
            columns:
              headerGroup.values.map((header, jx) => ({
                aggregate: curry(aggregateText, 2)(header),
                Header: header,
                id: ix*1000 +jx +1,
                accessor: r => r.values[ix][jx]
              }))
          }
        ))
      }
      className={`-striped`}
      style={{fontSize: `small`}}
      data={data} />
  </div>

ExperimentDesignTable.propTypes = {
  data: PropTypes.array.isRequired,
  headers: PropTypes.array.isRequired
}

export default ExperimentDesignTable