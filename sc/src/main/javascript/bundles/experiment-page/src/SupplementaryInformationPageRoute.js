import React from 'react'
import PropTypes from 'prop-types'
import StaticTable from './supplementary-information/StaticTable'
import LinkToResource from './supplementary-information/LinkToResource'

const sectionTypeComponent = {
  'static-table' : StaticTable,
  'resources' : LinkToResource
}

const SectionContent = ({type, props}) => {
  const Section = sectionTypeComponent[type]

  return (
    Section ? <Section {...props} /> : null
  )

}
const SupplementaryInformationPageRoute = (props) => {
  console.log(props)
  const sections = props.sections.map((section) =>
    <div key={section.name}>
      <h4>{section.name}</h4>
      <SectionContent type={section.type} props={section.props}/>
    </div>
  )

  return (
    <div>
      {sections}
    </div>
  )
}

SupplementaryInformationPageRoute.propTypes = {
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

export default SupplementaryInformationPageRoute