'use strict';

import React from 'react'
import {Nav, Navbar, NavItem} from "react-bootstrap";


export class Navigation extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		return (
				<Navbar inverse collapseOnSelect>
					<Navbar.Header>
						<Navbar.Brand>
							<a href="/">Mboyz</a>
						</Navbar.Brand>
						<Navbar.Toggle />
					</Navbar.Header>
					<Navbar.Collapse>
						<Nav>
							<NavItem href="/holidays">Urlaube</NavItem>
						</Nav>
						<Nav pullRight>
							{this.props.auth.loggedIn() ?
								<NavItem onClick={this.props.auth.logout}>Logout</NavItem> :
								<NavItem onClick={this.props.auth.login}>Login</NavItem>}
						</Nav>
					</Navbar.Collapse>
				</Navbar>
		)
	}
}

export default Navigation;