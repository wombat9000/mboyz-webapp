import {HttpClient} from '../../main/resources/static/js_src/HttpClient.es6';
import * as sinon from "sinon";

const ERROR = 503;
const OK = 200;
const CREATED = 201;

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

        it('should dispatch retrieved holidays to store if response is success', function () {
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
		let someHoliday = {
			name: "someHoliday",
			location: "someLocation"
		};

		it('should post to /holiday/create', function () {
			HttpClient.postNewHoliday(someHoliday, store);

			const URL = requests[0].url;
			const method = requests[0].method;
			expect(URL).toContain('/holiday/create');
			expect(method).toBe('POST');
		});

		it('should not dispatch to store if response is unsuccessful', function () {
			HttpClient.postNewHoliday(someHoliday, store);
			requests[0].respond(ERROR);

			expect(store.dispatch.called).toBe(false);
		});

		it('should post holiday data', function () {
			HttpClient.postNewHoliday(someHoliday, store);

			const holidayData = requests[0].requestBody;
			expect(holidayData.get("name")).toBe("someHoliday");
			expect(holidayData.get("location")).toBe("someLocation");
		});

		it('should dispatch created holiday to store if response is created', function () {
			const expectedHoliday = {
				id: 1,
				name: "someHoliday",
				location: "someLocation"
			};

			HttpClient.postNewHoliday(someHoliday, store);

			requests[0].respond(
				CREATED,
				{ "Content-Type": "application/json" },
				JSON.stringify(expectedHoliday)
			);

			expect(store.dispatch.calledWith({
				type: "ADD_HOLIDAY",
				holiday: expectedHoliday
			})).toBe(true);
		});
	});
});