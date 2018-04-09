import React from 'react'
import PropTypes from 'prop-types'

import Linkify from 'linkifyjs/react'

const Table = ({data}) => (
  <div className="row column expanded">
    <table>
      <thead>
        <tr>
          {
            data[0].map((element, index) => (
              <th key={index}>{element}</th>
            ))
          }
        </tr>
      </thead>
      <tbody>
      {
        data.slice(1, data.length).map((row, index) => (
          <tr key={index}>
            {
              row.map( (element, index) => (
                <td key={index}>
                  <Linkify>{element}</Linkify>
                </td>
              ))
            }
          </tr>
        ))
      }
      </tbody>
    </table>
  </div>
)

Table.propTypes = {
  data: PropTypes.arrayOf(PropTypes.arrayOf(PropTypes.string)).isRequired
}

export default Table
