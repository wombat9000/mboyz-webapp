'use strict';

import React from 'react';
import {Table} from "react-bootstrap";
import {LinkContainer} from "react-router-bootstrap";

class HolidayTable extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		const rows = this.props.state.map((holiday, index) =>
		<LinkContainer key={holiday.id} to={"/holidays/" + holiday.id}>
			<tr key={holiday.id}>
				<td>{holiday.name}</td>
				<td>{holiday.location}</td>
				<td>{holiday.startDate}</td>
				<td>{holiday.endDate}</td>
			</tr>
		</LinkContainer>
		);

		return (
			<div>
				<h2>Alle Urlaube</h2>
				<Table striped bordered hover>
					<thead>
					<tr>
						<th>Name</th>
						<th>Ort</th>
						<th>Start</th>
						<th>Ende</th>
					</tr>
					</thead>
					<tbody>
					{rows}
					</tbody>
				</Table>
			</div>
		);
	}
}

export default HolidayTable;