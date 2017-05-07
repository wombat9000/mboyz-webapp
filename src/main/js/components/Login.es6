'use strict';

import React from 'react'
import {ButtonToolbar, Button} from 'react-bootstrap'

export class Login extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		const auth = this.props.auth;
		return (
			<div>
				<h2>Login</h2>
				<ButtonToolbar>
					<Button bsStyle="primary" onClick={auth.login.bind(this)}>Login</Button>
				</ButtonToolbar>
			</div>
		)
	}
}

export default Login;