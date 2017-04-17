import {App} from '../../../main/resources/static/js_src/components/App.es6';
import React from 'react';
import {shallow} from 'enzyme';
import * as sinon from "sinon";

describe('App', function () {
	it('should render heading', function () {
		const props = {
			state: []
		};

		const renderedComponent = shallow(<App {...props} />);
		expect(renderedComponent).toContainReact(<h1>Neuen Urlaub anlegen:</h1>);
	});

	it('should render holidays', function () {
	    const props = {
	    	state: [
	    		{
	    			id: 1,
	    			name: "someHoliday"
			    },
			    {
			    	id: 2,
			    	name: "anotherHoliday",
				    location: "someLocation"
			    }
		    ]
	    };

		const renderedComponent = shallow(<App {...props} />);
		expect(renderedComponent).toContainReact(<td>someHoliday</td>);
		expect(renderedComponent).toContainReact(<tr><td>anotherHoliday</td><td>someLocation</td></tr>);
	});

	it('should fire addHandler on submit', function () {
		const props = {
			state: [],
			addHandler: sinon.spy()
		};

		const event = {
			preventDefault: () => {}
		};

		const testee = new App(props);
		testee.handleSubmit(event);

		expect(props.addHandler.called).toBe(true);
	});
});

