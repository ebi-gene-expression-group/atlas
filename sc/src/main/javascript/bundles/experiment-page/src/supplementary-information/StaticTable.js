import React from 'react'
import PropTypes from 'prop-types'

const Table = ({data}) => (
  <div className="row column expanded">
    <table>
      <thead>
        <tr>
          <th>Analysis Step</th>
          <th>Description</th>
        </tr>
        </thead>
      <tbody>
      {
        data.map((row, index) => (
          <tr key={index}>
            {
              row.map( (element, index) => (
                <td key={index}>
                  <div dangerouslySetInnerHTML={{__html: element}} />
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
