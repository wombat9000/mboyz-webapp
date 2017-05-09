'use strict';
import React from 'react';
import {BrowserRouter, Route} from 'react-router-dom';
import HolidayOverview from './HolidayOverview.es6';
import AuthService from '../AuthService.es6';
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
				<h2>Du musst einloggen, um diese Seite zu sehen.</h2>
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
					<Navigation auth={auth} />
					<Route exact path="/" component={Home}/>
					<PrivateRoute exact path="/holidays" component={HolidayOverview} componentProps={holidaysProps}/>
				</div>
			</BrowserRouter>
		);
	}
}

const Home = () => (
	<div>
		<h2>Home</h2>
	</div>
);

export {Application};