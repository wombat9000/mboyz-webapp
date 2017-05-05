import holiday from '../../../main/js/reducers/holidays.es6';

describe('holiday reducer', function () {
	it('should default to [] as initial state', function () {
		const unknownAction = {
			type: "unknown",
			holiday: "someHoliday"
		};

		const state = holiday(undefined, unknownAction);

		expect(state).toEqual([]);
	});

	it('should add holidays', function () {
		const action = {
			type: "ADD_HOLIDAY",
			holiday: "someHoliday"
		};

		const state = holiday([], action);

		expect(state.length).toBe(1);
		expect(state[0]).toBe("someHoliday");
	});

	it('should add multiple holidays', function () {
	    const action = {
	    	type: "ADD_HOLIDAYS",
		    holidays: ["someHoliday", "anotherHoliday"]
	    };

		const state = holiday([], action);

		expect(state.length).toBe(2);
		expect(state[0]).toBe("someHoliday");
		expect(state[1]).toBe("anotherHoliday");
	});
});

