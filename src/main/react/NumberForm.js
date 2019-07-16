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

const inputHelpText = "Please enter the numbers in a list format with each number on a new line or in a csv format...";

export default class NumberForm extends React.Component {

	constructor(props)
	{
		super(props);
		this.state = {value : '', fileText : '', placeholder: inputHelpText, uploadedFile : null};

		this.textAreaRef = React.createRef();

		this.handleChange = this.handleChange.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
		this.handleFileUpload = this.handleFileUpload.bind(this);
		this.submitFile = this.submitFile.bind(this);
		this.submitTextBox = this.submitTextBox.bind(this);
	}

	handleChange(event)
	{
		this.setState({value : event.target.value});
	}

	prepareTextForQuery(text)
	{
		text = text.replace(/\n/g, ",");
		return text;
	}

	handleSubmit(text)
	{
		if(text.length == 0) return;
		text = this.prepareTextForQuery(text);
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


	handleFileUpload(file)
	{
		if(file)
		{
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


		return (
				<div>
					<PaddedForm>
						<form  onSubmit= {this.submitTextBox}>
							<Label htmlFor="numberfield">
								Phone Numbers:
								<TextArea id ="numberfield" type="text" placeholder={this.state.placeholder} onChange={this.handleChange} ref={this.textAreaRef}/>
							</Label>
							<Button type="submit" value="Submit">
									Submit
							</Button>
							<Button style = {{"marginLeft":5}} type={"button"} onClick = {() => {
								this.textAreaRef.current.value = '';
								this.setState({value: ''});
							} }> Clear </Button>
						</form>
					</PaddedForm>
					<FileLoader value ={this.state.uploadedFile} onChange = {files => {
						this.setState({uploadedFile : files}, () => { this.handleFileUpload(this.state.uploadedFile[0])});
					}}/>
					<div>
						<Button style = {{"marginTop": 15}} type="button" onClick = { () => { this.submitFile()}}> Submit File </Button>
					</div>

				</div>
		);
	}

}


//features
//bugs
//check for ignite status before every request
//loading indicator
