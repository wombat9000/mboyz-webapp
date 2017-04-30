'use strict';

class HttpClient {

	static fetchInitialState(store) {
		const base_url = window.location.origin;
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
	}
}

export {HttpClient};