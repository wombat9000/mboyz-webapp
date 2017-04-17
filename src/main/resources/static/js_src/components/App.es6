'use strict';
import React from 'react';

class App extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			name: '',
			location: ''
		};

		this.handleChangeName = this.handleChangeName.bind(this);
		this.handleChangeLocation = this.handleChangeLocation.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleChangeName(event) {
		this.setState({name: event.target.value});
	}

	handleChangeLocation(event) {
		this.setState({location: event.target.value});
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
						<input name="name" type="text" value={this.state.name} onChange={this.handleChangeName}/>
						<input name="location" type="text" value={this.state.location} onChange={this.handleChangeLocation}/>
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