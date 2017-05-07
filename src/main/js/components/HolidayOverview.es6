'use strict';

import React from 'react';
import {HolidayForm} from "./HolidayForm";
import {Grid} from "react-bootstrap";

class HolidayOverview extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		const rows = this.props.state.map((holiday, index) =>
			<tr key={holiday.id}>
				<td>{holiday.name}</td>
				<td>{holiday.location}</td>
				<td>{holiday.startDate}</td>
				<td>{holiday.endDate}</td>
			</tr>
		);

		return (
			<div>
			<Grid>
			<HolidayForm addHandler={this.props.addHandler} />
			<table>
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
			</table>
			</Grid>
			</div>
		);
	}
}

export {HolidayOverview};