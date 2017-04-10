import holiday from '../../../main/resources/static/js_src/reducers/holidays.es6';


describe('holiday reducer', function () {
	it('should add holidays', function () {
		const action = {
			type: "ADD_HOLIDAY",
			holiday: "someHoliday"
		};

		const state = holiday([], action);

		expect(state.length).toBe(1);
		expect(state[0]).toBe("someHoliday");
	});

	it('should default to [] as initial state', function () {
	    const unknownAction = {
	    	type: "unknown",
		    holiday: "someHoliday"
	    };

		const state = holiday(undefined, unknownAction);

		expect(state).toEqual([]);
	});
});

