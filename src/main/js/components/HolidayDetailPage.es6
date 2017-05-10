import React from 'react';
const OK = 200;
const baseURL = window.location.origin;

class HolidayDetailPage extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			name: '',
			location: '',
			startDate: '',
			endDate: ''
		};
	}

	componentDidMount() {
		const holidayId = this.props.match.params.id;
		const myPromise = new Promise(function(resolve, reject) {
			const request = new XMLHttpRequest();
			request.open('GET', baseURL + '/api/holiday/' + holidayId);
			request.onload = function() {
				if (request.status === OK) {
					resolve(JSON.parse(request.responseText)); // we got data here, so resolve the Promise
				} else {
					reject(Error(request.statusText)); // status is not 200 OK, so reject
				}
			};

			request.onerror = function() {
				reject(Error('Error fetching data.')); // error occurred, reject the  Promise
			};

			request.send(); //send the request
		});

		myPromise.then(val => this.setState(val))
	}

	render () {
		return (
			<div>
				<h2>Urlaub: {this.props.match.params.id}</h2>
				<ul>
					<li>Name: {this.state.name}</li>
					<li>Location: {this.state.location}</li>
					<li>StartDate: {this.state.startDate}</li>
					<li>EndDate: {this.state.endDate}</li>
				</ul>
			</div>
		);
	}
}

export default HolidayDetailPage;
