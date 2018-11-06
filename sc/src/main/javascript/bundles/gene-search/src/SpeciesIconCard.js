import React from 'react'
import PropTypes from 'prop-types'
import EbiSpeciesIcon from 'react-ebi-species'

const SpeciesIconCard = ({iconSrc, description}) => {
    return (
        <div className={`column column-block text-center combo card`} style={{marginBottom:0, paddingBottom: `25px`}}>
      <span className={`species-icon`} style={{fontSize: `600%`}}>
        <EbiSpeciesIcon species={iconSrc}/>
      </span>

            {
                description && <h5 className="species-name">{description}</h5>
            }
        </div>
    )
}

SpeciesIconCard.propTypes = {
    iconSrc: PropTypes.string.isRequired,
    description: PropTypes.string
}

export default SpeciesIconCard

