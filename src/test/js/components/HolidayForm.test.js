import {HolidayForm} from '../../../main/js/components/HolidayForm.es6';
import React from 'react';
import {shallow} from 'enzyme';
import * as sinon from "sinon";


describe('HolidayForm', function () {
	it('should render heading', function () {
		const props = {
			state: []
		};

		const renderedComponent = shallow(<HolidayForm {...props} />);
		expect(renderedComponent).toContainReact(<h1>Neuen Urlaub anlegen:</h1>);
	});

	it('should fire addHandler on submit', function () {
		const props = {
			state: [],
			addHandler: sinon.spy()
		};

		const event = {
			preventDefault: () => {}
		};

		const testee = new HolidayForm(props);
		testee.handleSubmit(event);

		expect(props.addHandler.called).toBe(true);
	});
});