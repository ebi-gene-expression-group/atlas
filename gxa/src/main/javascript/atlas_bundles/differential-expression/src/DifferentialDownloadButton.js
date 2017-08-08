import React from 'react'
import PropTypes from 'prop-types'

const jsonToTsv = (results)  => {
  const arrayResults = typeof results !== `object` ? JSON.parse(results) : results

  return (
    [
      [`Gene`, `Organism`, `Experiment Accession`, `Comparison`, `log2foldchange`, `pValue`]
        .concat(arrayResults.some((diffResults) => diffResults.tStatistics !== null) ? [`tStatistics`] :[])
        .join(`\t`)
    ].concat(
      arrayResults
        .map((diffResults) =>
          [
            diffResults.bioentityIdentifier,
            diffResults.species,
            diffResults.experimentAccession,
            diffResults.comparison,
            diffResults.foldChange,
            diffResults.pValue,
            diffResults.tStatistics
          ]
            .filter(el => el !== null)    // tStatistics might be missing
          // .join(`\t`)
        )
    ).join(`\n`)
  )
}

const DownloadDifferentialButton = ({results}) =>
  <a className={`button`}
     download={`differentialResults.tsv`}
     href={`data:text/tsv;charset=utf-8,${encodeURI(jsonToTsv(results))}`}
     target={`_blank`}>
    <span className="icon icon-functional" data-icon="="> Download results</span>
  </a>

DownloadDifferentialButton.propTypes = {
  results: PropTypes.arrayOf(PropTypes.shape({
    species: PropTypes.string.isRequired,
    kingdom: PropTypes.string.isRequired,
    experimentType: PropTypes.string.isRequired,
    numReplicates: PropTypes.number.isRequired,    // faceting only works with strings https://issues.apache.org/jira/browse/SOLR-7496
    regulation: PropTypes.string.isRequired,
    factors: PropTypes.arrayOf(PropTypes.string).isRequired,
    bioentityIdentifier: PropTypes.string.isRequired,
    experimentAccession: PropTypes.string.isRequired,
    experimentName: PropTypes.string.isRequired,
    contrastId: PropTypes.string.isRequired,
    comparison: PropTypes.string.isRequired,
    foldChange: PropTypes.number.isRequired,
    pValue: PropTypes.number.isRequired,
    tStatistics: PropTypes.number,
    colour: PropTypes.string.isRequired,
    id: PropTypes.string.isRequired
  })).isRequired
}

export default DownloadDifferentialButton
