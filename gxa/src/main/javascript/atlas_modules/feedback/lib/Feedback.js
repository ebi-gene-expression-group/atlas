'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _react = require('react');

var _react2 = _interopRequireDefault(_react);

var _reactLocalstorage = require('react-localstorage');

var _reactLocalstorage2 = _interopRequireDefault(_reactLocalstorage);

var _reactTimerMixin = require('react-timer-mixin');

var _reactTimerMixin2 = _interopRequireDefault(_reactTimerMixin);

var _reactAddonsCssTransitionGroup = require('react-addons-css-transition-group');

var _reactAddonsCssTransitionGroup2 = _interopRequireDefault(_reactAddonsCssTransitionGroup);

var _Button = require('react-bootstrap/lib/Button');

var _Button2 = _interopRequireDefault(_Button);

var _FormGroup = require('react-bootstrap/lib/FormGroup');

var _FormGroup2 = _interopRequireDefault(_FormGroup);

var _FormControl = require('react-bootstrap/lib/FormControl');

var _FormControl2 = _interopRequireDefault(_FormControl);

var _emojioneSprites = require('./assets/emojione.sprites.png');

var _emojioneSprites2 = _interopRequireDefault(_emojioneSprites);

var _reactEmojione = require('react-emojione');

require('./gxaFeedback.css');

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var FeedbackPersistence = function createFeedbackComponent(FeedbackUIComponent) {
  return _react2.default.createClass({
    displayName: 'ExpressionAtlasFeedbackForm',
    mixins: [_reactLocalstorage2.default],

    propTypes: {
      collectionCallback: _react2.default.PropTypes.func.isRequired
    },

    getInitialState: function getInitialState() {
      return {
        created: new Date().toISOString(),
        shownTimes: 0,
        show: true
      };
    },

    _shouldShow: function _shouldShow() {
      var timeDiff = Math.abs(new Date().getTime() - new Date(this.state.created).getTime());
      var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));

      return this.state.show && diffDays > 0 && this.state.shownTimes < 50;
    },

    _hide: function _hide() {
      this.setState({ show: false });
    },

    _complete: function _complete(userResponse, optionalUserComment) {
      this.setState({ show: false });
      this.props.collectionCallback(userResponse, new Date().toISOString() + (optionalUserComment || ""));
    },

    render: function render() {
      var it = this._shouldShow() ? _react2.default.createElement(FeedbackUIComponent, { key: "box", onComplete: this._complete, onRequestHide: this._hide }) : _react2.default.createElement('div', { key: 'nullKey' });
      return _react2.default.createElement(
        _reactAddonsCssTransitionGroup2.default,
        { transitionName: 'feedbackBoxTransitionWrapper', transitionEnterTimeout: 500, transitionLeaveTimeout: 1000 },
        it
      );
    },

    componentDidMount: function componentDidMount() {
      if (this._shouldShow()) {
        this.setState(function (previousState) {
          return { shownTimes: previousState.shownTimes + 1 };
        });
      }
    }
  });
};

var FeedbackBox = _react2.default.createClass({
  displayName: 'FeedbackBox',

  propTypes: {
    onComplete: _react2.default.PropTypes.func.isRequired,
    onRequestHide: _react2.default.PropTypes.func.isRequired
  },

  mixins: [_reactTimerMixin2.default],

  getInitialState: function getInitialState() {
    return {
      askingWhyTheResultsWereNotUseful: false,
      feedbackMessage: ""
    };
  },

  componentDidUpdate: function componentDidUpdate() {
    if (this.state.askingWhyTheResultsWereNotUseful && this.state.feedbackMessage.length === 0) {
      this.setTimeout(function () {
        if (this.state.feedbackMessage.length === 0) {
          this._submitNegativeAnswer();
        }
      }.bind(this), 5000);
    }
  },

  _updateStateWithFormAnswer: function _updateStateWithFormAnswer(e) {
    this.setState({ feedbackMessage: e.target.value });
  },

  _submitNegativeAnswer: function _submitNegativeAnswer() {
    this._submitAnswer(0, this.state.feedbackMessage);
  },

  _submitPositiveAnswer: function _submitPositiveAnswer() {
    this._submitAnswer(10);
  },

  _submitAnswer: function _submitAnswer(score, optionalMessage) {
    this.props.onComplete.apply(this, arguments);
  },

  render: function render() {
    return _react2.default.createElement(
      'div',
      { className: 'gxaFeedbackQuestionBox' },
      _react2.default.createElement('div', { id: 'feedbackBoxCross', className: 'icon icon-functional', 'data-icon': 'x', onClick: this.props.onRequestHide }),
      _react2.default.createElement(
        'p',
        null,
        'Did you find these results useful?'
      ),
      _react2.default.createElement(
        'div',
        { className: 'gxaFeedbackQuestionBoxAnswer' },
        this.state.askingWhyTheResultsWereNotUseful ? _react2.default.createElement(
          'form',
          null,
          _react2.default.createElement(
            _FormGroup2.default,
            {
              controlId: 'optionalFeedback'
            },
            _react2.default.createElement(_FormControl2.default, {
              componentClass: 'textarea',
              type: 'text',
              value: this.state.feedbackMessage,
              placeholder: 'Why not? (optional)',
              onChange: this._updateStateWithFormAnswer
            }),
            _react2.default.createElement(_FormControl2.default.Feedback, null),
            _react2.default.createElement(
              _Button2.default,
              { style: { float: "right" }, onClick: this._submitNegativeAnswer },
              'Submit'
            )
          )
        ) : _react2.default.createElement(
          'div',
          null,
          _react2.default.createElement(
            _Button2.default,
            { bsStyle: 'default', onClick: this._submitPositiveAnswer },
            'Yes'
          ),
          _react2.default.createElement(
            _Button2.default,
            { onClick: function () {
                this.setState({ askingWhyTheResultsWereNotUseful: true });
              }.bind(this), bsStyle: 'default' },
            'No'
          ),
          _react2.default.createElement(
            'a',
            { onClick: this.props.onRequestHide },
            'Do not show this again'
          )
        )
      )
    );
  }
});

var Smiley = _react2.default.createClass({
  displayName: 'Smiley',

  propTypes: {
    emoji: _react2.default.PropTypes.string.isRequired,
    value: _react2.default.PropTypes.number.isRequired,
    onClickCallback: _react2.default.PropTypes.func.isRequired,
    selected: _react2.default.PropTypes.bool.isRequired
  },

  _onClick: function _onClick() {
    this.props.onClickCallback(this.props.value);
  },

  _emojifyOptions: {
    convertShortnames: true,
    convertUnicode: false,
    convertAscii: true,
    styles: {
      backgroundImage: 'url(' + _emojioneSprites2.default + ')',
      width: '32px',
      height: '32px',
      margin: '4px'
    }
  },

  render: function render() {
    return _react2.default.createElement(
      'span',
      { style: { padding: '6px' } },
      _react2.default.createElement(
        'span',
        { className: this.props.selected ? "gxaSmiley gxaSmileyClicked" : "gxaSmiley", onClick: this._onClick },
        (0, _reactEmojione.emojify)(this.props.emoji, this._emojifyOptions)
      )
    );
  }
});

var FeedbackSmileys = _react2.default.createClass({
  displayName: 'FeedbackSmileys',

  propTypes: {
    onComplete: _react2.default.PropTypes.func.isRequired,
    onRequestHide: _react2.default.PropTypes.func.isRequired
  },

  mixins: [_reactTimerMixin2.default],

  getInitialState: function getInitialState() {
    return {
      score: -1,
      feedbackMessage: ""
    };
  },

  _interactionHappened: function _interactionHappened() {
    return this.state.score !== this.getInitialState().score;
  },

  _updateStateWithFormAnswer: function _updateStateWithFormAnswer(e) {
    this.setState({ feedbackMessage: e.target.value });
  },

  _smileyClicked: function _smileyClicked(newScore) {
    this.setState({ score: newScore });
  },

  _submit: function _submit() {
    this.props.onComplete(this.state.score, this.state.feedbackMessage);
  },

  componentDidUpdate: function componentDidUpdate() {
    if (this._interactionHappened() && this.state.feedbackMessage.length === 0) {
      this.setTimeout(function () {
        if (this.state.feedbackMessage.length === 0) {
          this._submit();
        }
      }.bind(this), 5000);
    }
  },

  render: function render() {
    /* identifiers from http://emoji.codes/ */
    return _react2.default.createElement(
      'div',
      { className: 'gxaSmileyFeedbackBox' },
      _react2.default.createElement(
        'p',
        null,
        ' Did you find these results useful?'
      ),
      _react2.default.createElement(
        'div',
        { className: 'gxaSmileyRow' },
        [[":frowning:", 0], [":slight_frown:", 2], [":neutral_face:", 5], [":slight_smile:", 8], [":smiley:", 10]].map(function (ar) {
          return _react2.default.createElement(Smiley, {
            key: ar[0] + (this.state.score === ar[1]),
            emoji: ar[0],
            value: ar[1],
            onClickCallback: this._smileyClicked,
            selected: this.state.score === ar[1]
          });
        }.bind(this))
      ),
      _react2.default.createElement(
        'form',
        { style: { display: this._interactionHappened() ? "block" : "none" } },
        _react2.default.createElement(
          _FormGroup2.default,
          {
            controlId: 'optionalFeedback'
          },
          _react2.default.createElement(_FormControl2.default, {
            componentClass: 'textarea',
            type: 'text',
            value: this.state.feedbackMessage,
            placeholder: 'Feedback (optional)',
            onChange: this._updateStateWithFormAnswer
          }),
          _react2.default.createElement(_FormControl2.default.Feedback, null),
          _react2.default.createElement(
            'div',
            null,
            _react2.default.createElement(
              _Button2.default,
              { onClick: this._submit },
              'Submit'
            )
          )
        )
      )
    );
  }

});

exports.default = FeedbackPersistence(FeedbackSmileys);