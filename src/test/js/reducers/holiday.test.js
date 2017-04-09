import holiday from '../../../main/resources/static/js_src/reducers/holidays.js';

test('should add holidays', function () {

	var action = {
		type: "ADD_HOLIDAY",
		holiday: "someHoliday"
	};

	var state = holiday([], action);

	expect(state.length).toBe(1);
	expect(state[0]).toBe("someHoliday");
});
