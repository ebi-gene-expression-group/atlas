"use strict";

//*------------------------------------------------------------------*
var React = require('react');

var Results= require('./DifferentialResults.jsx');
var Facets = require('./DifferentialFacetsTree.jsx');

var $ = require('jquery');
$.ajaxSetup({ traditional:true });

var url = require('url');
var querystring = require('querystring');
var pushQueryIntoBrowserHistory = require('./urlMaintainer.js').differential;


//*------------------------------------------------------------------*

//*------------------------------------------------------------------*
/*
TODO if Solr queries get fast enough that we can reload facets after changing the results
- split the two requests, so that the facets load first, initial results load second
- maintain the state {filter, loading}
*/

var RequiredString = React.PropTypes.string.isRequired;
var DifferentialTabLoader = React.createClass({
  propTypes: {
    host: RequiredString,
    identifier: RequiredString,
    geneQuery: RequiredString,
    conditionQuery : RequiredString,
    species: RequiredString
  },

  getInitialState: function(){
    return {
      facetsTreeData: {},
      resultsData:{
        results: [],
        maxDownLevel: 0,
        minDownLevel: 0,
        minUpLevel: 0,
        maxUpLevel: 0
      },
      querySelect: {}
    }
  },

  componentDidMount: function(){
    this._loadInitialData();
  },

  /**
   * Parse the `ds` search field in the URL
   */
  _parseSelectedFacetsFromLocation: function() {
      var currentURL = url.parse(window.location.toString());
      var selectString = querystring.parse(currentURL.query)["ds"];
      return selectString ? JSON.parse(selectString) : {};
  },

  setChecked: function (checked, facet, facetItem) {
    var querySelect = this.state.querySelect;
    if (checked) {
      if (!querySelect[facet]) {
          querySelect[facet] = {};
      }
      querySelect[facet][facetItem] = true;
    } else {
      querySelect[facet][facetItem] = false;
    }
    pushQueryIntoBrowserHistory(querySelect, false);
    //filterAndRenderResults();
    this.setState({querySelect:querySelect})
  },

  _filteredResults: function(querySelect) {
    var query = querySelect || this.state.querySelect;
    return this.state.resultsData.results.filter(function(result) {
        for (var facetName in query) {
            if (query.hasOwnProperty(facetName)) {

                var hasCheckedItemsInThisFacet = false;
                var facetMatch = false;

                for (var facetItem in query[facetName]) {
                    if (query[facetName].hasOwnProperty(facetItem)) {

                        if (query[facetName][facetItem]) {
                            hasCheckedItemsInThisFacet = true;

                            if (result[facetName].constructor === Array) {
                                facetMatch = facetMatch || result[facetName].indexOf(facetItem) != -1;
                            }
                            else {
                                facetMatch = facetMatch || result[facetName] === facetItem;
                            }
                        }

                    }
                }

                if (hasCheckedItemsInThisFacet && !facetMatch) {
                    return false;
                }
            }
        }

        return true;
    });
  },

  _prepareFacetData: function filterAndRenderResults() {

      var filteredResults = this._filteredResults();

      var disabledCheckedFacets = {};
      var disabledUncheckedFacets = {};

      var selectedFacets = [];
      for (var facet in this.state.querySelect) {
          var itemFacet = this.state.querySelect[facet];
          for (var item in itemFacet) {
              if (itemFacet[item]) {
                  selectedFacets.push(item);
              }
          }
      }

      var notSelectedFacets = {};
      for (var facet in this.state.facetsTreeData) {
          var facetItems = this.state.facetsTreeData[facet];
          var unSelectedFacets = [];
          for (var item in facetItems) {
              var facetItem = facetItems[item];
              if (selectedFacets.indexOf(facetItem.name) === -1) {
                  unSelectedFacets.push(facetItem);
              }
          }
          notSelectedFacets[facet] = unSelectedFacets;
      }

      for (var facet in notSelectedFacets) {
          var facetNotExistsValue = [];
          var _query = this.state.querySelect; //The modifications to this will probably not persist

          for (var item in notSelectedFacets[facet]) {
              var facetItem = notSelectedFacets[facet][item];

              if (_query.hasOwnProperty(facet)) {
                  _query[facet][facetItem.name] = true;
              } else {
                  var _item = {};
                  _item[facetItem.name] = true;
                  _query[facet] = _item;
              }

              var queryResults = this._filteredResults(_query);
              if (queryResults.length === 0 || queryResults.length === filteredResults.length) {
                  facetNotExistsValue.push(facetItem.name);
              }
              _query[facet][facetItem.name] = false; // once calculated, leave it as it was
          }

          if (facetNotExistsValue.length > 0) {
              disabledUncheckedFacets[facet] = facetNotExistsValue;
          }
      }

      for (var facet in disabledUncheckedFacets) {
          var facetValue = [];

          for (var item in disabledUncheckedFacets[facet]) {
              var existsFacet = true;
              var existMultiValueFacet = false;

              for (var index in filteredResults) {
                  var filtered = filteredResults[index];
                  var facetResults = filtered[facet];

                  if (facet === "factors" && facetResults.length > 1 ) {
                      if (facetResults.indexOf(disabledUncheckedFacets[facet][item].toString()) === -1) {
                          existsFacet = false;
                      } else {
                          existMultiValueFacet = true;
                      }

                  } else if (disabledUncheckedFacets[facet][item].toString() !== facetResults.toString()) {
                      existsFacet = false;
                  }
              }

              //All the results contain the same item
              if (existsFacet) {
                  facetValue.push(disabledUncheckedFacets[facet][item]);
              }

              //If all the results don't contain the same facet but the factor multiValue exists in more than one result
              if (!existsFacet && existMultiValueFacet) {
                  disabledUncheckedFacets[facet].splice(item, 1);
              }
          }

          //FacetValue contains all the items that are present in the results for a facet
          if (facetValue.length > 0) {
              disabledCheckedFacets[facet] = facetValue;
          }
      }
      return {
        facets: this.state.facetsTreeData,
        checkedFacets: this.state.querySelect,
        setChecked: this.setChecked,
        disabledCheckedFacets: disabledCheckedFacets,
        disabledUncheckedFacets: disabledUncheckedFacets
      };
  },

  render: function(){
    return (
      <div>
        <div className="grid_6 alpha" id="gxaDifferentialFacetsContainerDiv">
          {Object.keys(this.state.facetsTreeData).length
            ? <Facets {...this._prepareFacetData()} />
            : <div>{"Loading facets"}</div>
            }
        </div>
        <div className="grid_18 omega" id="gxaDifferentialResultsContainerDiv">
          {this.state.resultsData.results && this.state.resultsData.results.length
            ? <Results
                results = {this._filteredResults()}
                host = {this.props.host}
                minDownLevel={this.state.resultsData.minDownLevel}
                minUpLevel={this.state.resultsData.minUpLevel}
                maxDownLevel={this.state.resultsData.maxDownLevel}
                maxUpLevel={this.state.resultsData.maxUpLevel}/>
            : <div> {"Loading results"}</div>
          }
        </div>
      </div>
    )
  },


  _loadInitialData: function(){
    var differentialFacetsUrlObject = {protocol: window.location.protocol, host: this.props.host},
        differentialResultsUrlObject = {protocol: window.location.protocol, host: this.props.host};

    if (window.location.pathname.match(/\/genes\//)) {
        differentialFacetsUrlObject.pathname = 'gxa/json/genes/' + this.props.identifier + '/differentialFacets';
        differentialResultsUrlObject.pathname = 'gxa/json/genes/' + this.props.identifier + '/differentialResults';
    } else if (window.location.pathname.match(/\/genesets\//)) {
        queryParams = {organism: this.props.species};
        differentialFacetsUrlObject.pathname = 'gxa/json/genesets/' + this.props.identifier + '/differentialFacets';
        differentialFacetsUrlObject.query = queryParams;
        differentialResultsUrlObject.pathname = 'gxa/json/genesets/' + this.props.identifier + '/differentialResults';
        differentialFacetsUrlObject.query = queryParams;
    } else {
        var queryParams = {geneQuery: this.props.geneQuery, conditionQuery: this.props.conditionQuery, organism: this.props.species};
        differentialFacetsUrlObject.pathname = 'gxa/json/query/differentialFacets';
        differentialFacetsUrlObject.query = queryParams;
        differentialResultsUrlObject.pathname = 'gxa/json/query/differentialResults';
        differentialResultsUrlObject.query = queryParams;
    }

    $.ajax({
        url: url.format(differentialFacetsUrlObject),
        dataType: "json",
        success: function(response) {
            var facetsTreeData = response;
            var querySelect= {};
            if (this.props.species) {
                for (var i = 0 ; i < facetsTreeData.species.length ; i++) {
                    if (facetsTreeData.species[i].name === this.props.species) {
                      if (!querySelect["species"]) {
                          querySelect["species"] = {};
                      }
                      querySelect["species"][this.props.species] = true;
                    }
                }
            }
            if (Object.keys(querySelect).length === 0) {
                querySelect = this._parseSelectedFacetsFromLocation();
            }
            pushQueryIntoBrowserHistory(querySelect,true);
            this.setState({
              facetsTreeData: facetsTreeData,
              querySelect: querySelect
            });
            $.ajax({
                url: url.format(differentialResultsUrlObject),
                dataType: "json",
                success: function(response) {
                    this.setState({resultsData:response});
                    //filterAndRenderResults();
                }.bind(this),
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log("ERROR");
                    console.log("Status: " + textStatus);
                    console.log("Error thrown: " + errorThrown);
                }
            });
        }.bind(this),
        error: function(jqXHR, textStatus, errorThrown) {
            console.log("ERROR");
            console.log("Status: " + textStatus);
            console.log("Error thrown: " + errorThrown);
        }
    });

  }

});

module.exports = DifferentialTabLoader;
