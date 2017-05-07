'use strict';
import React from 'react';
import DatePicker from "react-datepicker";
import {Button, FormGroup, Form, Col, ControlLabel, FormControl} from 'react-bootstrap'


class HolidayForm extends React.Component {
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
		this.setState({
			startDate: date
		});
	}

	handleEndDateChange(date) {
		this.setState({
			endDate: date
		});
	}

	handleSubmit(event) {
		this.props.addHandler(this.state);
		event.preventDefault();
	}

	render() {
		return (
			<div>
				<h2>Neuen Urlaub anlegen:</h2>
				<Form horizontal onSubmit={this.handleSubmit}>
					<FormGroup controlId="name">
						<Col componentClass={ControlLabel} sm={2}>
							Name:
						</Col>
						<Col sm={10}>
							<FormControl name="name" type="text" value={this.state.name} onChange={this.handleChange} />
						</Col>
					</FormGroup>
					<FormGroup controlId="name">
						<Col componentClass={ControlLabel} sm={2}>
							Ort:
						</Col>
						<Col sm={10}>
							<FormControl name="location" type="text" value={this.state.location} onChange={this.handleChange} />
						</Col>
					</FormGroup>
					<FormGroup controlId="startDate">
						<Col componentClass={ControlLabel} sm={2}>
							Von:
						</Col>
						<Col sm={10}>
							<DatePicker
								dateFormat="DD.MM.YYYY"
								selected={this.state.startDate}
								selectsStart
								startDate={this.state.startDate}
								endDate={this.state.endDate}
								onChange={this.handleStartDateChange}
								isClearable={true}
							/>
						</Col>
					</FormGroup>
					<FormGroup controlId="endDate">
						<Col componentClass={ControlLabel} sm={2}>
							Bis:
						</Col>
						<Col sm={10}>
							<DatePicker
								dateFormat="DD.MM.YYYY"
								selected={this.state.endDate}
								selectsEnd
								startDate={this.state.startDate}
								endDate={this.state.endDate}
								onChange={this.handleEndDateChange}
								isClearable={true}
							/>
						</Col>
					</FormGroup>
					<FormGroup>
						<Col smOffset={2} sm={10}>
							<Button type="submit">
								Submit
							</Button>
						</Col>
					</FormGroup>
				</Form>
			</div>
		)
	}
}

export default HolidayForm;