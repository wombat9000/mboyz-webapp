'use strict';
import React from 'react';
import {BrowserRouter, Route, Redirect} from 'react-router-dom';
import HolidayOverview from './HolidayOverview.es6';
import AuthService from '../AuthService.es6';
import Login from './Login.es6'
import Navigation from "./Navigation";

const auth = new AuthService('czRO1jls_01h49xVXcxmtMdLvCrtOAyW', 'wombat9000.eu.auth0.com');

class PrivateRoute extends React.Component {
	constructor(props) {
		super(props);
	}

	render () {
		const Component = this.props.component;
		const propsWithoutComponent = {...this.props};
		delete propsWithoutComponent.component;

		return (
		<Route {...propsWithoutComponent} render={() => (
			auth.loggedIn() ? (
				<Component {...this.props.componentProps}/>
			) : (
				<Redirect to={{
					pathname: '/login',
					state: { from: this.props.location }
				}}/>
			)
		)}/>
		)
	}
}

class Application extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		const holidaysProps = {
			state: this.props.state,
			addHandler: this.props.addHandler,
			auth: auth
		};

		return (
			<BrowserRouter>
				<div>
					<Navigation/>
					<PrivateRoute exact path="/" component={HolidayOverview} componentProps={holidaysProps}/>
					<Route path="/login" render={() => <Login auth={auth} />} />
				</div>
			</BrowserRouter>
		);
	}
}

export {Application};