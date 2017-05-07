'use strict';

import Auth0Lock from 'auth0-lock'
import createHistory from 'history/createBrowserHistory'
const history = createHistory({
	basename: '',             // The base URL of the app (see below)
	forceRefresh: true,      // Set true to force full page refreshes
	keyLength: 6,             // The length of location.key
	// A function to use to confirm navigation with the user (see below)
	getUserConfirmation: (message, callback) => callback(window.confirm(message))
});

const baseURL = window.location.origin;

export default class AuthService {
	constructor(clientId, domain) {
		// Configure Auth0
		this.lock = new Auth0Lock(clientId, domain, {
			auth: {
				redirectUrl: baseURL + '/login',
				responseType: 'token'
			}
		});
		// Add callback for lock `authenticated` event
		this.lock.on('authenticated', this._doAuthentication.bind(this));
		// binds login functions to keep this context
		this.login = this.login.bind(this);
	}

	_doAuthentication(authResult) {
		// Saves the user token
		this.setToken(authResult.idToken);
		// navigate to the home route
		history.replace('/');
	}

	login() {
		// Call the show method to display the widget.
		this.lock.show();
	}

	loggedIn() {
		// Checks if there is a saved token and it's still valid
		return !!this.getToken();
	}

	setToken(idToken) {
		// Saves user token to local storage
		localStorage.setItem('id_token', idToken);
	}

	getToken() {
		// Retrieves the user token from local storage
		return localStorage.getItem('id_token');
	}

	logout() {
		// Clear user token and profile data from local storage
		localStorage.removeItem('id_token');
	}
}