'use strict';

const holiday = (state = [], action) => {
	switch (action.type) {
		case 'ADD_HOLIDAY':
			state.push(action.holiday);
			return state;
		default:
			return state;
	}
};

export default holiday