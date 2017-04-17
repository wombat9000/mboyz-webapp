'use strict';
import React from 'react';
import DatePicker from "react-datepicker";
import moment from 'moment';

class App extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			name: '',
			location: '',
			startDate: '',
			endDate: ''
		};

		this.handleChange = this.handleChange.bind(this);
		this.handleStartDateChange = this.handleStartDateChange.bind(this);
		this.handleEndDateChange = this.handleEndDateChange.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleChange(event) {
		const target = event.target;
		const value = target.value;
		const name = target.name;

		this.setState({
			[name]: value
		});
	}

	handleStartDateChange(date) {
		console.log(date);
		this.setState({
			startDate: date
		});
	}

	handleEndDateChange(date) {
		console.log(date);
		this.setState({
			endDate: date
		});
	}

	handleSubmit(event) {
		this.props.addHandler(this.state);
		event.preventDefault();
	}

	render() {
		const rows = this.props.state.map((holiday, index) =>
			<tr key={holiday.id}><td>{holiday.name}</td><td>{holiday.location}</td></tr>
		);

		return (
			<div>
				<h1>Neuen Urlaub anlegen:</h1>
				<form onSubmit={this.handleSubmit}>
					<label>
						Name:
						<input name="name" type="text" value={this.state.name} onChange={this.handleChange}/>
					</label>
					<label>
						Ort:
						<input name="location" type="text" value={this.state.location} onChange={this.handleChange}/>
					</label>
					<label>
						Von:
						<DatePicker
							dateFormat="DD.MM.YYYY"
							selected={this.state.startDate}
							selectsStart
							startDate={this.state.startDate}
							endDate={this.state.endDate}
							onChange={this.handleStartDateChange}
							isClearable={true}
						/>
					</label>
					<label>
						Bis:
						<DatePicker
							dateFormat="DD.MM.YYYY"
							selected={this.state.endDate}
							selectsEnd
							startDate={this.state.startDate}
							endDate={this.state.endDate}
							onChange={this.handleEndDateChange}
							isClearable={true}
						/>
					</label>

					<input type="submit" value="Submit" />
				</form>
				<table>
					<thead><tr>
						<th>Name</th>
						<th>Ort</th>
					</tr></thead>
				<tbody>
					{rows}
				</tbody>
				</table>
			</div>
		);
	}
}

export {App};