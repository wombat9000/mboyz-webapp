'use strict';

import moment from "moment";

const OK = 200;
const CREATED = 201;
const baseURL = window.location.origin;

class HttpClient {
	static fetchInitialState(store) {
		const request = new XMLHttpRequest();
		request.onreadystatechange = () => {
			if(request.readyState === XMLHttpRequest.DONE && request.status === OK) {
				const json = request.responseText;
				store.dispatch({
					type: "ADD_HOLIDAYS",
					holidays: JSON.parse(json)
				});
			}
		};

		request.open('GET', baseURL + '/holiday/index');
		request.send();
	}

	static postNewHoliday(holiday, store) {
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

		const request = new XMLHttpRequest();
		request.onreadystatechange = () => {
			if(request.readyState === XMLHttpRequest.DONE && request.status === CREATED) {
				const json = request.responseText;
				store.dispatch({
					type: "ADD_HOLIDAY",
					holiday: JSON.parse(json)
				});
			}
		};

		request.open('POST', baseURL + '/holiday/create');
		request.send(data);
	}
}

export {HttpClient};