'use strict';

import React from 'react';
import HolidayForm from './HolidayForm';
import {Grid} from 'react-bootstrap';
import HolidayTable from './HolidayTable';

class HolidayOverview extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		return (
			<div>
			<Grid>
			<HolidayForm addHandler={this.props.addHandler} />
			<HolidayTable state={this.props.state} />
			</Grid>
			</div>
		);
	}
}

export default HolidayOverview;