'use strict';

const OK = 200;
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
}

export {HttpClient};