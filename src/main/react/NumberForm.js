import React, { Component } from 'react';
import {TextArea, Button, FileLoader} from '@bandwidth/shared-components';
import styled from 'styled-components';


/**
 * Form for inputting numbers that will be fed into the rest API for the number lookup feature
 */


const Label = styled.label`
	margin-bottom:15px;
	display:block;
`;

const PaddedForm = styled.div`
	margin-top:15px;
	display:block;
`;


export default class NumberForm extends React.Component {

	constructor(props)
	{
		super(props);
		this.state = {value : '', placeholder: 'Please enter the numbers you want to search using a csv format...'};

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
		fetch('http://localhost:8080/number?PhoneNumber=' + this.state.value, {
			headers : {
				'Content-Type': 'application/json',
				'Accept' : 'application/json'
			}
		})
		.then(response => response.json())
		.then(responseJSON => {this.props.onRequestSubmission(responseJSON);
		});


		//make the request here and send response up as a result instead of this.state.value
		//this.props.onRequestSubmission(this.state.value);
		event.preventDefault();
	}


	render()
	{
		//JSX inside the render element
		return (
		<PaddedForm>
			<form  onSubmit= {this.handleSubmit}>
				<Label htmlFor="numberfield">
					Phone Numbers:
					<TextArea id="numberfield" type="text" placeholder={this.state.placeholder} onChange={this.handleChange} />
				</Label>
				<FileLoader/>

				<Button type="submit" value="Submit">
						Submit
				</Button>
			</form>
		</PaddedForm>
		);
	}

}
