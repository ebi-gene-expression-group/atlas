import React from 'react'
import PropTypes from 'prop-types'
import AnalysisMethodsTable from './supplementary-information/AnalysisMethodsTable'
import LinksToResources from './supplementary-information/LinksToResources'

const sectionTypeComponent = {
  'static-table' : AnalysisMethodsTable,
  'resources' : LinksToResources
}

const SectionContent = ({type, props}) => {
  const Section = sectionTypeComponent[type]

  return (Section && <Section {...props} />)
}

const SupplementaryInformationRoute = (props) => {
  const sections = props.sections.map((section) =>
    <div key={section.name}>
      <h3>{section.name}</h3>
      <SectionContent type={section.type} props={section.props}/>
    </div>
  )

  return (
    <div className={`row expanded margin-top-large`}>
      <div className={`small-12 columns`}>
        {sections}
      </div>
    </div>
  )
}

SupplementaryInformationRoute.propTypes = {
  match: PropTypes.object.isRequired,
  location: PropTypes.object.isRequired,
  history: PropTypes.object.isRequired,
  atlasUrl: PropTypes.string.isRequired,
  resourcesUrl: PropTypes.string,
  experimentAccession: PropTypes.string.isRequired,
  sections: PropTypes.arrayOf(PropTypes.shape({
    name: PropTypes.string.isRequired,
    type: PropTypes.string.isRequired,
    props: PropTypes.object.isRequired
  }))
}

export default SupplementaryInformationRoute