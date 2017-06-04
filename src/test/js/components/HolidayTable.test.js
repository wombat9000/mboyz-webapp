import HolidayTable from '../../../main/js/components/HolidayTable.es6';
import React from 'react';
import {shallow} from 'enzyme';
import * as sinon from "sinon";

describe('HolidayTable', function () {
	it('should render holidays', function () {
	    const props = {
	    	state: {
	    		holidays: [
	    			{
						id: 1,
						name: "someHoliday"
					},
					{
						id: 2,
						name: "anotherHoliday",
						location: "someLocation",
						startDate: "2017-04-12",
						endDate: "2017-04-13"
					}
				]
			}
	    };

		const renderedComponent = shallow(<HolidayTable {...props} />);
		expect(renderedComponent).toContainReact(<td>someHoliday</td>);
		expect(renderedComponent).toContainReact(<tr>
			<td>anotherHoliday</td>
			<td>someLocation</td>
			<td>2017-04-12</td>
			<td>2017-04-13</td>
		</tr>);
	});
});
