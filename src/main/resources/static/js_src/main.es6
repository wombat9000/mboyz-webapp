'use strict';
import React from 'react';
import ReactDOM from 'react-dom';
import {createStore} from 'redux';
import {Application} from './components/Application.es6';
import holiday from './reducers/holidays';
import moment from "moment";

const base_url = window.location.origin;
const store = createStore(holiday);

const request = new XMLHttpRequest();
request.onreadystatechange = () => {
	if(request.readyState === XMLHttpRequest.DONE && request.status === 200) {
		const json = request.responseText;
		store.dispatch({
			type: "ADD_HOLIDAYS",
			holidays: JSON.parse(json)
		});
	}
};

request.open('GET', base_url + '/holiday/index');
request.send();

const addHandler = (holiday) => {
	const data = new FormData();

	Object.keys(holiday).forEach(function (key) {
		data.set(""+key, holiday[key])
	});

	if (moment.isMoment(holiday.startDate)) {
		data.set("startDate", holiday.startDate.format("YYYY-MM-DD"));

	}
	if (moment.isMoment(holiday.endDate)) {
		data.set("endDate", holiday.endDate.format("YYYY-MM-DD"));
	}

	const oReq = new XMLHttpRequest();
	oReq.onreadystatechange = () => {
		if(oReq.readyState === XMLHttpRequest.DONE && oReq.status === 201) {
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
		<Application state={store.getState()} addHandler={addHandler} />,
		document.getElementById('root')
	);
};

store.subscribe(render);
render();