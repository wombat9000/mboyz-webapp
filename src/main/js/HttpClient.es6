'use strict';

import moment from "moment";

const OK = 200;
const CREATED = 201;
const baseURL = window.location.origin;

class HttpClient {
	//TODO:
	// - introduce holiday class
	// - watch further tutorials on how to move some of this logic to reducers
		// - store dispatch gets called from elsewhere, and reducer fires the requests
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

		request.open('GET', baseURL + '/api/holiday/index');
		request.send();
	}

	static postNewHoliday(holiday, store) {
		const formData = extractFormData(holiday);
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

		request.open('POST', baseURL + '/api/holiday/create');
		request.send(formData);
	}
}

function extractFormData (holiday) {
	const data = new FormData();
	Object.keys(holiday).forEach(function (key) {
		data.set("" + key, holiday[key])
	});

	if (moment.isMoment(holiday.startDate)) {
		data.set("startDate", holiday.startDate.format("YYYY-MM-DD"));
	}

	if (moment.isMoment(holiday.endDate)) {
		data.set("endDate", holiday.endDate.format("YYYY-MM-DD"));
	}
	return data;
}

export {HttpClient};