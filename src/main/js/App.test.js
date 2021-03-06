import React from 'react';
import {shallow} from 'enzyme';
import {App} from './App';

test('App shows hello world', () => {
	const wrapper = shallow(<App/>);
	expect(wrapper.find('h1').text()).toEqual("Hello, world!");
});