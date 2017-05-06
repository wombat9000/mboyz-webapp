'use strict';
import React from 'react';
import {BrowserRouter as Router, Route} from 'react-router-dom';
import {HolidayOverview} from './HolidayOverview.es6';

const Test = () => <div>test</div>;

class Application extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		return (
			<Router>
				<div>
				<Route exact path="/" render={props => <HolidayOverview  state={this.props.state} addHandler={this.props.addHandler} />} />
				<Route path="/test" component={Test} />
				</div>
			</Router>
		);
	}
}

export {Application};