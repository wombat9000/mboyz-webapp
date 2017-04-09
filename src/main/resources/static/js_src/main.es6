'use strict';
import React from 'react';
import ReactDOM from 'react-dom';
import {createStore} from 'redux';

import {HolidayPage} from './components/HolidayPage.es6';
import holiday from './reducers/holidays';

const store = createStore(holiday);

const addHandler = (holiday) => {
	store.dispatch({
		type: "ADD_HOLIDAY",
		holiday: holiday
	});
};


const render = () => {
	ReactDOM.render(<HolidayPage state={store.getState()}
	                             addHandler={addHandler} />, document.getElementById('root'));
};

store.subscribe(render);
render();