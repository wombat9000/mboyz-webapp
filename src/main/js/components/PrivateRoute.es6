import React from 'react';
import {Route} from "react-router-dom";

class PrivateRoute extends React.Component {
	constructor(props) {
		super(props);
	}

	render () {
		const Component = this.props.component;
		const propsWithoutComponent = {...this.props};
		delete propsWithoutComponent.component;

		return (
			<Route {...propsWithoutComponent} render={(props) => (
				this.props.auth.loggedIn() ? (
					<Component {...props} {...this.props.componentProps}/>
				) : (
					<h2>Du musst einloggen, um diese Seite zu sehen.</h2>
				)
			)}/>
		)
	}
}

export default PrivateRoute;
