const React = require(`react`);
const PropTypes = require(`../PropTypes.js`);

const SelectionDropdownFactory = (displayName) =>
  React.createClass({
      displayName: displayName,
      propTypes: PropTypes.SelectionDropdown,

      getInitialState() {
          return { selected: this.props.current, disabled: false }
      },

      _handleChange(e) {
          this.props.onSelect(e.target.value);
          this.setState({selected: e.target.value});
      },

      render() {
          const createOption = (option, key) => <option key={key} value={option}>{option}</option>;

          return (
              <div>
                  <span>{displayName}</span>
                  <select onChange={this._handleChange} value={this.state.selected} disabled={this.props.disabled}>
                      {this.props.available.map(createOption)}
                  </select>
              </div>
          );
      }
  });

module.exports = SelectionDropdownFactory;
