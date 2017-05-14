'use strict';
import React from 'react';
import ReactDOM from 'react-dom';
import {createStore} from 'redux';
import AuthService from './AuthService.es6';
import {Application} from './components/Application.es6';
import {HttpClient} from './HttpClient.es6';
import holiday from './reducers/holidays';

const store = createStore(holiday);
HttpClient.fetchInitialState(store);

const addHandler = (holiday) => {
	HttpClient.postNewHoliday(holiday, store);
};

const auth = new AuthService('czRO1jls_01h49xVXcxmtMdLvCrtOAyW', 'wombat9000.eu.auth0.com');

const render = () => {
	ReactDOM.render(
		<Application state={store.getState()} addHandler={addHandler} auth={auth}/>,
		document.getElementById('root')
	);
};

store.subscribe(render);
render();