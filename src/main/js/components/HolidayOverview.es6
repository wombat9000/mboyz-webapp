'use strict';

import React from 'react';
import HolidayForm from './HolidayForm';
import {Grid} from 'react-bootstrap';
import HolidayTable from './HolidayTable';
import LogoutButton from "./LogoutButton";

class HolidayOverview extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		return (
			<div>
			<Grid>
			<LogoutButton auth={this.props.auth} />
			<HolidayForm addHandler={this.props.addHandler} />
			<HolidayTable state={this.props.state} />
			</Grid>
			</div>
		);
	}
}

export default HolidayOverview;