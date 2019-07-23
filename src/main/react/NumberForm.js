import React, { Component } from 'react';
import {TextArea, Button, FileLoader} from '@bandwidth/shared-components';
import styled from 'styled-components';
import InputTabs from './InputTabs';
import {Tab} from '@bandwidth/shared-components';
import {withRouter} from "react-router-dom";


/**
 * Form for inputting numbers that are fed into the rest API
 */


const Label = styled.label`
	margin-bottom:15px;
	display:block;
`;


const inputHelpText = "Please enter the numbers in a list format with each number on a new line or in a csv format...";

class NumberForm extends React.Component {

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


	componentDidMount() {

		const location = this.props.history.location;
		if(location.pathname == "/") return;

		this.handleSharedLink(location.search);

	}

	/**
	 * Parse the shared link and query
	 * @param url
	 */
	handleSharedLink(search)
	{
		const query = search.split('=');
		if(query[0] !== '?phoneNumbers') return;
		this.handleSubmit(query[1]);
	}


	/**
	 * state is updated whenever a change is made in the text box
	 * @param event
	 */
	handleChange(event)
	{
		this.setState({value : event.target.value});
	}

	/**
	 * Translates list format to csv since the backend is only equipped to handle csv queries
	 * @param text
	 * @returns {*}
	 */
	prepareTextForQuery(text)
	{
		text = text.replace(/\n/g, ",");
		return text;
	}

	/**
	 * Sends the prepared output to the backend calls a parent function to send the response upwards
	 * @param text the csv number query
	 */

	handleSubmit(text)
	{
		if(text.length == 0) return;
		text = this.prepareTextForQuery(text);
		this.props.toggleLoading(true);
		fetch('http://localhost:8080/number/', {
			method: 'POST',
			headers : {
				'Content-Type': 'application/json',
				'Accept' : 'application/json',
				'X-HTTP-Method-Override': 'GET',
			},
		body: text,
		}).then(response => {
			let responseJSON = response.json();
			if(response.status === 500) throw new Error(response.status);
			return responseJSON;
		})
		.then(responseJSON => {
			this.props.onRequestSubmission(responseJSON, new Date().toLocaleString(), text);
			this.props.toggleLoading(false);
		})
		.catch((error) => {
			this.props.onRequestSubmission("500", new Date().toLocaleString(), text);
			this.props.toggleLoading(false);
		});


	}

	/**
	 * Reads the file and updates the component staet with the text
	 * @param file 	the file uploaded by the user
	 */
	handleFileUpload(files)
	{

		if(files)
		{
			let fullText = "";
			let fileCounter = files.length;

			Object.keys(files).forEach(i => {
				let file = files[i];
				let text = "";
				let reader = new FileReader();

				reader.onload = () =>
					{
						text = reader.result;
						if(text != "")
						{
							text += (text[text.length - 1] !=="\n") ? "\n" : "";
							fileCounter--;
							fullText += text;
							if(fileCounter ===0) {
								this.setState({fileText:fullText})}
							;

						}
					}
					reader.readAsText(file, "UTF-*");
			})



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

		let paddedForm =
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


		let fileLoader = 	<div>
			<FileLoader accept = ".csv, .doc, .txt, .docx" value ={this.state.uploadedFile} onChange = {files => {
				this.setState({uploadedFile : files}, () => { this.handleFileUpload(this.state.uploadedFile)});
			}} multiple/>
			<Button style = {{"marginTop": 15}} type="button" onClick = { () => { this.submitFile()}}> Submit File </Button>
		</div>

		return (
				<div style={{"marginTop":"50"}}>
					<InputTabs Group = {Tab.Group} Container = {Tab.Container} Tab={Tab} contents = {[paddedForm,fileLoader]} />
				</div>
		);
	}
}

export default withRouter(NumberForm)

