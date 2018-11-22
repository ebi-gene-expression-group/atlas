import React from 'react'
import LocalStorageMixin from 'react-localstorage'
import TimerMixin from 'react-timer-mixin'
import ReactCSSTransitionGroup from 'react-addons-css-transition-group'

import BootstrapButton from 'react-bootstrap/lib/Button'
import BootstrapFormGroup from 'react-bootstrap/lib/FormGroup'
import BootstrapFormControl from 'react-bootstrap/lib/FormControl'

import EmojiSpritesFile from './assets/emojione.sprites.png'
import {emojify} from 'react-emojione'

import './gxaFeedback.css'

var FeedbackPersistence = function createFeedbackComponent(FeedbackUIComponent){
  return (
    React.createClass({
      displayName: 'ExpressionAtlasFeedbackForm',
      mixins: [LocalStorageMixin],

      propTypes: {
        collectionCallback: React.PropTypes.func.isRequired
      },

      getInitialState: function() {
        return {
          created : new Date().toISOString(),
          shownTimes : 0,
          show : true
            }
      },

      _shouldShow: function () {
        var timeDiff = Math.abs(new Date().getTime() - new Date(this.state.created).getTime())
        var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24))

        return this.state.show && diffDays >0 && this.state.shownTimes < 50
      },

      _hide: function () {
        this.setState({show: false})
      },

      _complete : function (userResponse, optionalUserComment) {
        this.setState({show: false})
        this.props.collectionCallback(userResponse, new Date().toISOString()+(optionalUserComment||""))
      },


      render: function () {
        var it = this._shouldShow()
                  ? <FeedbackUIComponent key={"box"} onComplete={this._complete} onRequestHide={this._hide} />
                  : <div key="nullKey" />
        return (
          <ReactCSSTransitionGroup transitionName="feedbackBoxTransitionWrapper" transitionEnterTimeout={500} transitionLeaveTimeout={1000} >
            {it}
          </ReactCSSTransitionGroup>

        )
      },


      componentDidMount: function () {
        if(this._shouldShow()){
          this.setState(function(previousState) {
            return {shownTimes: previousState.shownTimes+1}
          })
        }
      }
    })
  )
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
        this.setTimeout(function() {if(this.state.feedbackMessage.length===0) {this._submitNegativeAnswer()}}.bind(this), 5000)
      }
    },

    _updateStateWithFormAnswer: function(e) {
      this.setState({feedbackMessage: e.target.value})
    },

    _submitNegativeAnswer: function() {
      this._submitAnswer(0, this.state.feedbackMessage)
    },

    _submitPositiveAnswer: function () {
      this._submitAnswer(10)
    },

    _submitAnswer: function(score, optionalMessage) {
      this.props.onComplete.apply(this, arguments)
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
            :
              <div>
                <BootstrapButton bsStyle="default" onClick={this._submitPositiveAnswer}>Yes</BootstrapButton>
                <BootstrapButton onClick={function(){this.setState({askingWhyTheResultsWereNotUseful:true})}.bind(this)} bsStyle="default">No</BootstrapButton>
                <a onClick={this.props.onRequestHide} >Do not show this again</a>
              </div>}
          </div>
      </div>)
    }
  })

  var Smiley = React.createClass({
    propTypes: {
      emoji: React.PropTypes.string.isRequired,
      value: React.PropTypes.number.isRequired,
      onClickCallback: React.PropTypes.func.isRequired,
      selected: React.PropTypes.bool.isRequired
    },

    _onClick: function() {
      this.props.onClickCallback(this.props.value)
    },

    _emojifyOptions: {
      convertShortnames: true,
      convertUnicode: false,
      convertAscii: true,
      styles: {
          backgroundImage: 'url('+EmojiSpritesFile+')',
          width: '32px',
          height: '32px',
          margin: '4px'
      }
    },

    render: function () {
      return (
        <span style={{padding: `6px`}} >
          <span className={this.props.selected? "gxaSmiley gxaSmileyClicked": "gxaSmiley"} onClick={this._onClick} >
          {emojify(this.props.emoji, this._emojifyOptions)}
          </span>
        </span>
      )
    }
  })

  var FeedbackSmileys = React.createClass({
      propTypes: {
          onComplete: React.PropTypes.func.isRequired,
          onRequestHide : React.PropTypes.func.isRequired
      },

      mixins: [TimerMixin],

      getInitialState: function() {
        return {
          score: -1,
          feedbackMessage: ""
        }
      },

      _interactionHappened: function() {
        return this.state.score !== this.getInitialState().score
      },

      _updateStateWithFormAnswer: function(e) {
        this.setState({feedbackMessage: e.target.value})
      },

      _smileyClicked: function(newScore) {
        this.setState({score: newScore})
      },

      _submit: function() {
        this.props.onComplete(this.state.score, this.state.feedbackMessage)
      },

      componentDidUpdate: function () {
        if(this._interactionHappened() && this.state.feedbackMessage.length===0){
          this.setTimeout(
            function() {
              if(this.state.feedbackMessage.length===0) {
                this._submit()
              }
            }.bind(this), 5000)
        }
      },

      render: function() { /* identifiers from http://emoji.codes/ */
        return (
          <div className="gxaSmileyFeedbackBox">
            <p> Did you find these results useful?</p>
            <div className="gxaSmileyRow">
              {[[":frowning:", 0],[":slight_frown:", 2], [":neutral_face:", 5], [":slight_smile:",8], [":smiley:",10]].map(function(ar){
                return (
                  <Smiley
                    key={ar[0]+(this.state.score===ar[1])}
                    emoji={ar[0]}
                    value={ar[1]}
                    onClickCallback={this._smileyClicked}
                    selected={this.state.score===ar[1]}
                  />
                )
              }.bind(this))}
            </div>
            <form style={{display: this._interactionHappened() ? "block" :"none"}}>
              <BootstrapFormGroup
                controlId="optionalFeedback"
              >
                <BootstrapFormControl
                componentClass="textarea"
                  type="text"
                  value={this.state.feedbackMessage}
                  placeholder="Feedback (optional)"
                  onChange={this._updateStateWithFormAnswer}
                />
                <BootstrapFormControl.Feedback />
                <div>
                  <BootstrapButton onClick={this._submit}>
                  Submit
                  </BootstrapButton>
                </div>
              </BootstrapFormGroup>
            </form>
          </div>
        )
      }

  })



export default FeedbackPersistence(FeedbackSmileys)
