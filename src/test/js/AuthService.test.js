import AuthService from '../../main/js/AuthService.es6';
import Auth0Lock from 'auth0-lock'
import * as sinon from "sinon";

describe('AuthService', function () {
	let testee;
	let lockMock;

	beforeEach(() => {
		lockMock = sinon.createStubInstance(Auth0Lock);
		testee = new AuthService(lockMock);
	});

	afterEach(() => {
	});

	describe('when initialising', function () {
		it('should register authentication callback', function () {
			sinon.assert.calledWith(lockMock.on, 'authenticated', testee.authenticate);
		});
	});
});
