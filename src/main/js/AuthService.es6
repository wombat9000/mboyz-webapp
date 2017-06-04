'use strict';

import createHistory from 'history/createBrowserHistory'
import {isTokenExpired} from './jwtExpiration';

const history = createHistory({
	basename: '',             // The base URL of the app (see below)
	forceRefresh: true,      // Set true to force full page refreshes
	keyLength: 6,             // The length of location.key
	// A function to use to confirm navigation with the user (see below)
	getUserConfirmation: (message, callback) => callback(window.confirm(message))
});

const navigateToHome = () => {
	history.replace('/');
};

export default class AuthService {
	constructor(lock) {
		this.lock = lock;
		// Add callback for lock `authenticated` event
		this.lock.on('authenticated', AuthService.authenticate);
		// binds login functions to keep this context
		this.login = this.login.bind(this);
	}

	static authenticate(authResult) {
		// Saves the user token
		AuthService.setToken(authResult.idToken);
		navigateToHome();
	}

	login() {
		// display the widget.
		this.lock.show();
	}

	logout() {
		// Clear user token and profile data from local storage
		localStorage.removeItem('id_token');
		history.replace('/');
	}

	loggedIn() {
		// Checks if there is a saved token and it's still valid
		const token = AuthService.getToken();
		return !!token && !isTokenExpired(token);
	}

	static setToken(idToken) {
		// Saves user token to local storage
		localStorage.setItem('id_token', idToken);
	}

	static getToken() {
		// Retrieves the user token from local storage
		return localStorage.getItem('id_token');
	}
}