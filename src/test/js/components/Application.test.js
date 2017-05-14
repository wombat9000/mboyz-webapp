'use strict';

import React from 'react';
import {shallow} from 'enzyme';
import * as sinon from "sinon";
import {Application} from "../../../main/js/components/Application";
import Navigation from "../../../main/js/components/Navigation";
import {Home} from "../../../main/js/components/Home";

describe('Application', function () {

	const props = {
		state: [],
		addHandler: sinon.spy,
		auth: {
			loggedIn: sinon.spy
		}
	};

	it('should show navigation', function () {
	    const renderedComponent = shallow(<Application {...props} />);
	    expect(renderedComponent).toContainReact(<Navigation auth={props.auth} />);

    });

    xit('should show home component for /', function () {
	    const renderedComponent = shallow(<Application {...props} />);
	    expect(renderedComponent).toContainReact(<Home/>);
    });

    xit('should require auth for holiday overview', function () {

    });
});