'use strict';
import React from 'react';
import ReactDOM from 'react-dom';
import {createStore} from 'redux';
import {App} from './components/App.es6';
import holiday from './reducers/holidays';

const base_url = window.location.origin;

const store = createStore(holiday);


const request = new XMLHttpRequest();
request.onreadystatechange = () => {
	if(request.readyState === XMLHttpRequest.DONE && request.status === 200) {
		const json = request.responseText;
		console.log(request.responseText);

		store.dispatch({
			type: "ADD_HOLIDAYS",
			holidays: JSON.parse(json)
		});
	}
};
request.open('GET', base_url + '/holiday/index');
request.send(null);

const addHandler = (holiday) => {
	const data = new FormData();
	data.append("name", holiday);

	const oReq = new XMLHttpRequest();
	oReq.onreadystatechange = () => {
		if(oReq.readyState === XMLHttpRequest.DONE && oReq.status === 200) {
			const json = oReq.responseText;

			store.dispatch({
				type: "ADD_HOLIDAY",
				holiday: JSON.parse(json)
			});
		}
	};
	oReq.open("POST", base_url + "/holiday/create");
	oReq.send(data);
};

const render = () => {
	ReactDOM.render(
		<App state={store.getState()} addHandler={addHandler} />,
		document.getElementById('root')
	);
};

store.subscribe(render);
render();


