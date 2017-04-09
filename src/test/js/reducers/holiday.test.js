import holiday from '../../../main/resources/static/js_src/reducers/holidays.es6';

test('should add holidays', function () {

	const action = {
		type: "ADD_HOLIDAY",
		holiday: "someHoliday"
	};

	const state = holiday([], action);

	expect(state.length).toBe(1);
	expect(state[0]).toBe("someHoliday");
});
