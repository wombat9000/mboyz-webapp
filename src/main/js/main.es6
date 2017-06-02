'use strict';
import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux';

import {createStore, combineReducers} from 'redux';
import Auth0Lock from 'auth0-lock'
import AuthService from './AuthService.es6';
import {Application} from './components/Application.es6';
import {HttpClient} from './HttpClient.es6';
import holidays from './reducers/holidays';


const reducers = combineReducers({
	holidays
});

const store = createStore(reducers);
HttpClient.fetchInitialState(store);

const addHandler = (holiday) => {
	HttpClient.postNewHoliday(holiday, store);
};

// Configure Auth0
const baseURL = window.location.origin;
const clientId = 'czRO1jls_01h49xVXcxmtMdLvCrtOAyW';
const domain = 'wombat9000.eu.auth0.com';
const lock = new Auth0Lock(clientId, domain, {
	auth: {
		redirectUrl: baseURL + '/login',
		responseType: 'token'
	}
});
const auth = new AuthService(lock);

const render = () => {
	ReactDOM.render(
		<Provider store={store} >
			<Application addHandler={addHandler} auth={auth}/>
		</Provider>,
		document.getElementById('root')
	);
};

store.subscribe(render);
render();