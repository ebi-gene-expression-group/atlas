import React from 'react'
import PropTypes from 'prop-types'
import StaticTable from './supplementary-information/StaticTable'
import LinksToResources from './supplementary-information/LinksToResources'

const sectionTypeComponent = {
  'static-table' : StaticTable,
  'resources' : LinksToResources
}

const SectionContent = ({type, props}) => {
  const Section = sectionTypeComponent[type]

  return (
    Section ? <Section {...props} /> : null
  )

}
const SupplementaryInformationRoute = (props) => {
  const sections = props.sections.map((section) =>
    <div key={section.name}>
      <h4>{section.name}</h4>
      <SectionContent type={section.type} props={section.props}/>
    </div>
  )

  return (
    <div className={"margin-top-large"}>
      {sections}
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