'use strict';
import React from 'react';
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
			state: this.props.state,
			addHandler: this.props.addHandler,
			auth: this.props.auth
		};

		return (
			<BrowserRouter>
				<div>
					<Navigation auth={this.props.auth} />
					<Route exact path="/" component={Home}/>
					<PrivateRoute exact path="/holidays" auth={this.props.auth} component={HolidayOverview} componentProps={holidaysProps}/>
					<PrivateRoute path="/holidays/:id" auth={this.props.auth} component={HolidayDetailPage} componentProps={holidaysProps}/>
				</div>
			</BrowserRouter>
		);
	}
}


export {Application};