import {HttpClient} from '../../main/resources/static/js_src/HttpClient.es6';
import * as sinon from "sinon";

const ERROR = 503;
const OK = 200;

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

		it('should fetch from /holiday/index', function () {
			HttpClient.fetchInitialState(store);
			const URL = requests[0].url;
			expect(URL).toContain('/holiday/index');
		});

        it('should not dispatch to store if response is unsuccessful', function () {
        	HttpClient.fetchInitialState(store);

	        requests[0].respond(ERROR);

        	expect(store.dispatch.called).toBe(false);
        });

        it('should dispatch retrieved holidays to store if response is successful', function () {
	        const holidays = [
		        {id: 1, name: "someHoliday"},
		        {id: 2, name: "anotherHoliday"}
	        ];

	        HttpClient.fetchInitialState(store);

	        requests[0].respond(
	        	OK,
		        { "Content-Type": "application/json" },
		        JSON.stringify(holidays)
	        );

	        expect(store.dispatch.calledWith({
		        type: "ADD_HOLIDAYS",
		        holidays: holidays
	        })).toBe(true);
        });
   });
});