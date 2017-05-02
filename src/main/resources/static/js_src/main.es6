'use strict';
import React from 'react';
import ReactDOM from 'react-dom';
import {createStore} from 'redux';
import {Application} from './components/Application.es6';
import {HttpClient} from './HttpClient.es6';
import holiday from './reducers/holidays';

const store = createStore(holiday);

HttpClient.fetchInitialState(store);

const addHandler = (holiday) => {
	HttpClient.postNewHoliday(holiday, store);
};

const render = () => {
	ReactDOM.render(
		<Application state={store.getState()} addHandler={addHandler} />,
		document.getElementById('root')
	);
};

store.subscribe(render);
render();