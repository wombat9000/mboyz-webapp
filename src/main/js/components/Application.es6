'use strict';
import React from 'react';
import PropTypes from 'prop-types'
import {BrowserRouter, Route} from 'react-router-dom';
import HolidayOverview from './HolidayOverview.es6';
import Navigation from "./Navigation";
import PrivateRoute from "./PrivateRoute";
import HolidayDetailPage from "./HolidayDetailPage";
import {Home} from "./Home";

class Application extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		const holidaysProps = {
			addHandler: this.props.addHandler,
		};

		return (
			<BrowserRouter>
				<div>
					<Navigation auth={this.props.auth} />
					<Route exact path="/" component={Home} />
					<PrivateRoute exact path="/holidays" auth={this.props.auth} component={HolidayOverview} componentProps={holidaysProps} />
					<PrivateRoute path="/holidays/:id" auth={this.props.auth} component={HolidayDetailPage} componentProps={holidaysProps} />
				</div>
			</BrowserRouter>
		);
	}
}
Application.contextTypes = {
	store: PropTypes.object,
};


export {Application};