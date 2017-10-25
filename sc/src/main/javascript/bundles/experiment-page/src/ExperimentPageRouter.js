import React from 'react'
import PropTypes from 'prop-types'
import {BrowserRouter, Route, Redirect, Switch, NavLink, withRouter} from 'react-router-dom'

import URI from 'urijs'

import TSnePlotViewRoute from './TSnePlotViewRoute'


const RoutePropTypes = {
  match: PropTypes.object.isRequired,
  location: PropTypes.object.isRequired,
  history: PropTypes.object.isRequired
}

const TabCommonPropTypes = {
  atlasUrl: PropTypes.string.isRequired,
  experimentAccession: PropTypes.string.isRequired,
  accessKey: PropTypes.string,
  resourcesUrl: PropTypes.string
}


// What component each tab type should render, coupled to ExperimentController.java
const tabTypeComponent = {
  // 'multipart' : ``,
  't-sne-plot' : TSnePlotViewRoute
  // 'experiment-design' : `ExperimntDesign`,
  // 'resources' : `Resources`,
  // 'static-table' : StaticTable,
  // 'qc-report' : QCReport
}

const TopRibbon = ({tabNames, routeProps}) =>
  <ul className={`tabs`}>
    {
      tabNames.map((tabName) =>
        <li title={tabName} key={tabName} className={`tabs-title`}>
          <NavLink to={{pathname:`/${tabName}`, search: routeProps.location.search, hash: routeProps.location.hash}}
                   activeStyle={{color: `#0a0a0a`, background: `#e6e6e6`}}>
            {tabName}
          </NavLink>
        </li>
    )}
  </ul>

TopRibbon.propTypes = {
  tabNames: PropTypes.arrayOf(PropTypes.string).isRequired,
  routeProps: PropTypes.shape(RoutePropTypes)
}


const TabContent = ({type, tabProps, commonProps, routeProps}) => {
  // Pass in the search from location
  const Tab = tabTypeComponent[type]

  return (
    Tab ? <Tab {...tabProps} {...commonProps} {...routeProps}/> : null
  )
}

TabContent.propTypes = {
  type: PropTypes.string.isRequired,
  tabProps: PropTypes.object,
  commonProps: PropTypes.shape(TabCommonPropTypes),
  routeProps: PropTypes.shape(RoutePropTypes)
}

const RedirectWithSearchAndHash = (props) =>
  <Redirect to={{ pathname: props.pathname, search: props.location.search, hash: props.location.hash}} />

const RedirectWithLocation = withRouter(RedirectWithSearchAndHash)


const ExperimentPageRouter = ({atlasUrl, resourcesUrl, experimentAccession, accessKey, tabs}) => {

  const tabCommonProps = {
    atlasUrl,
    resourcesUrl,
    experimentAccession,
    accessKey
  }

  return (
    <BrowserRouter basename={URI(`experiments/${experimentAccession}`, URI(atlasUrl).path()).toString()}>
      <div>
        <Route path={`/`}
               render={ (routeProps) => <TopRibbon tabNames={tabs.map((tab) => tab.name)} routeProps={routeProps}/> }
        />
        <Switch>
          {
            tabs.map((tab) =>
              <Route key={tab.name}
                     path={`/${tab.name}`}
                     render={ (routeProps) => <TabContent type={tab.type} tabProps={tab.props} commonProps={tabCommonProps} routeProps={routeProps}/> }
              />
            )
          }
          <RedirectWithLocation pathname={`/${tabs[0].name}`} />
        </Switch>

      </div>
    </BrowserRouter>
  )
}

ExperimentPageRouter.propTypes = {
  ...TabCommonPropTypes,
  tabs: PropTypes.arrayOf(PropTypes.shape({
    type: PropTypes.string.isRequired,
    name: PropTypes.string.isRequired,
    props: PropTypes.object.isRequired
  })).isRequired
}

export default ExperimentPageRouter
