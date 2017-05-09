'use strict';

import React from 'react'
import {Nav, Navbar, NavItem} from "react-bootstrap";
import {LinkContainer} from "react-router-bootstrap";


export class Navigation extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		return (
				<Navbar inverse collapseOnSelect>
					<Navbar.Header>
						<Navbar.Brand>
							<LinkContainer to="/">
								<a href="/">Mboyz</a>
							</LinkContainer>
						</Navbar.Brand>
						<Navbar.Toggle />
					</Navbar.Header>
					<Navbar.Collapse>
						<Nav>
							<LinkContainer to="/holidays">
								<NavItem eventKey={1} href="/holidays">Urlaube</NavItem>
							</LinkContainer>
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