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
		this.state = {value : '', fileText : '', placeholder: 'Please enter the numbers you want to search using a csv format...'};

		this.handleChange = this.handleChange.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
		this.handleFileUpload = this.handleFileUpload.bind(this);
		this.submitFile = this.submitFile.bind(this);
		this.submitTextBox = this.submitTextBox.bind(this);
	}

	//on every keystroke handleChange is called which then updates the state
	handleChange(event)
	{
		this.setState({value : event.target.value});
	}


	//not reading response
	handleSubmit(text)
	{
		if(text.length == 0) return;
		fetch('http://localhost:8080/number/', {
			method: 'POST',
			headers : {
				'Content-Type': 'application/json',
				'Accept' : 'application/json',
				'X-HTTP-Method-Override': 'GET',
			},
		body: text,
		}).then(response => response.json())
		.then(responseJSON => {
			this.props.onRequestSubmission(responseJSON, new Date().toLocaleString());
		});


	}

	fileUpload;

	handleFileUpload(file)
	{
		if(file)
		{
			this.fileUpload = file;
			let reader = new FileReader();
			let text = "";
			reader.onload = () =>
			{
				text = reader.result;
				if(text != "")
				{
					this.setState({fileText : text});
				}
			}
			reader.readAsText(file, "UTF-*");

		}
	}

	submitTextBox(event)
	{
		console.log(this.state.value);
		this.handleSubmit(this.state.value + "");
		event.preventDefault();
	}

	submitFile()
	{
		if(this.state.fileText !== "")
		{
			this.handleSubmit(this.state.fileText);
		}
	}


	render()
	{
		//JSX inside the render element
		let file;

		return (
				<div>
					<PaddedForm>
						<form  onSubmit= {this.submitTextBox}>
							<Label htmlFor="numberfield">
								Phone Numbers:
								<TextArea id="numberfield" type="text" placeholder={this.state.placeholder} onChange={this.handleChange} />
							</Label>
							<Button type="submit" value="Submit">
									Submit
							</Button>
						</form>
					</PaddedForm>
					<FileLoader value = {file} onChange = {files => this.handleFileUpload(files[0])}/>
					<Button type="button" value="Upload File" onClick = { () => { this.submitFile()}}/>

				</div>
		);
	}

}


//features
//file
//timestamp for dates in csv millis
//file name as data for query time
//bugs
//multirow breaks
//rows remain but header and whatever gets changed, not cleaning the table
//fix appearances of file button