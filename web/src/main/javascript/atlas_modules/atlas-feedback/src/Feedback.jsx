"use strict";

//*------------------------------------------------------------------*
var React = require('react');
var LocalStorageMixin = require('react-localstorage');
var TimerMixin = require('react-timer-mixin');
var ReactCSSTransitionGroup = require('react-addons-css-transition-group');

var BootstrapButton = require('react-bootstrap/lib/Button');
var BootstrapFormGroup = require('react-bootstrap/lib/FormGroup');
var BootstrapFormControl = require('react-bootstrap/lib/FormControl');

require("./gxaFeedback.css");

//*------------------------------------------------------------------*

var FeedbackPersistence = function createFeedbackComponent(FeedbackUIComponent){
  return (
    React.createClass({
      displayName: 'FeedbackForm',
      mixins: [LocalStorageMixin],

      propTypes: {
        collectionCallback: React.PropTypes.func.isRequired
      },

      getInitialState: function() {
        return {
          created : new Date().toISOString("2016-05-02T11:10:05.417Z"), //TODO
          shownTimes : 0,
          show : true
            };
      },

      _shouldShow: function () {
        var timeDiff = Math.abs(new Date().getTime() - new Date(this.state.created).getTime());
        var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));

        return this.state.show && diffDays >0 && this.state.shownTimes < 1500;
      },

      _hide: function () {
        this.setState({show: false});
      },

      _complete : function (userResponse, optionalUserComment) {
        this.setState({show: false});
        this.props.collectionCallback.apply(this, arguments);
      },


      render: function () {
        var it = this._shouldShow()
                  ? <FeedbackUIComponent key={"box"} onComplete={this._complete} onRequestHide={this._hide} />
                  : <div key="nullKey" />;
        return (
          <ReactCSSTransitionGroup transitionName="feedbackBoxTransitionWrapper" transitionEnterTimeout={500} transitionLeaveTimeout={1000} >
            {it}
          </ReactCSSTransitionGroup>

        );
      },


      componentDidMount: function () {
        if(this._shouldShow()){
          this.setState(function(previousState) {
            return {shownTimes: previousState.shownTimes+1};
          });
        }
      }
    })
  );
}

var FeedbackBox = React.createClass({
    propTypes: {
        onComplete: React.PropTypes.func.isRequired,
        onRequestHide : React.PropTypes.func.isRequired
    },

    mixins: [TimerMixin],

    getInitialState: function () {
      return {
        askingWhyTheResultsWereNotUseful: false,
        feedbackMessage: ""
      }
    },

    componentDidUpdate: function () {
      if(this.state.askingWhyTheResultsWereNotUseful && this.state.feedbackMessage.length===0){
        this.setTimeout(function() {if(this.state.feedbackMessage.length===0) {this._submitNegativeAnswer();}}.bind(this), 5000);
      }
    },

    _updateStateWithFormAnswer: function(e) {
      this.setState({feedbackMessage: e.target.value});
    },

    _submitNegativeAnswer: function() {
      this._submitAnswer(0, this.state.feedbackMessage);
    },

    _submitPositiveAnswer: function () {
      this._submitAnswer(10);
    },

    _submitAnswer: function(score, optionalMessage) {
      this.props.onComplete.apply(this, arguments);
    },

    render: function () {
      return( <div className="gxaFeedbackQuestionBox">
      <div id="feedbackBoxCross" className="icon icon-functional" data-icon="x" onClick={this.props.onRequestHide}></div>
          <p>Did you find these results useful?</p>
          <div className="gxaFeedbackQuestionBoxAnswer">
          {this.state.askingWhyTheResultsWereNotUseful
            ?
              <form>
                <BootstrapFormGroup
                  controlId="optionalFeedback"
                >
                  <BootstrapFormControl
                  componentClass="textarea"
                    type="text"
                    value={this.state.feedbackMessage}
                    placeholder="Why not? (optional)"
                    onChange={this._updateStateWithFormAnswer}
                  />
                  <BootstrapFormControl.Feedback />
                  <BootstrapButton style={{float: "right"}} onClick={this._submitNegativeAnswer}>
                    Submit
                  </BootstrapButton>
                </BootstrapFormGroup>
              </form>
            : <div>
                <BootstrapButton bsStyle="default" onClick={this._submitPositiveAnswer}>Yes</BootstrapButton>
                <BootstrapButton onClick={function(){this.setState({askingWhyTheResultsWereNotUseful:true})}.bind(this)}bsStyle="default">No</BootstrapButton>
                <a onClick={this.props.onRequestHide} >Do not show this again</a>
              </div>}
          </div>
      </div>)
    }
  });



module.exports = FeedbackPersistence(FeedbackBox);
