import React from 'react';
import ReactDOM from 'react-dom';
import {HolidayForm} from './components/HolidayForm.es6';

class HelloMessage extends React.Component {
	render() {
		return <div>Hello {this.props.name}</div>;
	}
}

ReactDOM.render(<HelloMessage name="React updated" />, document.getElementById('root'));
ReactDOM.render(<HolidayForm/>, document.getElementById('root'));