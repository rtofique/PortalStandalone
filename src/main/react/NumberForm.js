import React, { Component } from 'react';
import {TextArea} from '@bandwidth/shared-components';

/**
 * Form for inputting numbers that will be fed into the rest API for the number lookup feature
 */

export default class NumberForm extends React.Component {

	constructor(props)
	{
		super(props);
		this.state = {value : 'Enter all the phone numbers you need to look up in a csv format...'};

		this.handleChange = this.handleChange.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	//on every keystroke handleChange is called which then updates the state
	handleChange(event)
	{
		this.setState({value : event.target.value});
	}


	handleSubmit(event)
	{
		//instead of making the whole change here, call a function passed as a prop
		//make the request here

		alert('The value was submitted: ' + this.state.value);
		fetch('http://localhost:8080/PhoneNumber=' + this.state.value, {
			headers : {
				'Content-Type': 'application/json',
				'Accept' : 'application/json'
			}
		})

		.then(response => {this.props.onRequestSubmission(response.json())})

		//make the request here and send response up as a result instead of this.state.value
		//this.props.onRequestSubmission(this.state.value);
		event.preventDefault();
	}

	render()
	{
		//JSX inside the render elementd
		return (
		<div>
			<form  onSubmit= {this.handleSubmit}>
				<label>
					Phone Numbers:
					<TextArea type="text" placeholder={this.state.value} onChange={this.handleChange} />
				</label>
				<input type="submit" value="Submit" />
			</form>
		</div>
		);
	}

}
