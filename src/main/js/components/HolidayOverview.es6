'use strict';

import React from 'react';
import PropTypes from 'prop-types';
import HolidayForm from './HolidayForm';
import {Grid} from 'react-bootstrap';
import HolidayTable from './HolidayTable';

class HolidayOverview extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		const state = this.context.store.getState();
		return (
			<div>
			<Grid>
			<HolidayForm addHandler={this.props.addHandler} />
			<HolidayTable state={state} />
			</Grid>
			</div>
		);
	}
}
HolidayOverview.contextTypes = {
	store: PropTypes.object,
};

export default HolidayOverview;