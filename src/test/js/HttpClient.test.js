import {HttpClient} from '../../main/resources/static/js_src/HttpClient.es6';
import * as sinon from "sinon";

const ERROR = 503;
const OK = 200;

describe('HttpClient', function () {
	let requests;
	let store;

	beforeEach(() => {
		store = {
			dispatch: sinon.spy()
		};

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
		it('should get from /holiday/index', function () {
			HttpClient.fetchInitialState(store);
			const URL = requests[0].url;
			const method = requests[0].method;
			expect(URL).toContain('/holiday/index');
			expect(method).toBe('GET');

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

	describe('create new holiday', function () {
		it('should post to /holiday/create', function () {
			HttpClient.postNewHoliday();

			const URL = requests[0].url;
			const method = requests[0].method;
			expect(URL).toContain('/holiday/create');
			expect(method).toBe('POST');
		});
	});
});