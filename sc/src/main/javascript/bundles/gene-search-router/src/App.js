// eslint-disable-next-line
import React, { Component } from 'react';
import './App.css';
import {withRouter} from 'react-router-dom';
import SwitchRoute from './SwitchRoute';
import queryString from 'query-string';
import GeneSearchForm from 'scxa-gene-search-form'
import EpisodeCard from './EpisodeCard';
import ExperimentCard from './ExperimentCard';

const formprops = {
      atlasUrl: 'http://localhost:8080/gxa/sc/',
      wrapperClassName: 'row',
      actionEndpoint: 'search',
      suggesterEndpoint: 'json/suggestions/gene_ids',
      autocompleteClassName: 'small-8 columns',
      enableSpeciesSelect: true ,
      enableSubmitButton: false,
      speciesEndpoint: 'json/suggestions/species',
      speciesSelectClassName: 'small-4 columns',
      defaultSpecies: 'Any',
      selectedSpecies:'',
      allSpecies: ["Homo sapiens", "Mus musculus"],
      defaultValue: {
        term: 'foo',
        category: 'bar'
      },
      speciesSelectStatusMessage:''
    };


class App extends Component {
  
  constructor(props){
    super(props)
    this.state = {
      value: '' ,
      selectedSpecies: '',
      category: '',
    }
    this.handleSubmit = this.handleSubmit.bind(this)
    this.speciesSelectOnChange = this._speciesSelectOnChange.bind(this)
  }

  async handleSubmit(val){
    await this.setState({
      value : val.term,
      category: val.category
    });

    if(this.state.selectedSpecies===undefined){
     await this.setState({
        selectedSpecies: ''
      })
    }
   
    this.props.history.push({
      pathname: `/gxa/sc/search?${val.category}=${this.state.value}&species=${this.state.selectedSpecies}`,
    })
    
  }


  async _speciesSelectOnChange(event) {
    event.persist();
    await this.setState({ selectedSpecies: event.target.value });

    this.props.history.push({
        pathname:  `/gxa/sc/search?${this.state.category}=${this.state.value}&species=${this.state.selectedSpecies}`
    })
  }

  componentWillMount() {
    const values = queryString.parse(this.props.location.search);
    let category =Object.keys(values).filter(val => val!=='species')[0];
    let value = values[category];
    console.log('card',EpisodeCard);
    this.setState({
      category: category,
      selectedSpecies: values.species,
      value: value
     
    })
    if(Object.keys(values)[1]){
      this.props.history.push({
          pathname:  `/gxa/sc/search?${category}=${value}&species=${values.species}`
      })
    }
  }

  render() {
    return (     
      <div className="App">
        <div className={formprops.wrapperClassName}>
            <GeneSearchForm {...formprops} currentValue ={this.state.value} currentSpecies={this.state.selectedSpecies} onChange={this.handleSubmit} speciesSelectOnChange={this.speciesSelectOnChange}/>
        </div>
        
        <SwitchRoute ResultElementClass={ExperimentCard} value={this.state.value} species={this.state.selectedSpecies} category={this.state.category}/>
      </div>
    );
  }
}

export default withRouter(App);
