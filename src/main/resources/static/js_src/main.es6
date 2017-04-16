'use strict';
import React from 'react';
import ReactDOM from 'react-dom';
import {createStore} from 'redux';
import {App} from './components/App.es6';
import holiday from './reducers/holidays';

const base_url = window.location.origin;

const request = new XMLHttpRequest();
request.open('GET', base_url + '/holiday/index', false);  // `false` makes the request synchronous
request.send(null);

if (request.status === 200) {
	console.log(request.responseText);
	const holidays = JSON.parse(request.responseText);

	const store = createStore(holiday, holidays);

	const addHandler = (holiday) => {
		store.dispatch({
			type: "ADD_HOLIDAY",
			holiday: {
				name: holiday
			}
		});

		sendRequest(holiday)
	};

	const render = () => {
		ReactDOM.render(
			<App state={store.getState()} addHandler={addHandler} />,
			document.getElementById('root')
		);
	};

	store.subscribe(render);
	render();
}

function sendRequest(holiday) {
	const data = new FormData();
	data.append("name", holiday);

	const oReq = new XMLHttpRequest();
	oReq.addEventListener("load", reqListener);
	oReq.open("POST", base_url + "/holiday/create");
	oReq.send(data);
}

function reqListener () {
	console.log(this.responseText);
}

