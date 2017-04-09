'use strict';
import React from 'react';

class HolidayForm extends React.Component {
	constructor(props) {
		super(props);
		this.state = {name: ''};

		this.handleChange = this.handleChange.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleChange(event) {
		this.setState({name: event.target.value});
	}

	handleSubmit(event) {
		console.log('A holiday was submitted: ' + this.state.name);
		event.preventDefault();
	}

	render() {
		return (
			<div>
				<h1>Neuen Urlaub anlegen:</h1>
				<form onSubmit={this.handleSubmit}>
					<label>
						Name:
						<input name="name" type="text" value={this.state.name} onChange={this.handleChange}/>
					</label>
					<input type="submit" value="Submit" />
				</form>
			</div>
		);
	}
}

export {HolidayForm};