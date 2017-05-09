'use strict';

import React from 'react';
import {Button} from "react-bootstrap";
import {Redirect} from "react-router-dom";


class LogoutButton extends React.Component {

	constructor(props) {
		super(props);

		this.state = {
			wasClicked: false
		};

		this.logout = this.logout.bind(this);
	}

	logout() {
		this.props.auth.logout();
		this.setState({ wasClicked: true })
	}

	render () {
		if (this.state.wasClicked) {
			return <Redirect to="/login" />
		}

		return (
			<div>
				<Button onClick={this.logout}>Logout</Button>
			</div>
		);
	}
}

export default LogoutButton;