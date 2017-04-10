'use strict';

const holiday = (state = [], action) => {
	switch (action.type) {
		case 'ADD_HOLIDAY':
			return state.concat([action.holiday]);
		default:
			return state;
	}
};

export default holiday