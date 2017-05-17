'use strict';

const holidays = (state = [], action) => {
	switch (action.type) {
		case 'ADD_HOLIDAY':
			return state.concat([action.holiday]);
		case 'ADD_HOLIDAYS':
			return state.concat(action.holidays);
		default:
			return state;
	}
};

export default holidays