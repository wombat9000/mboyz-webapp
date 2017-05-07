'use strict';

import React from 'react';
import {Button} from "react-bootstrap";
import {Redirect} from "react-router-dom";


class LogoutButton extends React.Component {


	constructor(props) {
		super(props);

		this.state = {
			loggedOut: false
		};

		this.logout = this.logout.bind(this);
	}

	logout() {
		this.props.auth.logout();
		this.setState({ loggedOut: true })
	}

	render () {
		if (this.state.loggedOut) {
			return <Redirect to="/login" />
		}

		return <div>
			<Button onClick={this.logout}>Logout</Button>
		</div>;
	}
}

export default LogoutButton;