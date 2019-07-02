import React from 'react';
import NumberForm from "./NumberForm";
import NumberLookupOutput from "./NumberLookupOutput";

export default class NumberlookupPage extends React.Component{

	constructor(props)
	{
		super(props);
		this.handleRequestSubmission = this.handleRequestSubmission.bind(this);
		this.state = {numberInput : ''};
	}

	/**
	 *
	 * @param submission
	 * This function is passed to NumberForm so that request and response are lifted up to this componenent and can thus be passed on to the output dialogue
	 */
	handleRequestSubmission(submission)
	{
		this.setState({numberInput : submission});
	}

	render()
	{
		const input = this.state.numberInput;

		return (
			<div>
				<NumberForm onRequestSubmission = {this.handleRequestSubmission} />
				<NumberLookupOutput output = {input} />
			</div>
		);
	}
}
