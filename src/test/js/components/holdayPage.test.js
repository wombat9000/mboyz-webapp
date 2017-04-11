import {HolidayPage} from '../../../main/resources/static/js_src/components/HolidayPage.es6';
import React from 'react';
import {shallow} from 'enzyme';

describe('HolidayPage', function () {
	it('should render heading', function () {
		const props = {
			state: []
		};

		const renderedComponent = shallow(<HolidayPage {...props} />);
		expect(renderedComponent).toContainReact(<h1>Neuen Urlaub anlegen:</h1>);
	});
});

