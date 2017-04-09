import React from 'react';
import ReactDOM from 'react-dom';
import {createStore} from 'redux';

import {HolidayPage} from './components/HolidayPage.es6';
import holiday from './reducers/holidays';

const store = createStore(holiday);

const addHandler = (holiday) => {
	console.log('add handler was called');

	store.dispatch({
		type: "ADD_HOLIDAY",
		holiday: holiday
	});
};

console.log(store);

const render = () => {
	console.log("rendering...");
	ReactDOM.render(<HolidayPage state={store.getState()}
	                             addHandler={addHandler} />, document.getElementById('root'));
};

store.subscribe(render);
render();