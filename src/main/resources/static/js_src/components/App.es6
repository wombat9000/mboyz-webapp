'use strict';
import React from 'react';

class App extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			name: '',
			location: ''
		};

		this.handleChange = this.handleChange.bind(this);
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