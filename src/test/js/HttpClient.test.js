import {HttpClient} from '../../main/resources/static/js_src/HttpClient.es6';
import * as sinon from "sinon";


describe('HttpClient', function () {

	let requests;

	beforeEach(() => {
		this.xhr = sinon.useFakeXMLHttpRequest();
		requests = [];

		this.xhr.onCreate = function (xhr) {
			requests.push(xhr);
		};
	});

	afterEach(() => {
		this.xhr.restore();
	});

	describe('fetching initial state', function () {
		let store;
		beforeEach(() => {
			store = {
				dispatch: sinon.spy()
			};
		});

		//TODO: it should go against correct URL, have correct request parameters

        it('should not dispatch to store if response is unsuccessful', function () {
        	HttpClient.fetchInitialState(store);
        	expect(requests.length).toBe(1);

        	requests[0].respond(503);

        	expect(store.dispatch.called).toBe(false);
        });

        it('should dispatch to store if response is successful', function () {
	        const someHoliday = {
	        	id: 1,
		        name: "someHoliday"
	        };

	        HttpClient.fetchInitialState(store);
	        expect(requests.length).toBe(1);

	        requests[0].respond(200, { "Content-Type": "application/json" },
		        '[{ "id": 1, "name": "someHoliday" }]');

	        expect(store.dispatch.called).toBe(true);
	        expect(store.dispatch.calledWith({
		        type: "ADD_HOLIDAYS",
		        holidays: [someHoliday]
	        })).toBe(true);
        });
   });
});